package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.createdBy.username = ?1")
    Optional<Cart> findByCreatedByUsername(String username);
}
