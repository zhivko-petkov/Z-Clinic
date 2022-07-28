package com.zhivkoproject.ZClinic.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tests")
public class Test extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User addedBy;

    public Test() {
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
