package com.fean.seufinanceiro.dto;

import com.fean.seufinanceiro.model.Movimentacao;

import java.util.ArrayList;
import java.util.List;

public class FluxoDeCaixaDto {

    private List<MovimentacaoDto> movimentacaos = new ArrayList<>();
    private String fluxoCaixa;

    public FluxoDeCaixaDto() {}

    public FluxoDeCaixaDto(List<MovimentacaoDto> movimentacaos, String fluxoCaixa) {
        this.movimentacaos = movimentacaos;
        this.fluxoCaixa = fluxoCaixa;
    }

    public List<MovimentacaoDto> getMovimentacaos() {
        return movimentacaos;
    }

    public void setMovimentacaos(List<MovimentacaoDto> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }

    public String getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(String fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }
}
