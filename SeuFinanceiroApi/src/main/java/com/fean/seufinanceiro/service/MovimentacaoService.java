package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    private TransactionRepository transactionRepository;

    @Autowired
    public MovimentacaoService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Movimentacao> showAllDespesas(){
        return (List<Movimentacao>) transactionRepository.findAll();
    }

    public List<Movimentacao> showAllMovimentacoesByUser(Long id){
        return transactionRepository.findAllByUsuarioId(id);
    }

    public Movimentacao showMovimentacaoByIdByUserId(Long idMovimentacao, Long idUser){
        return transactionRepository.findByIdAndUsuarioId(idMovimentacao, idUser);
    }


    public List<Movimentacao> showAllDespesasByYearMonth(String year, String month, Long userId){
        return  transactionRepository.findByAnoAndMesAndUsuarioId(year, Integer.parseInt(month), userId);
    }

    public Movimentacao findDespesaById(Long id){
        return transactionRepository.findById(id).get();
    }

    public void novoDespesa(Movimentacao movimentacao){
        transactionRepository.save(movimentacao);
    }

    public void removeDespesa(Long id){
        transactionRepository.deleteById(id);
    }
}
