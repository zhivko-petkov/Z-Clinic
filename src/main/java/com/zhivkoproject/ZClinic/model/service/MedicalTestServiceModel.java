package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class MedicalTestServiceModel {
    private Long id;
    private String name;
    private Category category;
    private String material;
    private BigDecimal price;
    private User addedBy;


}
