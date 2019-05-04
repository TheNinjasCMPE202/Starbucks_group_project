package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("FROM CartItem o WHERE o.cart.id = :cartId")
    List<CartItem> findCartItemsByCart(@Param("cartId") Integer cartId);

    CartItem findCartItemById(int id);
}
