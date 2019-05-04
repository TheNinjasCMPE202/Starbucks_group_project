package com.sjsu.project.cmpe202.controller;

import com.sjsu.project.cmpe202.model.Cart;
import com.sjsu.project.cmpe202.model.CartItem;
import com.sjsu.project.cmpe202.repository.CartItemRepository;
import com.sjsu.project.cmpe202.repository.CartRepository;
import com.sjsu.project.cmpe202.repository.ItemRepository;
import com.sjsu.project.cmpe202.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CartItemController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/get_cart_item_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<List<CartItem>> getCartItemByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        int id = cartRepository.findCartByUser_Id(userRepository.findUserByUsername(user.get("username")).getId()).getId();
        return new ResponseEntity<>(cartItemRepository.findCartItemsByCart(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(
            value = "/order/add_cart_item_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addCartItemByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>("Missing Parameter username", HttpStatus.BAD_REQUEST);
        if (!user.containsKey("product"))
            return new ResponseEntity<>("Missing Parameter product", HttpStatus.BAD_REQUEST);

        Cart cart = cartRepository.findCartByUser_Id(userRepository.findUserByUsername(user.get("username")).getId());
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart(cart.getId());
        if (!cartItems.isEmpty()) {
            for (CartItem cartItem : cartItems) {
                if (user.get("product").equals(cartItem.getItem())) {
                    int quantity = 1 + cartItem.getQuantity();
                    cartItem.setQuantity(quantity);
                }
                cartItemRepository.save(cartItem);
            }
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(itemRepository.findItemByProduct(user.get("product")));
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);
        }
        return new ResponseEntity<>("Add new CardItem successfully", HttpStatus.OK);
    }
}
