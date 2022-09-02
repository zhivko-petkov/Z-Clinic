package com.zhivkoproject.ZClinic.model.entity;

import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@Setter
public class Category extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;
}
