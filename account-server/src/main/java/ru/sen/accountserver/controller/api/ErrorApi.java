package ru.sen.accountserver.controller.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface ErrorApi {

    @GetMapping("/input/error")
    String signInErrorPage(Model model);

    @GetMapping("/input/errorPage")
    String notPageError(Model model);

}
