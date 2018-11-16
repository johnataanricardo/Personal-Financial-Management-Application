package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.FluxoDeCaixaDto;
import com.fean.seufinanceiro.dto.MovimentacaoDto;
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


    public List<Movimentacao> showAllDespesasByYearMonth(String year, String month, Long userId){
        return  movimentacaoRepository.findByAnoAndMesAndUsuarioId(year, Meses.valueOf(month.toUpperCase()), userId);
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

        for (MovimentacaoDto movimentacaoDto : fluxoDeCaixaDto.getMovimentacaos()) {
            if (movimentacaoDto.getTipoDespesa().equals(TipoDespesa.ENTRADA)){
                depEntrada +=  Double.parseDouble(movimentacaoDto.getValor());
            }else{
                depSaida +=  Double.parseDouble(movimentacaoDto.getValor());
            }
        }

        return depEntrada - depSaida;

    }

}
