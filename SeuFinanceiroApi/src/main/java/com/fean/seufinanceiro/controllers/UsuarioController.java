package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.SignUpDto;
import com.fean.seufinanceiro.dto.UsuarioDto;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.service.UsuarioService;
import com.fean.seufinanceiro.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("user")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<Response<List<UsuarioDto>>> getUsuarios(){
        LOGGER.info("Buscando todos dados de usuários...");
        Response<List<UsuarioDto>> response = new Response<>();
        List<UsuarioDto> usuariosDto = usuarioService.usuariosDto();

        if (usuariosDto.isEmpty()){
            LOGGER.info("Nenhum usuário foi encontrada...");
            response.getErrors().add("Nenhum usuário foi encontrado...");
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(usuariosDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<UsuarioDto>> getUsuarioById(@PathVariable("id") Long id){
        LOGGER.info("Buscando dados de usuário pelo ID: ", id);
        Response<UsuarioDto> response = new Response<>();
        Usuario usuario = usuarioService.finById(id);

        if (usuario == null){
            LOGGER.info("Usuário não encontrado pelo ID: ", id);
            response.getErrors().add("Usuário não encontrado pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertUsuarioDto(usuario));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Response<String>> save(@Valid @RequestBody
                                                         SignUpDto usuarioNovo,
                                                                BindingResult result) {

        Response<String> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        usuarioService.add(convertUsuarioNovoDto(usuarioNovo));
        response.setData("Usuário salvo com sucesso!!!");

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody UsuarioDto usuarioDto,
                                                   BindingResult result)
            throws ParseException {

        usuarioDto.setId(String.valueOf(id));

        LOGGER.info("Atualizando usuário: {}", usuarioDto.toString());

        Response<String> response = new Response<>();
        if (result.hasErrors()) {
            LOGGER.error("Erro validando usuário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }


        this.usuarioService.update(convertUsuario(usuarioDto));
        response.setData("Usuário atualizado com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id){
        LOGGER.info("Removendo usuário ID: {}", id);
        Response<String> response = new Response<>();
        Usuario usuario = this.usuarioService.finById(id);

        if (usuario == null){
            LOGGER.info("Erro ao remover devido ao usuário ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover usuário. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.usuarioService.remove(id);
        response.setData("Usuário removido com sucesso!");
        return ResponseEntity.ok(response);
    }

    private UsuarioDto convertUsuarioDto(Usuario usuario) {
        return  new UsuarioDto(String.valueOf(usuario.getId()),
                usuario.getNome(),
                usuario.getSobreNome(),
                usuario.getCpf(),
                usuario.getEstado(),
                usuario.getCidade(),
                usuario.getEndereco(),
                usuario.getNumero(),
                usuario.getEmail());
    }

    private Usuario convertUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setId(Long.parseLong(usuarioDto.getId()));
        usuario.setNome(usuarioDto.getNome());
        usuario.setSobreNome(usuarioDto.getSobreNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNumero(usuarioDto.getNumero());
        usuario.setEstado(usuarioDto.getEstado());
        usuario.setCidade(usuarioDto.getCidade());
        usuario.setCpf(usuarioDto.getCpf());
        return usuario;
    }


    private Usuario convertUsuarioNovoDto(SignUpDto signUpDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(signUpDto.getNome());
        usuario.setSobreNome(signUpDto.getSobreNome());
        usuario.setEmail(signUpDto.getEmail());
        usuario.setNumero(signUpDto.getNumero());
        usuario.setEstado(signUpDto.getEstado());
        usuario.setCidade(signUpDto.getCidade());
        usuario.setCpf(signUpDto.getCpf());
        usuario.setSenha(PasswordUtils.generateBCrypt(signUpDto.getSenha()));
        return usuario;
    }


}
