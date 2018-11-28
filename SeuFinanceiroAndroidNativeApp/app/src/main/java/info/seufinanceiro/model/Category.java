package info.seufinanceiro.model;

public class Category {
    private Long id;
    private String descricao;
    private User usuario;
    private Movement movimentacao;

    public Category() {
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

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Movement getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movement movimentacao) {
        this.movimentacao = movimentacao;
    }
}
