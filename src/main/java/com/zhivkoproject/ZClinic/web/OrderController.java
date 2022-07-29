package com.zhivkoproject.ZClinic.web;
import com.zhivkoproject.ZClinic.model.binding.OrderAddBindingModel;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.service.OrderService;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
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
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TestService testService;

    public OrderController(OrderService orderService, ModelMapper modelMapper, UserService userService, TestService testService) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.testService = testService;
    }
//fixec security bug for users edit


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

    //TODO: RESULTS

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Principal principal){

        orderService.deleteOrder(id);

        return "redirect:/orders";
    }
/*
    @GetMapping("/add/{id}")
    public String add(@PathVariable Long id, Model model) {
        model.addAttribute("userId", id);
        model.addAttribute("allTests", testService.getAllMedicalTests());
        return "orders-add";
    }

    @PostMapping("/add/{id}")
    public String add(@Valid OrderAddBindingModel orderAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("orderAddBindingModel", orderAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddBindingModel",
                    bindingResult);
        }
        orderService.addOrder(orderAddBindingModel);
        return "orders-add";
    }

*/
/*
    @GetMapping("/result/add/{id}")
    public String addResult(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        List<ResultServiceModel> resultsOrder = orderService.getOrderResults(id);
        model.addAttribute("resultsOrder", resultsOrder);
        model.addAttribute("resultAddBindingModel", resultAddBindingModel());

        return "results-add-edit";

    }*/
/*
    @PostMapping("/result/add/{id}")
    public String addResult(@PathVariable Long id,
                       @Valid ResultAddBindingModel resultAddBindingModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       @AuthenticationPrincipal UserDetails userDetails){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("resultAddBindingModel", resultAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.resultAddBindingModel",
                    bindingResult);
            return "redirect:orders";
        }

        orderService.setResult(id, resultAddBindingModel);

        return "redirect:/orders/result/add/{id}";
    }
*/




}
