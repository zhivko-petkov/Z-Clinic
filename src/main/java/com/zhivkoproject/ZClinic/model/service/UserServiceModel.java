package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Data
public class UserServiceModel {
    private Long id;
    private String username;
    private String firstName;
    private String surname;
    private String address;
    private String email;
    private String additionalDetails;
    private String imageUrl;
    private List<UserRole> roles;

}
