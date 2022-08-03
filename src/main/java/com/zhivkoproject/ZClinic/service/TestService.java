package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.binding.MedicalTestAddBindingModel;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;

import java.io.IOException;
import java.util.List;

public interface TestService {
    List<MedicalTestServiceModel> getAllMedicalTests();

    void addMedicalTest(MedicalTestAddBindingModel medicalTestAddBindingModel);

    void deleteTest(Long id);

    MedicalTestServiceModel findTestById(Long id);

    void editTest(MedicalTestAddBindingModel medicalTestEditBindingModel, Long id, String username);

    boolean isUniqueName(String name);

    void initTests();
}
