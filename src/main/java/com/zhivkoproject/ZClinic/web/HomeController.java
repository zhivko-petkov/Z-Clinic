package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.binding.NewsAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.SubscriptionAddModel;
import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.service.NewsService;
import com.zhivkoproject.ZClinic.service.SubscriptionService;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final TestService testService;
    private final NewsService newsService;
    private final SubscriptionService subscriptionService;

    public HomeController(UserService userService, TestService testService, NewsService newsService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.testService = testService;
        this.newsService = newsService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) throws IOException, InterruptedException {


        if(principal != null){
            UserServiceModel userServiceModel = userService.findUser(principal.getName());
            model.addAttribute("userPhoto", userServiceModel.getImageUrl());
        }

        List<NewsServiceModel> news = newsService.getAllNews();
        if (news.size() > 2){
            news = news.subList(0, 3);
        }

        for (NewsServiceModel newsServiceModel : news) {
            newsServiceModel.setContent(newsServiceModel.getContent().substring(0, 50) + "..");
        }
        model.addAttribute("allNews", news);

        return "index";
    }

    @GetMapping("/doctors")
    public String doctors(Model model, Principal principal){
        List<UserServiceModel> doctors = userService.getDoctors();

        model.addAttribute("doctors", doctors);
        return "doctors";
    }

    @ModelAttribute("subscritpionAddBindingModel")
    public SubscriptionAddModel subscriptionAddBindingModel() {
        return new SubscriptionAddModel();
    }

    @PostMapping("/subscribe")
    public String subscribe(@Valid SubscriptionAddModel subscriptionAddModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("subscriptionAddModel",subscriptionAddModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.subscriptionAddModel",
                    bindingResult);
            return "redirect:/";
        }

        subscriptionService.saveSubscription(subscriptionAddModel);
        return "redirect:/subscription";
    }

    @GetMapping("/subscription")
    public String subscription(){
        return "subscription";
    }
}
