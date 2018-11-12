package com.fean.seufinanceiro.dto;

import com.fean.seufinanceiro.model.Movimentacao;

import java.util.List;

public class FluxoDeCaixaDto {

    private List<Movimentacao> movimentacaos;
    private String fluxoCaixa;

    public FluxoDeCaixaDto() {}

    public FluxoDeCaixaDto(List<Movimentacao> movimentacaos, String fluxoCaixa) {
        this.movimentacaos = movimentacaos;
        this.fluxoCaixa = fluxoCaixa;
    }

    public List<Movimentacao> getMovimentacaos() {
        return movimentacaos;
    }

    public void setMovimentacaos(List<Movimentacao> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }

    public String getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(String fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }
}
