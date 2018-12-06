package com.fean.seufinanceiro.dtos;

import javax.validation.constraints.NotEmpty;

public class CategoryDto {

    private Long id;

    @NotEmpty
    private String description;

    public CategoryDto() {}

    public CategoryDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
