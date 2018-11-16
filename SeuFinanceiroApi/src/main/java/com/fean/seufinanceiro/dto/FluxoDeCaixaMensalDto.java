package com.fean.seufinanceiro.dto;

import com.fean.seufinanceiro.model.enums.Meses;

public class FluxoDeCaixaMensalDto {

    private String month;
    private Double fluxoDeCaixa;


    public FluxoDeCaixaMensalDto() {}

    public FluxoDeCaixaMensalDto(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public Double getFluxoDeCaixa() {
        if (this.fluxoDeCaixa == null)
            this.fluxoDeCaixa = 0.0;
        return fluxoDeCaixa;
    }

    public void setFluxoDeCaixa(Double fluxoDeCaixa) {
        this.fluxoDeCaixa = fluxoDeCaixa;
    }


}
