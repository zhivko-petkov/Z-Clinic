package com.zhivkoproject.ZClinic.model.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {
    private Long id;

    private UserServiceModel user;

    private LocalDateTime createdOn;

    private BigDecimal totalPrice;

    private List<ResultServiceModel> results;

    private boolean isPaid;

    public OrderServiceModel() {
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ResultServiceModel> getResults() {
        return results;
    }

    public void setResults(List<ResultServiceModel> results) {
        this.results = results;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
