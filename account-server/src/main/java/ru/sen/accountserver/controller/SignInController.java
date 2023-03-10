package ru.sen.accountserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/input")
public class SignInController {

    @GetMapping
    public String input() {
        return "input";
    }
}
