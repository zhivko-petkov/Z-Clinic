package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.*;
import com.zhivkoproject.ZClinic.model.service.CartServiceModel;
import com.zhivkoproject.ZClinic.repository.*;
import com.zhivkoproject.ZClinic.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ResultRepository resultRepository;

    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, TestRepository testRepository, UserRepository userRepository, OrderRepository orderRepository, ResultRepository resultRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.resultRepository = resultRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addMedicalTestsInCart(Long medicalTestId, String username) {
        Cart cart = cartRepository.findByCreatedByUsername(username).orElse(null);
        Test addedTest = testRepository.findById(medicalTestId).orElse(null);

        if (cart != null) {
            List<Test> testInCart = cart.getTests();
            if (!testInCart.contains(addedTest)) {
                testInCart.add(addedTest);
            }
            cart.setTests(testInCart);
        } else {
            cart = new Cart();
            List<Test> testInCart = new ArrayList<>();
            testInCart.add(addedTest);
            cart.setTests(testInCart);
            //check
            cart.setUser(userRepository.findByUsername(username).orElse(null));
            //cart.setCreatedBy(userRepository.findByUsername(username).orElse(null));
        }

        cartRepository.save(cart);

    }


    @Override
    public CartServiceModel findCartByUser(String username) {
        Cart currentCart = cartRepository.findByCreatedByUsername(username).orElse(null);

        if (currentCart != null)
            return modelMapper.map(currentCart, CartServiceModel.class);

        return new CartServiceModel();
    }

    @Override
    public void deleteMedicalTestInCart(Long id, String name) {
        Cart cart = cartRepository.findByCreatedByUsername(name).orElse(null);
        Test removeTest = testRepository.findById(id).orElse(null);

        List<Test> testInCart = cart.getTests();
        testInCart.remove(removeTest);
        cart.setTests(testInCart);

        cartRepository.save(cart);
    }

    @Override
    public void makeOrder(Long cartId, String username) {
        Cart cart = cartRepository.findByCreatedByUsername(username).orElse(null);
        User user = userRepository.findByUsername(username).orElse(null);

        if (cart != null) {
            Order order = new Order();
            //order.setCart(cart);
            order.setCreatedOn(LocalDateTime.now());
            order.setUser(user);


            BigDecimal totalSum = new BigDecimal(0);
            for (Test test : cart.getTests()) {
                totalSum = totalSum.add(test.getPrice());
            }
            order.setTotalPrice(totalSum);

            //check in PAYMENT SERVICE
            order.setPaid(true);

            orderRepository.save(order);


            List<Order> addedOrders = orderRepository.findOrdersByUserId(user.getId());
            Order newOrder = addedOrders.get(addedOrders.size() - 1);

            List<Result> resultList = new ArrayList<>();
            for (Test test : cart.getTests()) {
                Result currentResult = new Result();
                currentResult.setTest(test);
                currentResult.setUser(user);
                currentResult.setOrder(newOrder);
                resultRepository.save(currentResult);
            }

            List<Result> newResults = resultRepository.findResultByOrder_Id(newOrder.getId());
            newOrder.setResults(newResults);
            orderRepository.save(newOrder);

        }
    }

    @Override
    public void removeCart(Long cartId, String username) {
        Cart cart = cartRepository.findByCreatedByUsername(username).orElse(null);
        cartRepository.delete(cart);
        //cartRepository.delete(cart);
    }

    @Override
    public void deleteTest(Long id) {
        List<Cart> cartList = cartRepository.findAll();
        Test test = testRepository.findById(id).orElse(null);
        for (Cart cart : cartList) {
            if (cart.getTests().contains(test)) {
                List<Test> testList = cart.getTests();
                int indexOf = 0;
                for (int i = 0; i < testList.size(); i++) {
                    if (testList.get(i).getId() == id) {
                        indexOf = i;
                    }
                }
                testList.remove(indexOf);
                cart.setTests(testList);
                cartRepository.save(cart);
            }
        }
    }


}
