package com.fean.seufinanceiro.dto;

import javax.validation.constraints.NotEmpty;

public class UsuarioDto {

    private String id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String email;
    private String senha;

    public UsuarioDto() {
    }

    public UsuarioDto(String id, @NotEmpty String nome, @NotEmpty String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

}
