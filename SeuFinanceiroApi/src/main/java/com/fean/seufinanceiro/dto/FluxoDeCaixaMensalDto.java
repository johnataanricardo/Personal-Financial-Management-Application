package com.fean.seufinanceiro.dto;

public class FluxoDeCaixaMensalDto {

    private Integer month;
    private Double fluxoDeCaixa;


    public FluxoDeCaixaMensalDto() {}

    public FluxoDeCaixaMensalDto(Integer month) {
        this.month = month;
    }

    public Integer getMonth() {
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
