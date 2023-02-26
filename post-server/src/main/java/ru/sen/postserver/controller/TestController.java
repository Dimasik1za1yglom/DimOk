package ru.sen.postserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class TestController {

    @GetMapping("/test")
    public String testMethod() {
        return "Hello! I am post server";
    }

}
