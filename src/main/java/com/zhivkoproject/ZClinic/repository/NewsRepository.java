package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
