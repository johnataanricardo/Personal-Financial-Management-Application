package com.fean.seufinanceiro.model;

import com.fean.seufinanceiro.model.enums.TipoDespesa;

import javax.persistence.*;

@Entity
public class Movimentacao {

    private Long id;
    private String descricao;
    private Double valor;
    private TipoDespesa tipoDespesa;
    private String ano;
    private Integer mes;
    private Usuario usuario;

    public Movimentacao() { }

    public Movimentacao(Long id, String descricao, Double valor, TipoDespesa tipoDespesa, String ano, Integer mes) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipoDespesa = tipoDespesa;
        this.ano = ano;
        this.mes = mes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
