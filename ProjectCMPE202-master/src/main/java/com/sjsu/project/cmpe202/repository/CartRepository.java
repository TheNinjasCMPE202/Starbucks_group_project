package com.sjsu.project.cmpe202.repository;

import com.sjsu.project.cmpe202.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUser_Id(int id);

    //    Cart findCartByUser(User user);
    Cart findCartById(int id);
}
