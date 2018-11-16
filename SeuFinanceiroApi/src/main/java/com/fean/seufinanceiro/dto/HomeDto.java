package com.fean.seufinanceiro.dto;

import java.util.ArrayList;
import java.util.List;

public class HomeDto {

    private List<MovimentacaoDto> entrada;
    private List<MovimentacaoDto> saida;
    private Double totalEntrada;
    private Double totalSaida;
    private Double fluxoCaixa;


    public HomeDto() { }

    public HomeDto(List<MovimentacaoDto> entrada, List<MovimentacaoDto> saida, Double totalEntrada, Double totalSaida, Double fluxoCaixa) {
        this.entrada = entrada;
        this.saida = saida;
        this.totalEntrada = totalEntrada;
        this.totalSaida = totalSaida;
        this.fluxoCaixa = fluxoCaixa;
    }

    public List<MovimentacaoDto> getEntrada() {
        if(this.entrada == null)
            this.entrada  = new ArrayList<>();
        return entrada;
    }

    public List<MovimentacaoDto> getSaida() {
        if (this.saida == null)
             this.saida = new ArrayList<>();
        return saida;
    }

    public void setEntrada(List<MovimentacaoDto> entrada) {
        this.entrada = entrada;
    }

    public void setSaida(List<MovimentacaoDto> saida) {
        this.saida = saida;
    }

    public Double getTotalEntrada() {
        if (this.totalEntrada == null)
                this.totalEntrada = 0.0;
        return totalEntrada;
    }

    public void setTotalEntrada(Double totalEntrada) {
        if (this.totalSaida == null)
                this.totalSaida = 0.0;
        this.totalEntrada = totalEntrada;
    }

    public Double getTotalSaida() {
        if (this.totalSaida == null)
            this.totalSaida = 0.0;
        return totalSaida;
    }

    public void setTotalSaida(Double totalSaida) {
        if (this.totalEntrada == null)
            this.totalEntrada = 0.0;
        this.totalSaida = totalSaida;
    }

    public Double getFluxoCaixa() {
        return fluxoCaixa;
    }

    public void setFluxoCaixa(Double fluxoCaixa) {
        this.fluxoCaixa = fluxoCaixa;
    }
}
