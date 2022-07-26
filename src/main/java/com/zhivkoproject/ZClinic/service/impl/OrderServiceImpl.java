package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.Order;
import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.OrderRepository;
import com.zhivkoproject.ZClinic.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<OrderServiceModel> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderServiceModel> orderServiceModels = new ArrayList<>();

        for (Order order : orders) {
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setPaid(order.isPaid());
            orderServiceModel.setCreatedOn(order.getCreatedOn());

            User orderUser = order.getUser();
            orderServiceModel.setUser(modelMapper.map(orderUser, UserServiceModel.class));

            orderServiceModel.setTotalPrice(order.getTotalPrice());

            List<ResultServiceModel> resultServiceModels = new ArrayList<>();
            for (Result result : order.getResults()) {
                ResultServiceModel resultServiceModel = new ResultServiceModel();

                Test currentTest = result.getTest();
                MedicalTestServiceModel medicalTestServiceModel = modelMapper.map(currentTest, MedicalTestServiceModel.class);
                resultServiceModel.setTest(medicalTestServiceModel);

                resultServiceModels.add(resultServiceModel);
            }
            orderServiceModel.setResults(resultServiceModels);

            orderServiceModels.add(orderServiceModel);
        }

        return orderServiceModels;
    }
}
