package ru.sen.accountserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.forms.AuthorizationDataForm;
import ru.sen.accountserver.security.service.AuthService;
import ru.sen.accountserver.services.AuthorizationDataService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
@Slf4j
public class SignUpController {

    private final AuthorizationDataService authorizationDataService;
    private final AuthService authService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String registration(RedirectAttributes redirectAttributes,
                               @Valid AuthorizationDataForm dataForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/registration: Errors were received when entering data. Do not meet certain requirements: {}", error);
        }
        if (authorizationDataService.dataVerification(dataForm)) {
            String error = "Пользователь с таким e-mail уже существует, попробуйте войти в систему";
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/registration: Attempt to register a user who is already in the system: {}", dataForm);
        }
        if (authorizationDataService.addData(dataForm)) {
            authService.setUpSecurity(dataForm);
            log.info("/registration: user registration in Spring Security: {}", dataForm);
            return "redirect:/user/myprofile";
        } else {
            String error = "Не удалось зарегистрировать пользователя, попробуйте позднее";
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/registration: Failed to register user {}", dataForm);
        }
        return "redirect:/registration";
    }
}
