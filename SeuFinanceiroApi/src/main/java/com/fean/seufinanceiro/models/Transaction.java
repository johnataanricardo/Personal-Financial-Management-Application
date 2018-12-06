package com.fean.seufinanceiro.models;

import com.fean.seufinanceiro.models.enums.TypeTransaction;
import javax.persistence.*;

@Entity
public class Transaction {

    private Long id;
    private String description;
    private Double value;
    private TypeTransaction typeTransaction;
    private String year;
    private Integer month;
    private User user;
    private Category category;

    public Transaction() { }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public String getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "transaction", cascade = CascadeType.ALL)
    public Category getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
