package info.seufinanceiro.model;

import java.util.List;

public class UserResponseData {
    private String token;
    private List errors;

    public UserResponseData(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List errors) {
        this.errors = errors;
    }
}
