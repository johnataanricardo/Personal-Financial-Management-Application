package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.CategoriaDto;
import com.fean.seufinanceiro.model.Categoria;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.service.CategoriaService;
import com.fean.seufinanceiro.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("categoria")
public class CategoriaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaController.class);

    private final CategoriaService categoriaService;

    private final UsuarioService usuarioService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService, UsuarioService usuarioService) {
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoriaDto>>> getCategorias(@AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando todos dados de categoria do usuário ID: ", jwtUser.getId());

        Response<List<CategoriaDto>> response = new Response<>();

        List<Categoria> categorias = categoriaService.showAllCategoryByUserId(jwtUser.getId());

        if (categorias.isEmpty()){
            LOGGER.info("Nenhuma categoria foi encontrada...");
        }

        List<CategoriaDto> categoriaDtos = new ArrayList<>();

        for (Categoria categoria: categorias) {
            categoriaDtos.add(convertCategoriaDto(categoria));
        }

        response.setData(categoriaDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<CategoriaDto>> getCategoriaById(@PathVariable("id") Long id,
                                                                   @AuthenticationPrincipal JwtUser jwtUser){
        LOGGER.info("Buscando dados de categoria pelo ID: ", id);
        Response<CategoriaDto> response = new Response<>();
        Categoria categoria = categoriaService.showCategoriaByIdAndUserId(id, jwtUser.getId());

        if (categoria == null){
            LOGGER.info("Categoria não encontrada pelo ID: ", id);
            response.getErrors().add("Categoria não encontrada pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertCategoriaDto(categoria));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<CategoriaDto>> save(@Valid @RequestBody CategoriaDto categoriaDto,
                                                       @AuthenticationPrincipal JwtUser jwtUser, BindingResult result) {

        LOGGER.info("Salvando uma nova categoria para o usuário ID: ", jwtUser.getId());
        Response<CategoriaDto> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Categoria categoria = categoriaService.novaCategoria(convertCategoria(categoriaDto,jwtUser));
        response.setData(convertCategoriaDto(categoria));

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long idCategoria,
                                                   @Valid @RequestBody CategoriaDto categoriaDto,
                                                   @AuthenticationPrincipal JwtUser jwtUser,
                                                   BindingResult result) {

        LOGGER.info("Atualizando categoria: {}", categoriaDto.toString());
        categoriaDto.setId(idCategoria);

        Response<String> response = new Response<>();
        if (result.hasErrors()) {
            LOGGER.error("Erro validando categoria: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.categoriaService.novaCategoria(convertCategoria(categoriaDto, jwtUser));
        response.setData("Categoria atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Removendo categoria ID:"+ id +" do usuário ID: {}", jwtUser.getId());

        Response<String> response = new Response<>();

        Categoria categoria = this.categoriaService.showCategoriaByIdAndUserId(id, jwtUser.getId());

        if (categoria == null){
            LOGGER.info("Erro ao remover devido a categoria ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover a categoria. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.categoriaService.removeCategoria(id);
        response.setData("Categoria removida com sucesso!");
        return ResponseEntity.ok(response);
    }

    private CategoriaDto convertCategoriaDto(Categoria categoria) {
        return new CategoriaDto(categoria.getId() ,categoria.getDescricao());
    }

    private Categoria convertCategoria(CategoriaDto categoriaDto,JwtUser jwtUser) {
        Categoria categoria;
        if(categoriaDto.getId() != null){
            categoria = categoriaService.showCategoriaByIdAndUserId(categoriaDto.getId(), jwtUser.getId());
        }else {
            categoria = new Categoria();
            Optional<Usuario> usuario = usuarioService.findUsuarioById(jwtUser.getId());
            usuario.ifPresent(categoria::setUsuario);
        }
        categoria.setDescricao(categoriaDto.getDescricao());
        return categoria;
    }

}
