package com.zhivkoproject.ZClinic.service;


import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;

public interface CategoryService {
    void initCategories();

    Category findCategory(CategoryEnum category);
}
