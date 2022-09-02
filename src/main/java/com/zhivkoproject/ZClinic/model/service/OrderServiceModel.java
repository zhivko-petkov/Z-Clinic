package com.zhivkoproject.ZClinic.model.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderServiceModel {
    private Long id;

    private UserServiceModel user;

    private LocalDateTime createdOn;

    private BigDecimal totalPrice;

    private List<ResultServiceModel> results;

    private boolean isPaid;

    private boolean isReady;


    public boolean isPaid() {
        return isPaid;
    }


}
