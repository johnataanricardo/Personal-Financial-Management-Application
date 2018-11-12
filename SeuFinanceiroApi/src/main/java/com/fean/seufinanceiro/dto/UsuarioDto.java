package com.fean.seufinanceiro.dto;

import javax.validation.constraints.NotEmpty;

public class UsuarioDto {

    private String id;

    @NotEmpty
    private String nome;

    private String sobreNome;

    @NotEmpty
    private String cpf;
    private String estado;
    private String cidade;
    private String endereco;
    private String numero;

    @NotEmpty
    private String email;

    public UsuarioDto() {
    }

    public UsuarioDto(String id, String nome, String sobreNome, String cpf, String estado,
                      String cidade, String endereco, String numero, String email) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpf = cpf;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.numero = numero;
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

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
