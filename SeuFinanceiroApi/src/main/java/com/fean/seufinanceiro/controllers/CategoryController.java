package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dtos.CategoryDto;
import com.fean.seufinanceiro.models.Category;
import com.fean.seufinanceiro.models.Transaction;
import com.fean.seufinanceiro.models.User;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.services.CategoryService;
import com.fean.seufinanceiro.services.TransactionService;
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
@RequestMapping("category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService, TransactionService transactionService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories(@AuthenticationPrincipal JwtUser jwtUser) {

        LOGGER.info("Searching for all user category data ID: ", jwtUser.getId());

        Response<List<CategoryDto>> response = new Response<>();

        List<Category> categories = categoryService.showAllCategoryByUserId(jwtUser.getId());

        if (categories.isEmpty()) {
            LOGGER.info("No categories found...");
        }

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (Category category : categories) {
            categoryDtoList.add(convertCategoryDto(category));
        }

        response.setData(categoryDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<CategoryDto>> getCategoryById(@PathVariable("id") Long id,
                                                                 @AuthenticationPrincipal JwtUser jwtUser) {

        LOGGER.info("Searching for category data by ID: ", id);

        Response<CategoryDto> response = new Response<>();

        Category category = categoryService.showCategoryByIdAndUserId(id, jwtUser.getId());

        if (category == null) {
            LOGGER.info("Category not found by ID: ", id);
            response.getErrors().add("Category not found by ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertCategoryDto(category));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<CategoryDto>> save(@Valid @RequestBody CategoryDto categoryDto,
                                                      @AuthenticationPrincipal JwtUser jwtUser,
                                                      BindingResult result) {

        LOGGER.info("Saving a new category to the user ID: ", jwtUser.getId());
        Response<CategoryDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Category category = categoryService.newCategory(convertCategory(categoryDto, jwtUser));
        response.setData(convertCategoryDto(category));

        return ResponseEntity.ok(response);
    }


    @PutMapping
    public ResponseEntity<Response<String>> update(@Valid @RequestBody CategoryDto categoryDto,
                                                   @AuthenticationPrincipal JwtUser jwtUser,
                                                   BindingResult result) {

        LOGGER.info("Updating category: {}", categoryDto.toString());

        Response<String> response = new Response<>();

        if (result.hasErrors()) {
            LOGGER.error("Error validating category: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.categoryService.newCategory(convertCategory(categoryDto, jwtUser));
        response.setData("Category successfully updated!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal JwtUser jwtUser) {

        LOGGER.info("Removing category ID:" + id + " by user ID: {}", jwtUser.getId());

        Response<String> response = new Response<>();

        Transaction transaction = this.transactionService.showTransactionByCategoryId(id);

        if (transaction != null) {
            LOGGER.info("Error removing category: {} invalid", id);
            response.getErrors().add("Error removing category. Record not found by id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.categoryService.removeCategory(id);
        response.setData("Category removed successfully!");
        return ResponseEntity.ok(response);
    }

    private CategoryDto convertCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getDescription());
    }

    private Category convertCategory(CategoryDto categoryDto, JwtUser jwtUser) {
        Category category;

        if (categoryDto.getId() != null) {
            category = categoryService.showCategoryByIdAndUserId(categoryDto.getId(), jwtUser.getId());
        } else {
            category = new Category();
            Optional<User> user = userService.findUsuarioById(jwtUser.getId());
            user.ifPresent(category::setUser);
        }

        category.setDescription(categoryDto.getDescription());

        return category;
    }

}
