package com.fean.seufinanceiro.models;

import javax.persistence.*;

@Entity
public class Category {

    private Long id;
    private String description;
    private User user;
    private Transaction transaction;

    public Category() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Transaction getTransaction() {
        return transaction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
