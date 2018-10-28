package com.fean.seufinanceiro.dto;

import com.fean.seufinanceiro.model.Despesa;

import java.util.List;

public class FluxoDeCaixaDto {

    private List<Despesa> despesas;
    private String fluxoCaixa;

    public FluxoDeCaixaDto() {}

    public FluxoDeCaixaDto(List<Despesa> despesas, String fluxoCaixa) {
        this.despesas = despesas;
        this.fluxoCaixa = fluxoCaixa;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public String getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(String fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }
}
