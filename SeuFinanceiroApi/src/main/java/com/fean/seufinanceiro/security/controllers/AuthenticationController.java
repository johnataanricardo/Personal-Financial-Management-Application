package com.fean.seufinanceiro.security.controllers;

import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.dtos.JwtAuthenticationDto;
import com.fean.seufinanceiro.security.dtos.TokenDto;
import com.fean.seufinanceiro.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Generate a new token JWT
     *
     * @param authenticationDto
     * @param result
     * @return ResponseEntity<Response<TokenDto>>
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<Response<TokenDto>> generateTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto,
                                                               BindingResult result) throws AuthenticationException {

        Response<TokenDto> response = new Response<TokenDto>();

        if (result.hasErrors()) {
            LOGGER.error("Error Validating Data Integrity: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        LOGGER.info("Generating token for email {}.", authenticationDto.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(),
                        authenticationDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
        String token = jwtTokenUtil.obtainToken(userDetails);
        response.setData(new TokenDto(token));

        return ResponseEntity.ok(response);
    }

    /**
     * Generate a new token JWT with due
     *
     * @param request
     * @return ResponseEntity<Response<TokenDto>>
     */
    @PostMapping(value = "/refresh")
    public ResponseEntity<Response<TokenDto>> generateRefreshTokenJwt(HttpServletRequest request) {

        LOGGER.info("Generating refresh token JWT.");

        Response<TokenDto> response = new Response<TokenDto>();

        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.getErrors().add("Token not informed.");
        } else if (!jwtTokenUtil.validToken(token.get())) {
            response.getErrors().add("Token invalid or expired.");
        }

        if (!response.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }

        String refreshedToken = jwtTokenUtil.refreshToken(token.get());

        response.setData(new TokenDto(refreshedToken));
        return ResponseEntity.ok(response);
    }

    /**
     * Validate token
     *
     * @param tokenDto
     * @return ResponseEntity<Response<Boolean>>
     */
    @PostMapping(value = "/valid")
    public ResponseEntity<Response<Boolean>> verifyTokenJwt(@RequestBody TokenDto tokenDto) {

        LOGGER.info("Checking the token received...");

        Response<Boolean> response = new Response<>();

        if (tokenDto == null || jwtTokenUtil.getUsernameFromToken(tokenDto.getToken()) == null) {
            response.setData(Boolean.FALSE);
        } else {
            response.setData(jwtTokenUtil.validToken(tokenDto.getToken()));
        }

        return ResponseEntity.ok(response);
    }
}
