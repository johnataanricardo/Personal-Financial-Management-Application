package com.fean.seufinanceiro.dtos;

import java.util.ArrayList;
import java.util.List;

public class FluxoDeCaixaDto {

    private List<TransactionDto> movimentacaos = new ArrayList<>();
    private String fluxoCaixa;

    public FluxoDeCaixaDto() {}

    public FluxoDeCaixaDto(List<TransactionDto> movimentacaos, String fluxoCaixa) {
        this.movimentacaos = movimentacaos;
        this.fluxoCaixa = fluxoCaixa;
    }

    public List<TransactionDto> getMovimentacaos() {
        return movimentacaos;
    }

    public void setMovimentacaos(List<TransactionDto> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }

    public String getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(String fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }
}
