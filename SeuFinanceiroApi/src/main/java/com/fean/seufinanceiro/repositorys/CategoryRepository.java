package com.fean.seufinanceiro.repositorys;

import com.fean.seufinanceiro.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAllByUserId(Long id);

    Category findByIdAndUserId(Long idCategory, Long userId);
}
