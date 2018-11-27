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
    private List<Movimentacao> movimentacaos;
    private List<Categoria> categories;

    public Usuario() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getNome() {
        return nome;
    }

    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProfileEnum getPerfil() {
        return perfil;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Movimentacao> getMovimentacaos() {
        return movimentacaos;
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Categoria> getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfil(ProfileEnum perfil) {
        this.perfil = perfil;
    }

    public void setMovimentacaos(List<Movimentacao> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }

    public void setCategories(List<Categoria> categories) {
        this.categories = categories;
    }

}

