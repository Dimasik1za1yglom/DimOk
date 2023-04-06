package ru.sen.accountserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sen.accountserver.controller.api.SignInApi;

@Controller
@RequestMapping("/input")
public class SignInController implements SignInApi {

    @Override
    public String input() {
        return "input";
    }
}
