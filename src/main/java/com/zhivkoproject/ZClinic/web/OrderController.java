package com.zhivkoproject.ZClinic.web;
import com.zhivkoproject.ZClinic.model.service.OrderServiceModel;
import com.zhivkoproject.ZClinic.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    //TODO: RESULTS


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
