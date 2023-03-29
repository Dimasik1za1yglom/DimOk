package ru.sen.accountserver.controller.api;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.forms.AuthorizationDataForm;

public interface SignUpApi {

    @GetMapping
    String registration();

    @PostMapping
    String registration(RedirectAttributes redirectAttributes,
                               @Valid AuthorizationDataForm dataForm,
                               BindingResult bindingResult);


}
