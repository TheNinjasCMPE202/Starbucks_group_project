package com.sjsu.project.cmpe202.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World! It works";

    }
}
