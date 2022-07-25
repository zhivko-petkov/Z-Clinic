package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.Cart;
import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.service.CartServiceModel;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.repository.CartRepository;
import com.zhivkoproject.ZClinic.repository.TestRepository;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, TestRepository testRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addMedicalTestsInCart(Long medicalTestId, String username) {
        Cart cart = cartRepository.findByCreatedByUsername(username).orElse(null);
        Test addedTest = testRepository.findById(medicalTestId).orElse(null);
        if(cart != null){
            List<Test> testInCart = cart.getTests();
            if(!testInCart.contains(addedTest)){
                testInCart.add(addedTest);
            }
            cart.setTests(testInCart);
        } else {
            cart = new Cart();
            List<Test> testInCart = new ArrayList<>();
            testInCart.add(addedTest);
            cart.setTests(testInCart);
            //check
            cart.setBuyer(userRepository.findByUsername(username).orElse(null));
            cart.setCreatedBy(userRepository.findByUsername(username).orElse(null));
        }

        cartRepository.save(cart);
    }


    @Override
    public CartServiceModel findCartByUser(String username) {
        Cart currentCart = cartRepository.findByCreatedByUsername(username).orElse(null);

        if(currentCart != null)
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
}
