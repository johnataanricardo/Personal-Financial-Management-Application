package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dtos.CategoryDto;
import com.fean.seufinanceiro.models.Category;
import com.fean.seufinanceiro.models.User;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.services.CategoryService;
import com.fean.seufinanceiro.services.UserService;

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
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategorys(@AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando todos dados de categoria do usuário ID: ", jwtUser.getId());

        Response<List<CategoryDto>> response = new Response<>();

        List<Category> categories = categoryService.showAllCategoryByUserId(jwtUser.getId());

        if (categories.isEmpty()){
            LOGGER.info("Nenhuma categoria foi encontrada...");
        }

        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category category : categories) {
                categoryDtos.add(convertCategoryDto(category));
        }

        response.setData(categoryDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<CategoryDto>> getCategoriaById(@PathVariable("id") Long id,
                                                                  @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando dados de category pelo ID: ", id);
        Response<CategoryDto> response = new Response<>();

        Category category = categoryService.showCategoryByIdAndUserId(id, jwtUser.getId());

        if (category == null){
            LOGGER.info("Category não encontrada pelo ID: ", id);
            response.getErrors().add("Category não encontrada pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertCategoryDto(category));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<CategoryDto>> save(@Valid @RequestBody CategoryDto categoryDto,
                                                      @AuthenticationPrincipal JwtUser jwtUser,
                                                      BindingResult result) {

        LOGGER.info("Salvando uma nova category para o usuário ID: ", jwtUser.getId());
        Response<CategoryDto> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Category category = categoryService.newCategory(convertCategory(categoryDto,jwtUser));
        response.setData(convertCategoryDto(category));

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long idCategoria,
                                                   @Valid @RequestBody CategoryDto categoryDto,
                                                   @AuthenticationPrincipal JwtUser jwtUser,
                                                   BindingResult result) {

        LOGGER.info("Atualizando categoria: {}", categoryDto.toString());
        categoryDto.setId(idCategoria);

        Response<String> response = new Response<>();

        if (result.hasErrors()) {
            LOGGER.error("Erro validando categoria: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.categoryService.newCategory(convertCategory(categoryDto, jwtUser));
        response.setData("Category atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Removendo category ID:"+ id +" do usuário ID: {}", jwtUser.getId());

        Response<String> response = new Response<>();

        Category category = this.categoryService.showCategoryByIdAndUserId(id, jwtUser.getId());

        if (category == null){
            LOGGER.info("Erro ao remover devido a category ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover a category. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.categoryService.removeCategory(id);
        response.setData("Category removida com sucesso!");
        return ResponseEntity.ok(response);
    }

    private CategoryDto convertCategoryDto(Category category) {
        return new CategoryDto(category.getId() , category.getDescription());
    }

    private Category convertCategory(CategoryDto categoryDto, JwtUser jwtUser) {
        Category category;

        if(categoryDto.getId() != null){
            category = categoryService.showCategoryByIdAndUserId(categoryDto.getId(), jwtUser.getId());
        }else {
            category = new Category();
            Optional<User> user = userService.findUsuarioById(jwtUser.getId());
            user.ifPresent(category::setUser);
        }

        category.setDescription(categoryDto.getDescription());

        return category;
    }

}
