package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.SignUpDto;
import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.enums.ProfileEnum;
import com.fean.seufinanceiro.service.UsuarioService;
import com.fean.seufinanceiro.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("{id}")
    public ResponseEntity<Response<UsuarioDto>> getUsuarioById(@PathVariable("id") Long id) {
        LOGGER.info("Buscando dados de usuário pelo ID: ", id);
        Response<UsuarioDto> response = new Response<>();
        Optional<Usuario> usuario = usuarioService.findUsuarioById(id);

        if (usuario == null) {
            LOGGER.info("Usuário não encontrado pelo ID: ", id);
            response.getErrors().add("Usuário não encontrado pelo ID: " + id);
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

    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody UsuarioDto usuarioDto,
                                                   BindingResult result) {

        usuarioDto.setId(String.valueOf(id));

        LOGGER.info("Atualizando usuário: {}", usuarioDto.toString());
        Response<String> response = new Response<>();

        checkUsuarioData(usuarioDto, result);

        if (result.hasErrors()) {
            LOGGER.error("Erro validando usuário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.usuarioService.newUser(convertUsuario(usuarioDto));
        response.setData("Usuário atualizado com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
        LOGGER.info("Removendo usuário ID: {}", id);
        Response<String> response = new Response<>();
        Optional<Usuario> usuario = this.usuarioService.findUsuarioById(id);

        if (usuario == null) {
            LOGGER.info("Erro ao remover devido ao usuário ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover usuário. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.usuarioService.removeUser(id);
        response.setData("Usuário removido com sucesso!");
        return ResponseEntity.ok(response);
    }

    private UsuarioDto convertUsuarioDto(Usuario usuario) {
        return new UsuarioDto(String.valueOf(usuario.getId()),
                usuario.getNome(),
                usuario.getEmail());
    }

    private Usuario convertUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setId(Long.parseLong(usuarioDto.getId()));
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPerfil(ProfileEnum.ROLE_USUARIO);
        return usuario;
    }

    private void checkUsuarioData(UsuarioDto usuarioDto, BindingResult result) {
        if (usuarioDto.getId() == null) {
            result.addError(new ObjectError("Usuário",
                    "ID do usuário não informado."));
            return;
        }

        LOGGER.info("Validando Usuário ID {}: ", usuarioDto.getId());
        Optional<Usuario> usuario = this.usuarioService
                .findUsuarioById(Long.parseLong(usuarioDto.getId()));

        if (!usuario.isPresent()) {
            result.addError(new ObjectError("Usuário",
                    "Usuário não encontrado. ID inexistente."));
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

    private void checkUsuarioSignUpData(SignUpDto signUpDto, BindingResult result) {

        LOGGER.info("Validando Usuário ID {}: ", signUpDto.getEmail());
        Optional<Usuario> usuario = this.usuarioService
                .findUserByUsernameEmail(signUpDto.getEmail());

        if (usuario.isPresent()) {
            result.addError(new ObjectError("Usuário",
                    "Usuário já cadastrado."));
        }
    }

}
