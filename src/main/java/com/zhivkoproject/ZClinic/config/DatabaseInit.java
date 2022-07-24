package com.zhivkoproject.ZClinic.config;

import com.zhivkoproject.ZClinic.service.CategoryService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;

    public DatabaseInit(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.initUserRoles();
        userService.initUser();
        categoryService.initCategories();
    }
}
