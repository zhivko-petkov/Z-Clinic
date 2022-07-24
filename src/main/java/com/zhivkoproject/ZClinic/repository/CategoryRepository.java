package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryEnum(CategoryEnum categoryEnum);
}
