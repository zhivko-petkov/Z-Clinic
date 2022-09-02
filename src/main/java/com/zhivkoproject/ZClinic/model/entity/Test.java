package com.zhivkoproject.ZClinic.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tests")
@NoArgsConstructor
@Data
public class Test extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean isOffer;

    @Column
    private boolean isDelay;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Result> results;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User addedBy;


}
