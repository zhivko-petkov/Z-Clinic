package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;
import java.util.List;

public class CartServiceModel {
    private Long id;
    private List<MedicalTestServiceModel> tests;
    private UserServiceModel createdBy;
    private UserServiceModel buyer;

    public CartServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MedicalTestServiceModel> getTests() {
        return tests;
    }

    public void setTests(List<MedicalTestServiceModel> tests) {
        this.tests = tests;
    }

    public UserServiceModel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserServiceModel createdBy) {
        this.createdBy = createdBy;
    }

    public UserServiceModel getBuyer() {
        return buyer;
    }

    public void setBuyer(UserServiceModel buyer) {
        this.buyer = buyer;
    }
}
