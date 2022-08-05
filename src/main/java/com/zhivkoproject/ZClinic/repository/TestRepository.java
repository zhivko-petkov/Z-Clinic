package com.zhivkoproject.ZClinic.repository;

import com.zhivkoproject.ZClinic.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("select t from Test t where t.name = ?1")
    Optional<Test> findByName(String name);


    @Query("select t from Test t where t.isOffer = ?1 and t.isDelay = false")
    List<Test> findAllByOfferAndDelayFalse(boolean isOffer);
}
