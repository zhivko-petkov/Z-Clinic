package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

   @Query("select o from Order o where o.user.id = ?1")
   List<Order> findOrdersByUserId(Long id);

   List<Order> findOrdersByUserUsername(String username);

   Order findOrderById(Long id);
}
