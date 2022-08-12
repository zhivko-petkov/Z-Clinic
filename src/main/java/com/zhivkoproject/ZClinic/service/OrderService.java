package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.binding.OrderAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.ResultAddBindingModel;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;

import java.util.List;

public interface OrderService {
    List<OrderServiceModel> getAllOrders();

    List<OrderServiceModel> getUserOrders(String username);

    List<ResultServiceModel> getOrderResults(Long id);

    void setResult(ResultAddBindingModel resultAddBindingModel);

    void deleteOrder(Long id);

    void deleteResultInOrders(Long id);

}
