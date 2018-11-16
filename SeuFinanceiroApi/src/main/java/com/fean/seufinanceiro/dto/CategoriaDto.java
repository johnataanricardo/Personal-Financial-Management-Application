package com.fean.seufinanceiro.dto;

import javax.validation.constraints.NotEmpty;

public class CategoriaDto {

    private Long id;

    @NotEmpty
    private String descricao;

    public CategoriaDto() {}

    public CategoriaDto(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
