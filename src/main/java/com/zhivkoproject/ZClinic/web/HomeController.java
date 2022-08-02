package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.service.NewsService;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final TestService testService;
    private final NewsService newsService;

    public HomeController(UserService userService, TestService testService, NewsService newsService) {
        this.userService = userService;
        this.testService = testService;
        this.newsService = newsService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) throws IOException, InterruptedException {


        if(principal != null){
            UserServiceModel userServiceModel = userService.findUser(principal.getName());
            model.addAttribute("userPhoto", userServiceModel.getImageUrl());
        }

        List<NewsServiceModel> news = newsService.getAllNews().subList(0, 3);
        model.addAttribute("allNews", news);

        return "index";
    }

    @GetMapping("/doctors")
    public String doctors(Model model, Principal principal){
        List<UserServiceModel> doctors = userService.getDoctors();

        model.addAttribute("doctors", doctors);
        return "doctors";
    }
}
