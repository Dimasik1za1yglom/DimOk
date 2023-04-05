package ru.sen.accountserver.controller.api;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * api interface for working with user input
 */
public interface SignInApi {

    @GetMapping
    String input();
}
