package com.zhivkoproject.ZClinic.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubscriptionAddModel {
    @Email
    @NotNull
    @NotEmpty
    private String email;

    public SubscriptionAddModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
