package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.model.Categoria;
import com.fean.seufinanceiro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoriaService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Categoria> showAll(){
        return (List<Categoria>) categoryRepository.findAll();
    }

    public List<Categoria> showAllCategoryByUserId(Long id){
        return categoryRepository.findAllByUsuarioId(id);
    }

    public Categoria showCategoriaById(Long id){
        return categoryRepository.findById(id).get();
    }

    public Categoria showCategoriaByIdAndUserId(Long idCategoria, Long idUser){
        return categoryRepository.findByIdAndUsuarioId(idCategoria, idUser);
    }

    public Categoria novaCategoria(Categoria categoria){
        categoryRepository.save(categoria);
        return categoria;
    }

    public void removeCategoria(Long id){
        categoryRepository.deleteById(id);
    }
}
