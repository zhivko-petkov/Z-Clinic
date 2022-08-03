package com.zhivkoproject.ZClinic.config;

import com.zhivkoproject.ZClinic.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final NewsService newsService;
    private final TestService testService;
    private final SubscriptionService subscriptionService;

    public DatabaseInit(UserService userService, CategoryService categoryService, NewsService newsService, TestService testService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.newsService = newsService;
        this.testService = testService;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.initUserRoles();
        userService.initUser();
        categoryService.initCategories();
        newsService.initNews();
        testService.initTests();


    }
}
