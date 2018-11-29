package info.seufinanceiro.model;

import java.util.List;

public class User {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Movement> movimentacaos;
    private List<Category> categories;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Movement> getMovimentacaos() {
        return movimentacaos;
    }

    public void setMovimentacaos(List<Movement> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
