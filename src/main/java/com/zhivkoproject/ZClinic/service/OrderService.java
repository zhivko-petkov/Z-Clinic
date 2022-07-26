package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;

import java.util.List;

public interface OrderService {
    List<OrderServiceModel> getAllOrders();
}
