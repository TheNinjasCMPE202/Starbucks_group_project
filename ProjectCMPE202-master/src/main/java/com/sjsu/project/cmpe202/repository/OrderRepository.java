package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("FROM Order o WHERE o.user.id = :userId")
    List<Order> findOrdersByUser(@Param("userId") Integer userId);
}
