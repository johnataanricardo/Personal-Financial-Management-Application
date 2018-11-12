package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.CategoriaDto;
import com.fean.seufinanceiro.model.Categoria;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.service.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("categoria")
public class CategoriaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public ResponseEntity<Response<List<Categoria>>> getUsuarios(){
        LOGGER.info("Buscando todos dados de categoria...");
        Response<List<Categoria>> response = new Response<>();
        List<Categoria> categorias = categoriaService.showAll();

        if (categorias.isEmpty()){
            LOGGER.info("Nenhuma categoria foi encontrada...");
            response.getErrors().add("Nenhuma categoria foi encontrada...");
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(categorias);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<CategoriaDto>> getUsuarioById(@PathVariable("id") Long id){
        LOGGER.info("Buscando dados de categoria pelo ID: ", id);
        Response<CategoriaDto> response = new Response<>();
        Optional<Categoria> categoria = categoriaService.showCategoriaById(id);

        if (categoria == null){
            LOGGER.info("Categoria não encontrado pelo ID: ", id);
            response.getErrors().add("Usuário não encontrado pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertCategoriaDto(categoria.get()));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Response<String>> save(@Valid @RequestBody
                                                         CategoriaDto categoriaDto,
                                                 BindingResult result) {

        Response<String> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        categoriaService.novaCategoria(convertCategoria(categoriaDto));
        response.setData("Categoria salvo com sucesso!!!");

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody CategoriaDto categoriaDto,
                                                   BindingResult result) {

        categoriaDto.setId(String.valueOf(id));

        LOGGER.info("Atualizando categoria: {}", categoriaDto.toString());

        Response<String> response = new Response<>();
        if (result.hasErrors()) {
            LOGGER.error("Erro validando usuário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.categoriaService.novaCategoria(convertCategoria(categoriaDto));
        response.setData("Categoria atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id){
        LOGGER.info("Removendo usuário ID: {}", id);
        Response<String> response = new Response<>();
        Optional<Categoria> categoria = this.categoriaService.showCategoriaById(id);

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
        return  new CategoriaDto(String.valueOf(categoria.getId()) ,categoria.getDescricao());
    }

    private Categoria convertCategoria(CategoriaDto categoriaDto) {
        Categoria categoria = new Categoria();
        if(categoriaDto.getId() != null){
            categoria.setId(Long.parseLong(categoriaDto.getId()));
        }
        categoria.setDescricao(categoriaDto.getDescricao());
        return categoria;
    }

}
