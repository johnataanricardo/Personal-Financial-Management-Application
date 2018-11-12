package com.fean.seufinanceiro.model;

import javax.persistence.*;

@Entity
public class Categoria {

    private Long id;

    private String descricao;

    private Usuario usuario;

    public Categoria() {}

    public Categoria(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
