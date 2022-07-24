package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
