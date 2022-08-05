package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class MedicalTestAddBindingModel {

    @NotEmpty
    @Size(min = 3)
    private String name;

    @NotNull
    private CategoryEnum category;

    @NotNull
    @Size(min = 3)
    private String material;

    @Positive
    @NotNull
    private BigDecimal price;

    private String addedByUsername;

    private boolean isDelay;

    public MedicalTestAddBindingModel() {
    }

    public boolean isDelay() {
        return isDelay;
    }

    public void setDelay(boolean delay) {
        isDelay = delay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public String getAddedByUsername() {
        return addedByUsername;
    }

    public void setAddedByUsername(String addedByUsername) {
        this.addedByUsername = addedByUsername;
    }
}
