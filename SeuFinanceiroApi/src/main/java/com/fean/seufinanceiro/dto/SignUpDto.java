package com.fean.seufinanceiro.dto;

public class SignUpDto {

    private String nome;
    private String sobreNome;
    private String cpf;
    private String estado;
    private String cidade;
    private String endereco;
    private String numero;
    private String email;
    private String senha;

    public SignUpDto() {
    }

    public SignUpDto(String nome, String sobreNome, String cpf, String estado, String cidade, String endereco, String numero, String email, String senha) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpf = cpf;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.numero = numero;
        this.email = email;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
