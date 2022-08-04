package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.binding.NewsSearchBindingModel;
import com.zhivkoproject.ZClinic.model.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class NewsSpecification implements Specification<News> {

    private final NewsSearchBindingModel newsSearchBindingModel;

    public NewsSpecification(NewsSearchBindingModel newsSearchBindingModel) {
        this.newsSearchBindingModel = newsSearchBindingModel;
    }


    @Override
    public Predicate toPredicate(Root<News> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();

        if (newsSearchBindingModel.getTitle() != null && !newsSearchBindingModel.getTitle().equals("")) {
            p.getExpressions().add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("title"), newsSearchBindingModel.getTitle()))
            );
        }



        return p;
    }
}
