package ru.sen.accountserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sen.accountserver.controller.api.ErrorApi;

@Controller
@Slf4j
public class ErrorController implements ErrorApi {

    @Override
    public String signInErrorPage(Model model) {
        String error = "Не правильно введено значение email или password";
        model.addAttribute("error", error);
        log.error("The user could not log in to the profile");
        return "input";
    }

    @Override
    public String notPageError(Model model) {
        String error = "Возник сбой на сервере. Попробуйте зайти на странциу заново";
        model.addAttribute("error", error);
        log.error("Server failure. The page was not found");
        return "input";
    }
}
