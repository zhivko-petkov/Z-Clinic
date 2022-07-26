package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.service.CartServiceModel;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    //fixec security bug for users edit


    @GetMapping()
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model) {

        List<OrderServiceModel> orders = orderService.getAllOrders();
        for (OrderServiceModel order : orders) {
            System.out.println(order.getUser().getFirstName());
        }
        return "/";
    }


}
