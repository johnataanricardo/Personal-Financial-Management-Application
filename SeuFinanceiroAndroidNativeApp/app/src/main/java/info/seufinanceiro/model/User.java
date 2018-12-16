package info.seufinanceiro.model;

import java.util.List;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Transaction> movimentacaos;
    private List<Category> categories;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Transaction> getMovimentacaos() {
        return movimentacaos;
    }

    public void setMovimentacaos(List<Transaction> movimentacaos) {
        this.movimentacaos = movimentacaos;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
