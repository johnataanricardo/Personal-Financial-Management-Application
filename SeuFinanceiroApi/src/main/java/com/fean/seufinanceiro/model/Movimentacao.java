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
    private Categoria categoria;

    public Movimentacao() { }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValor() {
        return valor;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public String getAno() {
        return ano;
    }

    public Integer getMes() {
        return mes;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Usuario getUsuario() {
        return usuario;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "movimentacao", cascade = CascadeType.ALL)
    public Categoria getCategoria() {
        return categoria;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
