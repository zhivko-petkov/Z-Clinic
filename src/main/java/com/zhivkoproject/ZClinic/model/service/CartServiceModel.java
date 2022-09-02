package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Data
public class CartServiceModel {
    private Long id;
    private List<MedicalTestServiceModel> tests;
    private UserServiceModel createdBy;
    private UserServiceModel buyer;


}
