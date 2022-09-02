package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Data
public class UserEditBindingModel {
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

    private String imageUrl;

    private List<UserRole> roles;

}

