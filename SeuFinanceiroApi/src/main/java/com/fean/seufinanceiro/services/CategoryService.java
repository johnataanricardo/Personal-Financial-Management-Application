package com.fean.seufinanceiro.services;

import com.fean.seufinanceiro.models.Category;
import com.fean.seufinanceiro.repositorys.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> showAllCategoryByUserId(Long id){
        return categoryRepository.findAllByUserId(id);
    }

    public Optional<Category> showCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    public Category showCategoryByIdAndUserId(Long categoryId, Long userId){
        return categoryRepository.findByIdAndUserId(categoryId, userId);
    }

    public Category newCategory(Category category){
        categoryRepository.save(category);
        return category;
    }

    public void removeCategory(Long id){
        categoryRepository.deleteById(id);
    }

}
