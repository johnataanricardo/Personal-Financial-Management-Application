package info.seufinanceiro.model;

import java.util.List;

public class Data {
    private String token;
    private List<String> errors;

    public Data(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
