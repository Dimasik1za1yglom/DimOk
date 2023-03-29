package ru.sen.accountserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.controller.api.SignUpApi;
import ru.sen.accountserver.forms.AuthorizationDataForm;
import ru.sen.accountserver.security.service.AuthService;
import ru.sen.accountserver.services.AuthorizationDataService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class SignUpController implements SignUpApi {

    private final AuthorizationDataService authorizationDataService;
    private final AuthService authService;

    @Override
    public String registration() {
        return "registration";
    }

    @Override
    public String registration(RedirectAttributes redirectAttributes,
                               @Valid AuthorizationDataForm dataForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/registration: Errors were received when entering data. Do not meet certain requirements: {}", error);
            return "redirect:/registration";
        }
        if (authorizationDataService.checkIfEmailExists(dataForm.getEmail())) {
            String error = "Пользователь с таким e-mail уже существует, попробуйте войти в систему";
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/registration: Attempt to register a user who is already in the system: {}", dataForm);
            return "redirect:/registration";
        }
        if (authorizationDataService.addDataWasSuccessful(dataForm)) {
            authService.setUpSecurity(dataForm);
            log.info("/registration: user registration in Spring Security: {}", dataForm);
            return "redirect:/user/myprofile";
        } else {
            String error = "Не удалось зарегистрировать пользователя, попробуйте позднее";
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/registration: Failed to register user {}", dataForm);
            return "redirect:/registration";
        }
    }
}
