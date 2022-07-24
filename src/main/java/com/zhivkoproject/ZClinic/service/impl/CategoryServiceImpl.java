package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import com.zhivkoproject.ZClinic.repository.CategoryRepository;
import com.zhivkoproject.ZClinic.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if(categoryRepository.count() > 0){
            return;
        }
        Arrays.stream(CategoryEnum.values())
                .forEach(categoryNameEnum -> {
                    Category category = new Category();
                    category.setCategoryEnum(categoryNameEnum);
                    categoryRepository.save(category);
                });
    }

    @Override
    public Category findCategory(CategoryEnum category) {

        return categoryRepository.findCategoryByCategoryEnum(category);
    }
}
