package com.zhivkoproject.ZClinic.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity{

    @ManyToOne(optional = false)
    private User createdBy;

    @ManyToOne(optional = false)
    private User buyer;

    @ManyToMany
    private List<Test> tests;

    public Cart() {
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
