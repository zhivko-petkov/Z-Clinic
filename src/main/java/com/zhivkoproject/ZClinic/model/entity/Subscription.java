package com.zhivkoproject.ZClinic.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@Data
@Setter
public class Subscription extends BaseEntity {
    @Column(nullable = false)
    private String email;

    private LocalDate startDate;

}
