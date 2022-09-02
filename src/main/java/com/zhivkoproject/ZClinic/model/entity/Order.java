package com.zhivkoproject.ZClinic.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Data
public class Order extends BaseEntity{
    @ManyToOne
    private User user;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Result> results;

    private boolean isPaid;

    private boolean isReady;


}
