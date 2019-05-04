package com.sjsu.project.cmpe202.controller;

import com.sjsu.project.cmpe202.model.Cart;
import com.sjsu.project.cmpe202.repository.CartRepository;
import com.sjsu.project.cmpe202.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/get_cart_by_user_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<Cart> getCartByUserId(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cartRepository.findCartByUser_Id(user.get("user_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/get_cart_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<Cart> getCartByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        int id = userRepository.findUserByUsername(user.get("username")).getId();
        return new ResponseEntity<>(cartRepository.findCartByUser_Id(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/add_cart_by_user_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addCartByUserId(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>("Missing Parameter user_id", HttpStatus.BAD_REQUEST);

        Cart cart = new Cart();
        cart.setUser(userRepository.findUserById(user.get("user_id")));
        cartRepository.save(cart);
        return new ResponseEntity<>("Add Cart Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/add_cart_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addCartByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>("Missing Parameter username", HttpStatus.BAD_REQUEST);

        Cart cart = new Cart();
        cart.setUser(userRepository.findUserByUsername(user.get("username")));
        cartRepository.save(cart);
        return new ResponseEntity<>("Add Cart Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/delete_cart_by_user_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> deleteCartByUserId(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>("Missing Parameter user_id", HttpStatus.BAD_REQUEST);

        Cart cart = cartRepository.findCartByUser_Id(user.get("user_id"));
        cartRepository.delete(cart);
        return new ResponseEntity<>("Delete Cart Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/delete_cart_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> deleteCartByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>("Missing Parameter username", HttpStatus.BAD_REQUEST);

        Cart cart = cartRepository.findCartByUser_Id(userRepository.findUserByUsername(user.get("username")).getId());
        cartRepository.delete(cart);
        return new ResponseEntity<>("Delete Cart Successfully", HttpStatus.OK);
    }
}
