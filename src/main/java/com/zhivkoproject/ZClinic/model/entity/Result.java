package com.zhivkoproject.ZClinic.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table
@Entity(name = "results")
@NoArgsConstructor
@Data
public class Result extends BaseEntity{
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Test test;

    @Column(nullable = true)
    private BigDecimal result;

    @ManyToOne
    private Order order;

    @ManyToOne(optional = false)
    private User user;

}
