package info.seufinanceiro.model.enums;

public enum TypeTransaction {
    INPUT("ENTRADA"), OUTPUT("SAIDA");

    private String description;

    TypeTransaction(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
