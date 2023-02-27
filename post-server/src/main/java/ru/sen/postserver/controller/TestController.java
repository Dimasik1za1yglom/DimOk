package ru.sen.postserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class TestController {

    @Value("${eureka.instance.instance-id}")
    private String id;

    @GetMapping("/test")
    public String testMethod() {
        return "Hello! I am post server: " + id;
    }

}
