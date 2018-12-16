package com.fean.seufinanceiro.dtos;

public class TransactionDto {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String description;
    private String value;
    private String typeTransaction;
    private String year;
    private String month;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Long categoryId, String categoryName,
                          String description, String value, String typeTransaction,
                          String year, String month) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.value = value;
        this.typeTransaction = typeTransaction;
        this.year = year;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
