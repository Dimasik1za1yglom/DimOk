package ru.sen.accountserver.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.dto.AuthorizationDataDto;

/**
 * api interface for working with user input
 */
public interface SignInApi {

    @GetMapping
    String input();

    @PostMapping
    String input(HttpServletResponse httpServletResponse,
                 @Valid AuthorizationDataDto dataDto,
                 BindingResult bindingResult,
                 Model model,
                 RedirectAttributes redirectAttributes);
}
