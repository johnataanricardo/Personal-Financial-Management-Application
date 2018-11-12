package com.fean.seufinanceiro.repository;

import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.enums.Meses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends CrudRepository<Movimentacao, Long> {

    List<Movimentacao> findByAnoAndMes(String year, Meses month);

}
