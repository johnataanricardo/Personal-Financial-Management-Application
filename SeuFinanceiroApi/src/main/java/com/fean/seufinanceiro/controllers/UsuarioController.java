package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.SignUpDto;
import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.security.enums.ProfileEnum;
import com.fean.seufinanceiro.service.UsuarioService;
import com.fean.seufinanceiro.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
    public ResponseEntity<Response<String>> save(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {
        Response<String> response = new Response<>();

        checkUsuarioSignUpData(signUpDto, result);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        usuarioService.newUser(convertUsuarioNovoDto(signUpDto));
        response.setData("Usuário cadastrado com sucesso!");

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
        Usuario usuario = new Usuario();

        if(usuarioDto.getSenha() == null || usuarioDto.getSenha().isEmpty()){
            usuario.setSenha(
                    PasswordUtils.generateBCrypt(usuarioService.findUsuarioById(jwtUser.getId()).get().getSenha())
            );
        }else{
            usuario.setSenha(PasswordUtils.generateBCrypt(usuarioDto.getSenha()));
        }

        usuario.setId(jwtUser.getId());
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPerfil(ProfileEnum.ROLE_USUARIO);
        return usuario;
    }
}
