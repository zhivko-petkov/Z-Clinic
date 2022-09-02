package com.zhivkoproject.ZClinic.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserChangePasswordBindingModel {
    @NotEmpty
    @Size(min = 5)
    private String oldPassword;

    @NotEmpty
    @Size(min = 5)
    private String newPassword;

}
