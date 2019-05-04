package com.sjsu.project.cmpe202.controller;

import com.sjsu.project.cmpe202.model.*;
import com.sjsu.project.cmpe202.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/add_order_item_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addOrderItemByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>("Missing Parameter username", HttpStatus.BAD_REQUEST);

        Order newOrder = new Order();
        User orderUser = userRepository.findUserByUsername(user.get("username"));
        LocalDate createDate = LocalDate.now();
        newOrder.setDate(createDate);
        newOrder.setUser(orderUser);
        Cart cart = cartRepository.findCartByUser_Id(orderUser.getId());
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart(cart.getId());
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setItem(cartItem.getItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemRepository.save(orderItem);
        }
        return new ResponseEntity<>("Add new OrderItem successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/get_order_items_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<OrderItem>> getOrderItemsByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Order> orders = orderRepository.findOrdersByUser(userRepository.findUserByUsername(user.get("username")).getId());
        return new ResponseEntity<>(orderItemRepository.findOrderItemsByOrder(orders), HttpStatus.OK);
    }
}
