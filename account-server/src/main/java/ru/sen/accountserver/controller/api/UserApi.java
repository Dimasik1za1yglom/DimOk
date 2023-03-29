package ru.sen.accountserver.controller.api;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.forms.UserForm;

public interface UserApi {

    @GetMapping("/myprofile")
    String getProfile(Model model,
                             RedirectAttributes redirectAttributes);

    @PostMapping("/add")
    String addUser(@Valid UserForm userForm, BindingResult bindingResult,
                          Model model);

    @PostMapping("/{user-id}/delete")
    String deleteUser(@PathVariable("user-id") Long userId,
                             RedirectAttributes redirectAttributes);

    @PostMapping("/update")
    String updateUser(@Valid UserForm userForm,
                             BindingResult bindingResult,
                             Model model);

    @GetMapping("/change")
    String changeFieldsUser(Model model, RedirectAttributes redirectAttributes);

}
