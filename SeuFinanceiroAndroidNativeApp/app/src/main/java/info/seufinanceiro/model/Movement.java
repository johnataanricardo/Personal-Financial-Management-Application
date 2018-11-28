package info.seufinanceiro.model;

import info.seufinanceiro.model.Enums.MovementType;

public class Movement {
    private Long id;
    private String descricao;
    private Double valor;
    private MovementType tipoDespesa;
    private String ano;
    private Integer mes;
    private User usuario;
    private Category categoria;
    private MovementData data;

    public Movement() { }

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public MovementType getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(MovementType tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public MovementData getData() {
        return data;
    }

    public void setData(MovementData data) {
        this.data = data;
    }
}
