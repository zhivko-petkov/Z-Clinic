package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import java.util.List;

public class OrderAddBindingModel {
    private List<MedicalTestServiceModel> tests;

    public OrderAddBindingModel() {
    }

    public List<MedicalTestServiceModel> getTests() {
        return tests;
    }

    public void setTests(List<MedicalTestServiceModel> tests) {
        this.tests = tests;
    }
}
