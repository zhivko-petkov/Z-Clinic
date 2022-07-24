package com.zhivkoproject.ZClinic.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserChangePasswordBindingModel {
    @NotEmpty
    @Size(min = 5)
    private String oldPassword;

    @NotEmpty
    @Size(min = 5)
    private String newPassword;

    public UserChangePasswordBindingModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
