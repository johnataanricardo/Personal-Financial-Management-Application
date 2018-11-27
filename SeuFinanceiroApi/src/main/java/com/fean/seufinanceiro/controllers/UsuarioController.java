package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.SignUpDto;
import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.exceptions.UserNotFoundException;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.security.dtos.TokenDto;
import com.fean.seufinanceiro.security.enums.ProfileEnum;
import com.fean.seufinanceiro.security.utils.JwtTokenUtil;
import com.fean.seufinanceiro.service.UsuarioService;
import com.fean.seufinanceiro.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager,
                             JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping
    public ResponseEntity<Response<UsuarioDto>> getUsuario(@AuthenticationPrincipal JwtUser jwtUser) {
        LOGGER.info("Buscando dados de usuário pelo ID: ", jwtUser.getId());
        Response<UsuarioDto> response = new Response<>();
        Optional<Usuario> usuario = usuarioService.findUsuarioById(jwtUser.getId());

        if (!usuario.isPresent()) {
            LOGGER.info("Usuário não encontrado pelo ID: ", jwtUser.getId());
            response.getErrors().add("Usuário não encontrado pelo ID: " + jwtUser.getId());
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertUsuarioDto(usuario.get()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response<TokenDto>> save(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {
        Response<TokenDto> response = new Response<>();

        checkUsuarioSignUpData(signUpDto, result);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Usuario usuario = usuarioService.newUser(convertUsuarioNovoDto(signUpDto));

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtTokenUtil.obtainToken(userDetails);

        response.setData(new TokenDto(token));

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Response<String>> update(@Valid @RequestBody UsuarioDto usuarioDto,
                                                   @AuthenticationPrincipal JwtUser jwtUser,
                                                   BindingResult result) {

        LOGGER.info("Atualizando usuário: {}", usuarioDto.toString());
        Response<String> response = new Response<>();

        if (result.hasErrors()) {
            LOGGER.error("Erro validando usuário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.usuarioService.newUser(convertUsuario(usuarioDto, jwtUser));
        response.setData("Usuário atualizado com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Response<String>> remove(@AuthenticationPrincipal JwtUser jwtUser) {
        LOGGER.info("Removendo usuário ID: {}", jwtUser.getId());
        Response<String> response = new Response<>();
        Optional<Usuario> usuario = this.usuarioService.findUsuarioById(jwtUser.getId());

        if (usuario == null) {
            LOGGER.info("Erro ao remover devido ao usuário ID: {} ser inválido", jwtUser.getId());
            response.getErrors().add("Erro ao remover usuário. Registro não encontrado para o id " + jwtUser.getId());
            return ResponseEntity.badRequest().body(response);
        }

        this.usuarioService.removeUser(jwtUser.getId());
        response.setData("Usuário removido com sucesso!");
        return ResponseEntity.ok(response);
    }

    private void checkUsuarioSignUpData(SignUpDto signUpDto, BindingResult result) {
        LOGGER.info("Validando Usuário ID {}: ", signUpDto.getEmail());

        Optional<Usuario> usuario = this.usuarioService
                                    .findUserByUsernameEmail(signUpDto.getEmail());

        if (usuario.isPresent()) {
            result.addError(new ObjectError("Usuário",
                    "Usuário já cadastrado."));
        }
    }

    private Usuario convertUsuarioNovoDto(SignUpDto signUpDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(signUpDto.getNome());
        usuario.setEmail(signUpDto.getEmail());
        usuario.setSenha(PasswordUtils.generateBCrypt(signUpDto.getSenha()));
        usuario.setPerfil(ProfileEnum.ROLE_USUARIO);
        return usuario;
    }

    private UsuarioDto convertUsuarioDto(Usuario usuario) {
        return new UsuarioDto(String.valueOf(usuario.getId()),
                usuario.getNome(),
                usuario.getEmail());
    }

    private Usuario convertUsuario(UsuarioDto usuarioDto, JwtUser jwtUser) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(jwtUser.getId());

        if (!usuario.isPresent()){
                throw new UserNotFoundException("Usuário não encontrado");
        }

        if(usuarioDto.getSenha() != null){
            if (!usuarioDto.getSenha().isEmpty()){
                usuario.get().setSenha(PasswordUtils.generateBCrypt(usuarioDto.getSenha()));
            }
        }
        usuario.get().setId(jwtUser.getId());
        usuario.get().setNome(usuarioDto.getNome());
        usuario.get().setEmail(usuarioDto.getEmail());
        usuario.get().setPerfil(ProfileEnum.ROLE_USUARIO);
        return usuario.get();

    }
}
