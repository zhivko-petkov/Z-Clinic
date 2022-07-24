package com.zhivkoproject.ZClinic.model.binding;

import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    @Size(min = 4)
    private String username;

    @Size(min = 5)
    private String password;

    public UserLoginBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
