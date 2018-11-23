package com.fean.seufinanceiro.repository;

import com.fean.seufinanceiro.model.Movimentacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends CrudRepository<Movimentacao, Long> {

    List<Movimentacao> findByAnoAndMesAndUsuarioId(String year, Integer month, Long userId);
    List<Movimentacao> findAllByUsuarioId(Long userId);
    Movimentacao findByIdAndUsuarioId(Long movimentacaoId, Long userId);

}
