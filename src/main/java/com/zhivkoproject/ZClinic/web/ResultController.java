package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.binding.ResultAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserEditBindingModel;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.service.OrderService;
import com.zhivkoproject.ZClinic.service.ResultService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/result")
public class ResultController {
    private final ResultService resultService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @GetMapping("/order/{id}")
    public String index(@PathVariable Long id,
                        @AuthenticationPrincipal UserDetails userDetails,
                        Model model){
        List<ResultServiceModel> resultsOrder = orderService.getOrderResults(id);
        model.addAttribute("resultsOrder", resultsOrder);

        return "results-home";
    }

    @ModelAttribute
    public ResultAddBindingModel resultAddBindingModel(){
        return new ResultAddBindingModel();
    }

    @GetMapping("/order/add/{id}")
    public String addResult(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        List<ResultServiceModel> results = orderService.getOrderResults(id);
        /*
        for (int i = 0; i < results.size(); i++) {
            resultAddBindingModel.addResult(new ResultServiceModel());
        }*/
        //this.resultAddBindingModel().setResultsAdd(results);
        ResultAddBindingModel resultAddBindingModel = new ResultAddBindingModel();
        resultAddBindingModel.setResultsAdd(results);
        model.addAttribute("form", resultAddBindingModel);
        //model.addAttribute("results", results);

        return "results-add-edit";

    }


    @PostMapping("/save")
    public String saveResults(@ModelAttribute ResultAddBindingModel results,
                            Model model) {

        orderService.setResult(results);

        return "redirect:/orders";
    }


}
