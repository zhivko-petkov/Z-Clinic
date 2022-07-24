package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;

import java.util.List;

public interface CartService {
    void addMedicalTestsInCart(List<MedicalTestServiceModel> medicalTestServiceModels);
    void removeMedicalTestFromCart(MedicalTestServiceModel medicalTestServiceModel);

}
