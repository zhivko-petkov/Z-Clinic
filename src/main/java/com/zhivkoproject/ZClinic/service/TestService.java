package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.binding.MedicalTestAddBindingModel;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;

import java.io.IOException;
import java.util.List;

public interface TestService {
    List<MedicalTestServiceModel> getAllMedicalTests();

    void addMedicalTest(MedicalTestAddBindingModel medicalTestAddBindingModel);

    int getCovidNationalCases() throws IOException, InterruptedException;

    int getTotalCovidNationalCases() throws IOException, InterruptedException;
}
