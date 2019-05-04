package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Order;
import com.sjsu.project.cmpe202.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("FROM OrderItem o WHERE o.order.id = :orderId")
    List<OrderItem> findOrderItemsByOrder(@Param("orderId") Integer orderId);

    List<OrderItem> findOrderItemsByOrder(List<Order> orders);

    OrderItem findOrderItemById(int id);
}
