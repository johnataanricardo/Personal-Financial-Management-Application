package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.FluxoDeCaixaDto;
import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.enums.Meses;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import com.fean.seufinanceiro.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }


    public List<Movimentacao> showAllDespesas(){
        return (List<Movimentacao>) movimentacaoRepository.findAll();
    }

    public List<Movimentacao> showAllMovimentacoesByUser(Long id){
        return movimentacaoRepository.findAllByUsuarioId(id);
    }

    public Movimentacao showMovimentacaoByIdByUserId(Long idMovimentacao, Long idUser){
        return movimentacaoRepository.findByIdAndUsuarioId(idMovimentacao, idUser);
    }


    public List<Movimentacao> showAllDespesasByYearMonth(String year, String month){
        return  movimentacaoRepository.findByAnoAndMes(year, Meses.valueOf(month.toUpperCase()));
    }

    public Movimentacao findDespesaById(Long id){
        return movimentacaoRepository.findById(id).get();
    }

    public void novoDespesa(Movimentacao movimentacao){
        movimentacaoRepository.save(movimentacao);
    }

    public void removeDespesa(Long id){
        movimentacaoRepository.deleteById(id);
    }

    public Double calcFluxoCaixa(FluxoDeCaixaDto fluxoDeCaixaDto){

        Double depSaida = 0.0;
        Double depEntrada = 0.0;

        for (Movimentacao movimentacao : fluxoDeCaixaDto.getMovimentacaos()) {
            if (movimentacao.getTipoDespesa().equals(TipoDespesa.ENTRADA)){
                depEntrada +=  movimentacao.getValor();
            }else{
                depSaida +=  movimentacao.getValor();
            }
        }

        return depEntrada - depSaida;

    }

}
