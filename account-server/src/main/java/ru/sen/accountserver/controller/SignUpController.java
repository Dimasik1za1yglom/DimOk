package ru.sen.accountserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            return "redirect:/registration";
        }
        if (authorizationDataService.dataVerification(dataForm)) {
            String error = "Пользователь с таким e-mail уже существует, попробуйте войти в систему";
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/registration";
        }
        if (authorizationDataService.addData(dataForm)) {
            authService.setUpSecurity(dataForm);
            return "redirect:myProfile";
        } else {
            String error = "Не удалось зарегистрировать пользователя, попробуйте позднее";
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/registration";
        }
    }
}
