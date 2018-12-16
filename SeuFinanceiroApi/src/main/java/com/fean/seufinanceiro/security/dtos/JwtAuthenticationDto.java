package com.fean.seufinanceiro.security.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {

    private String email;
    private String password;

    public JwtAuthenticationDto() {
    }

    @NotEmpty(message = "Email can not be empty.")
    @Email(message = "Email invalid.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password can not be empty.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationRequestDto [email=" + email + ", password=" + password + "]";
    }

}
