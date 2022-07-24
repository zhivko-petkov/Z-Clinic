package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.entity.User;
import java.math.BigDecimal;

public class MedicalTestServiceModel {
    private Long id;
    private String name;
    private Category category;
    private String material;
    private BigDecimal price;
    private User addedBy;

    public MedicalTestServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }
}
