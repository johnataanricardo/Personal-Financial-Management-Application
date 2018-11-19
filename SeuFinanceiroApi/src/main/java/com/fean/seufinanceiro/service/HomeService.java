package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.HomeDto;
import com.fean.seufinanceiro.dto.MovimentacaoDto;
import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HomeService {

    private MovimentacaoService movimentacaoService;

    @Autowired
    public HomeService(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    public HomeDto showMovimentacaoByMonthYear(String year, String month, Long userId){

        HomeDto homeDto = new HomeDto();

        List<Movimentacao> movimentacaos = movimentacaoService.showAllDespesasByYearMonth(year, month, userId);

        if (movimentacaos.isEmpty()) { return null; }

        movimentacaos.forEach(movimentacao -> {
            if (movimentacao.getTipoDespesa().equals(TipoDespesa.ENTRADA)){
                    homeDto.getEntrada().add(convertMovimentacaoDto(movimentacao));
                    homeDto.setTotalEntrada(homeDto.getTotalEntrada() + movimentacao.getValor());
            }else  {
                    homeDto.getSaida().add(convertMovimentacaoDto(movimentacao));
                    homeDto.setTotalSaida(homeDto.getTotalSaida() + movimentacao.getValor());
            }
        });

        homeDto.setFluxoCaixa(homeDto.getTotalEntrada() - homeDto.getTotalSaida());
        return homeDto;
    }

    private MovimentacaoDto convertMovimentacaoDto(Movimentacao movimentacao) {
        return new MovimentacaoDto(String.valueOf(movimentacao.getId()),
                String.valueOf(movimentacao.getCategoria() != null ? movimentacao.getCategoria().getId() : ""),
                movimentacao.getCategoria() != null ? movimentacao.getCategoria().getDescricao() : "",
                movimentacao.getDescricao(),
                String.valueOf(movimentacao.getValor()),
                String.valueOf(movimentacao.getTipoDespesa()),
                movimentacao.getAno(),
                String.valueOf(movimentacao.getMes()));
    }

}
