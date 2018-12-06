package com.fean.seufinanceiro.models;

import com.fean.seufinanceiro.security.enums.ProfileEnum;
import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private ProfileEnum profile;
    private List<Transaction> transactions;
    private List<Category> categories;

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProfileEnum getProfile() {
        return profile;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Category> getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}

