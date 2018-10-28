package com.fean.seufinanceiro.dto;

import com.fean.seufinanceiro.model.enums.Meses;
import com.fean.seufinanceiro.model.enums.TipoDespesa;

public class DespesaDto {

    private String id;
    private String descricao;
    private String valor;
    private String tipoDespesa;
    private String ano;
    private String mes;

    public DespesaDto() {}

    public DespesaDto(String descricao, String valor, String tipoDespesa, String ano, String mes) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.ano = ano;
        this.mes = mes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(String tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
