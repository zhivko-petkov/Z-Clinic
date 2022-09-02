package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserAddBindingModel {
    @NotEmpty
    @Size(min = 4)
    private String username;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;

    private String surname;

    @NotEmpty
    @Size(min = 2)
    private String address;

    @Email
    @NotEmpty
    private String email;

    private String additionalDetails;

    @NotEmpty
    @Size(min = 5)
    private String password;

    @NotEmpty
    @Size(min = 5)
    private String confirmPassword;

    @NotNull
    private UserRoleEnum role;

}
