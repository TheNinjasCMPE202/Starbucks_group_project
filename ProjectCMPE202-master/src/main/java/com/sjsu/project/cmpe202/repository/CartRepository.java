package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Cart;
import com.sjsu.project.cmpe202.model.CartItem;
import com.sjsu.project.cmpe202.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUser_Id(int id);
//    Cart findCartByUser(User user);
    Cart findCartById(int id);
}
