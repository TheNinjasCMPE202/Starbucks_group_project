package com.sjsu.project.cmpe202.controller;

import com.sjsu.project.cmpe202.model.Cart;
import com.sjsu.project.cmpe202.model.User;
import com.sjsu.project.cmpe202.repository.CartRepository;
import com.sjsu.project.cmpe202.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/get_user_by_user_id",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<User> getUserByUserId(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userRepository.findUserById(user.get("user_id")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/get_user_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<User> getUserByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userRepository.findUserByUsername(user.get("username")), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/add_new_user",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> addNewUser(@RequestBody Map<String, String> user) {

        if (!user.containsKey("username"))
            return new ResponseEntity<>("Missing Parameter username", HttpStatus.BAD_REQUEST);

        if (!user.containsKey("pin"))
            return new ResponseEntity<>("Missing Parameter pin", HttpStatus.BAD_REQUEST);

        if (!user.containsKey("first_name"))
            return new ResponseEntity<>("Missing Parameter first_name", HttpStatus.BAD_REQUEST);

        if (!user.containsKey("last_name"))
            return new ResponseEntity<>("Missing Parameter last_name", HttpStatus.BAD_REQUEST);

        if (!user.containsKey("email"))
            return new ResponseEntity<>("Missing Parameter email", HttpStatus.BAD_REQUEST);


        User newUser = new User(user.get("username"), user.get("pin"), user.get("first_name"), user.get("last_name"), user.get("email"));
        Cart cart = new Cart();
        cart.setUser(newUser);
        cartRepository.save(cart);

        userRepository.save(newUser);

        return new ResponseEntity<>("Add New user Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/update_user_by_username",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> updateUserByUsername(@RequestBody Map<String, String> user) {
        User updateUser = userRepository.findUserByUsername(user.get("username"));

        if (user.containsKey("first_name"))
            updateUser.setFirstName(user.get("first_name"));

        if (user.containsKey("last_name"))
            updateUser.setLastName(user.get("last_name"));

        if (user.containsKey("email"))
            updateUser.setEmail(user.get("email"));

        userRepository.save(updateUser);

        return new ResponseEntity<>("Update User Successfully", HttpStatus.OK);
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
//    @ResponseBody
//    @RequestMapping(
//            value = "/user/update_user_by_user_id",
//            method = RequestMethod.POST,
//            consumes = "application/json")
//    public ResponseEntity<String> updateUserByUserId(@RequestBody Map<String, String> user) {
//        int id = Integer.parseInt(user.get("user_id"));
//        User updateUser = userRepository.findUserById(id);
//
//        if (user.containsKey("first_name"))
//            updateUser.setFirstName(user.get("first_name"));
//
//        if (user.containsKey("last_name"))
//            updateUser.setLastName(user.get("last_name"));
//
//        if (user.containsKey("email"))
//            updateUser.setEmail(user.get("email"));
//
//        userRepository.save(updateUser);
//
//        return new ResponseEntity<>("Update User Successfully", HttpStatus.OK);
//    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/delete_user_by_user_id",
            method = RequestMethod.DELETE,
            consumes = "application/json")
    public ResponseEntity<String> deleteUserById(@RequestBody Map<String, Integer> user) {
        if (!user.containsKey("user_id"))
            return new ResponseEntity<>("Missing Parameter user_id", HttpStatus.BAD_REQUEST);

        userRepository.deleteById(user.get("user_id"));
        return new ResponseEntity<>("Delete User Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/delete_user_by_username",
            method = RequestMethod.DELETE,
            consumes = "application/json")
    public ResponseEntity<String> deleteUserByUsername(@RequestBody Map<String, String> user) {
        if (!user.containsKey("username"))
            return new ResponseEntity<>("Missing Parameter username", HttpStatus.BAD_REQUEST);

        int id = userRepository.findUserByUsername(user.get("username")).getId();
        userRepository.deleteById(id);
        return new ResponseEntity<>("Delete User Successfully", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://starbucks-web.herokuapp.com", "https://starbucks-web-map.herokuapp.com"})
    @ResponseBody
    @RequestMapping(
            value = "/user/get_all_users",
            method = RequestMethod.GET,
            consumes = "application/json")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}