package com.fean.seufinanceiro.repository;

import com.fean.seufinanceiro.model.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Categoria, Long> {
    List<Categoria> findAllByUsuarioId(Long id);
    Categoria findByIdAndUsuarioId(Long idCategoria, Long idUsuario);
}
