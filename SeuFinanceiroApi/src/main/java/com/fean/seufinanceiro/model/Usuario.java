package com.fean.seufinanceiro.model;

import com.fean.seufinanceiro.security.enums.ProfileEnum;

import javax.persistence.*;
import java.util.List;


@Entity
public class Usuario {

    private Long id;
    private String nome;
    private String sobreNome;
    private String cpf;
    private String estado;
    private String cidade;
    private String endereco;
    private String numero;
    private String email;
    private String senha;
    private ProfileEnum perfil;
    private List<Movimentacao> movimentacoes;
    private List<Categoria> categorias;

    public Usuario() {}

    public Usuario(Long id, String nome, String sobreNome, String cpf, String estado, String cidade, String endereco, String numero, String email, String sennha, ProfileEnum profileEnum) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpf = cpf;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.numero = numero;
        this.email = email;
        this.senha = sennha;
        this.perfil = profileEnum;
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

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    @Column(unique = true, nullable = false)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

