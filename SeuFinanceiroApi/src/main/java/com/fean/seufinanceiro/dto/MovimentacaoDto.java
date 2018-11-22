package com.fean.seufinanceiro.dto;

public class MovimentacaoDto {

    private String id;
    private String idCategoria;
    private String nomeCategoria;
    private String descricao;
    private String valor;
    private String tipoDespesa;
    private String ano;
    private String mes;

    public MovimentacaoDto() {}

    public MovimentacaoDto(String id, String idCategoria, String nomeCategoria, String descricao, String valor, String tipoDespesa, String ano, String mes) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
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

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
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
