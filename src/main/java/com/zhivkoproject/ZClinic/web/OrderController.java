package com.zhivkoproject.ZClinic.web;
import com.zhivkoproject.ZClinic.model.binding.OrderAddBindingModel;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.service.OrderService;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TestService testService;



    @GetMapping()
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model) {

        List<OrderServiceModel> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);

        //orderService.deleteOrder(2L);


        return "orders-index";
    }

    @GetMapping("/loggedUser")
    public String userOrders(@AuthenticationPrincipal UserDetails userDetails,
                             Model model){
        List<OrderServiceModel> loggedUserOrders = orderService.getUserOrders(userDetails.getUsername());
        model.addAttribute("loggedUserOrders", loggedUserOrders);

        return "results-index";

    }
    @GetMapping("/{id}")
    public String userOrdersById(@PathVariable Long id, Model model){
        String username = userService.findUsernameById(id);
        List<OrderServiceModel> loggedUserOrders = orderService.getUserOrders(username);

        model.addAttribute("loggedUserOrders", loggedUserOrders);

        return "results-index";

    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Principal principal){

        orderService.deleteOrder(id);

        return "redirect:/orders";
    }



}
