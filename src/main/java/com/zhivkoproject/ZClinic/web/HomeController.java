package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;
    private final TestService testService;

    public HomeController(UserService userService, TestService testService) {
        this.userService = userService;
        this.testService = testService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) throws IOException, InterruptedException {


        if(principal != null){
            UserServiceModel userServiceModel = userService.findUser(principal.getName());
            model.addAttribute("userPhoto", userServiceModel.getImageUrl());
        }

        model.addAttribute("newNationalCases",testService.getCovidNationalCases());
        model.addAttribute("allNationalCases", testService.getTotalCovidNationalCases());

        return "index";
    }
}
