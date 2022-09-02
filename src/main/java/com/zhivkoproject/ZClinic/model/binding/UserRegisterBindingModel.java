package com.zhivkoproject.ZClinic.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserRegisterBindingModel {
    //check unique
    @NotEmpty
    @Size(min = 4)
    private String username;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;

    private String surname;

    @NotEmpty
    @Size(min = 2, max = 20)
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

}
