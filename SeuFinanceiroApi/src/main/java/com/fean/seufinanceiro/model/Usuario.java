package com.fean.seufinanceiro.model;

import com.fean.seufinanceiro.security.enums.ProfileEnum;

import javax.persistence.*;
import java.util.List;


@Entity
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private ProfileEnum perfil;
    private List<Movimentacao> movimentacoes;
    private List<Categoria> categorias;

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String senha, ProfileEnum perfil, List<Movimentacao> movimentacoes, List<Categoria> categorias) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.movimentacoes = movimentacoes;
        this.categorias = categorias;
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
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProfileEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(ProfileEnum perfil) {
        this.perfil = perfil;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}

