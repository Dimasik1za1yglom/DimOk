package ru.sen.accountserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ErrorController {

    @GetMapping("/input/error")
    public String signInErrorPage(Model model) {
        String error = "Не правильно введено значение email или password";
        model.addAttribute("error", error);
        log.error("The user could not log in to the profile");
        return "input";
    }

    @GetMapping("/input/errorPage")
    public String notPageError(Model model) {
        String error = "Возник сбой на сервере. Попробуйте зайти на странциу заново";
        model.addAttribute("error", error);
        log.error("Server failure. The page was not found");
        return "input";
    }
}
