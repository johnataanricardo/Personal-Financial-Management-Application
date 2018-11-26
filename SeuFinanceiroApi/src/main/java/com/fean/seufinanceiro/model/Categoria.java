package com.fean.seufinanceiro.model;

import javax.persistence.*;

@Entity
public class Categoria {

    private Long id;
    private String descricao;
    private Usuario usuario;
    private Movimentacao movimentacao;

    public Categoria() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getDescricao() {
        return descricao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Usuario getUsuario() {
        return usuario;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

}
