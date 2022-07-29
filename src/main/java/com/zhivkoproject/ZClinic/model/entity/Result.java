package com.zhivkoproject.ZClinic.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table
@Entity(name = "results")
public class Result extends BaseEntity{
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Test test;

    @Column(nullable = true)
    private BigDecimal result;

    @ManyToOne
    private Order order;

    @ManyToOne(optional = false)
    private User user;

    public Result() {
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
