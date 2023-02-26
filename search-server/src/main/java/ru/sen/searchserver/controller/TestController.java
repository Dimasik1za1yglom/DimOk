package ru.sen.searchserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class TestController {

    @GetMapping("/test")
    public String testMethod() {
        return "Hello! I am search server";
    }

}
