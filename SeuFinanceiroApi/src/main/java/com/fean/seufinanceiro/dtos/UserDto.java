package com.fean.seufinanceiro.dtos;

import javax.validation.constraints.NotEmpty;

public class UserDto {

    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    private String password;

    public UserDto() {
    }

    public UserDto(String id, @NotEmpty String name, @NotEmpty String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
