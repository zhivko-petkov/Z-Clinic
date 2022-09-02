package com.zhivkoproject.ZClinic.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserLoginBindingModel {
    @Size(min = 4)
    private String username;

    @Size(min = 5)
    private String password;

}
