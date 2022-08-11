package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.OrderAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.ResultAddBindingModel;
import com.zhivkoproject.ZClinic.model.entity.Order;
import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.OrderRepository;
import com.zhivkoproject.ZClinic.repository.ResultRepository;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ResultRepository resultRepository,
                            UserRepository userRepository,
                            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderServiceModel> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderServiceModel> orderServiceModels = new ArrayList<>();

        for (Order order : orders) {
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setId(order.getId());
            orderServiceModel.setPaid(order.isPaid());
            orderServiceModel.setCreatedOn(order.getCreatedOn());

            User orderUser = order.getUser();
            orderServiceModel.setUser(modelMapper.map(orderUser, UserServiceModel.class));

            orderServiceModel.setTotalPrice(order.getTotalPrice());

            List<ResultServiceModel> resultServiceModels = new ArrayList<>();

            boolean isReady = true;
            for (Result result : order.getResults()) {
                if (result.getResult() == null){
                    isReady = false;
                }
                ResultServiceModel resultServiceModel = new ResultServiceModel();

                Test currentTest = result.getTest();
                MedicalTestServiceModel medicalTestServiceModel = modelMapper.map(currentTest, MedicalTestServiceModel.class);
                resultServiceModel.setTest(medicalTestServiceModel);
                resultServiceModel.setId(result.getId());

                resultServiceModels.add(resultServiceModel);
            }
            orderServiceModel.setResults(resultServiceModels);
            orderServiceModel.setReady(isReady);

            orderServiceModels.add(orderServiceModel);
        }

        return orderServiceModels;
    }

    @Override
    public List<OrderServiceModel> getUserOrders(String username) {
        List<Order> orders = orderRepository.findOrdersByUserUsername(username);
        List<OrderServiceModel> orderServiceModels = new ArrayList<>();

        for (Order order : orders) {
            OrderServiceModel orderServiceModel = new OrderServiceModel();
            orderServiceModel.setId(order.getId());
            orderServiceModel.setPaid(order.isPaid());
            orderServiceModel.setCreatedOn(order.getCreatedOn());

            User orderUser = order.getUser();
            orderServiceModel.setUser(modelMapper.map(orderUser, UserServiceModel.class));

            orderServiceModel.setTotalPrice(order.getTotalPrice());

            List<ResultServiceModel> resultServiceModels = new ArrayList<>();

            boolean isReady = true;
            for (Result result : order.getResults()) {
                if (result.getResult() == null){
                    isReady = false;
                }
                ResultServiceModel resultServiceModel = new ResultServiceModel();

                Test currentTest = result.getTest();
                MedicalTestServiceModel medicalTestServiceModel = modelMapper.map(currentTest, MedicalTestServiceModel.class);
                resultServiceModel.setTest(medicalTestServiceModel);
                resultServiceModel.setId(result.getId());

                resultServiceModels.add(resultServiceModel);
            }
            orderServiceModel.setResults(resultServiceModels);
            orderServiceModel.setReady(isReady);

            orderServiceModels.add(orderServiceModel);
        }

        return orderServiceModels;

    }

    @Override
    public List<ResultServiceModel> getOrderResults(Long id) {
        Order order = orderRepository.findOrderById(id);

        List<ResultServiceModel> resultServiceModels = new ArrayList<>();

        boolean isReady = true;
        for (Result result : order.getResults()) {
            if (result.getResult() == null){
                isReady = false;
            }
            ResultServiceModel resultServiceModel = new ResultServiceModel();

            Test currentTest = result.getTest();
            MedicalTestServiceModel medicalTestServiceModel = modelMapper.map(currentTest, MedicalTestServiceModel.class);
            resultServiceModel.setTest(medicalTestServiceModel);
            resultServiceModel.setId(result.getId());
            resultServiceModel.setResult(result.getResult());
            resultServiceModel.setFullNameOfUser(order.getUser().getFirstName() + " " + order.getUser().getSurname());
            resultServiceModels.add(resultServiceModel);

        }

        return resultServiceModels;

    }

    @Override
    public void setResult(ResultAddBindingModel resultAddBindingModel) {

        for (ResultServiceModel resultServiceModel : resultAddBindingModel.getResultsAdd()) {
            Result result = resultRepository.findById(resultServiceModel.getId()).orElse(null);
            result.setResult(resultServiceModel.getResult());
            resultRepository.save(result);
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        //User user = userRepository.findById(order.getUser().getId()).orElse(null);

        /*List<Order> orderList = user.getOrders();
        orderList.remove(order);
        user.setOrders(orderList);

        userRepository.save(user);

        User userC = userRepository.findById(order.getUser().getId()).orElse(null);
        System.out.println(userC.getOrders().size());*/
        orderRepository.delete(order);


    }

    @Override
    public void deleteResultInOrders(Long id) {
        List<Result> results = resultRepository.findByTestId(id);

        for (Result result : results) {
            resultRepository.delete(result);
        }



    }

    @Override
    public void addOrder(OrderAddBindingModel orderAddBindingModel) {

    }
}
