package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.Order;
import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

public class ResultServiceModel {
    private Long id;
    private MedicalTestServiceModel test;
    private BigDecimal result;
    private String fullNameOfUser;

    public ResultServiceModel() {
    }

    public MedicalTestServiceModel getTest() {
        return test;
    }

    public void setTest(MedicalTestServiceModel test) {
        this.test = test;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullNameOfUser() {
        return fullNameOfUser;
    }

    public void setFullNameOfUser(String fullNameOfUser) {
        this.fullNameOfUser = fullNameOfUser;
    }
}
