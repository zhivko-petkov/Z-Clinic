package com.zhivkoproject.ZClinic.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity{
    @ManyToOne(optional = false)
    private User user;

    @ManyToMany
    private List<Test> tests;

    public Cart() {
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
