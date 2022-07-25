package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.service.CartServiceModel;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;

public interface CartService {
    void addMedicalTestsInCart(Long medicalTestId, String username);

    CartServiceModel findCartByUser(String username);

    void deleteMedicalTestInCart(Long id, String name);
}
