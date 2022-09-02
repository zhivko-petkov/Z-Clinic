package com.zhivkoproject.ZClinic.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class SubscriptionAddModel {
    @Email
    @NotNull
    @NotEmpty
    private String email;

}
