package ru.sen.accountserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.forms.UserForm;
import ru.sen.accountserver.security.details.UserDetailsImpl;
import ru.sen.accountserver.services.AuthorizationDataService;
import ru.sen.accountserver.services.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthorizationDataService dataService;

    @GetMapping("/myprofile")
    public String getProfile(Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            if (!userService.userVerification(getEmailUser())) {
                return "userFields";
            } else {
                User user = userService.getUserById(dataService.getData(getEmailUser()).getUserId());
                model.addAttribute("user", user);
            }
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "registration";
        }
        return "myprofile";
    }


    @PostMapping("/add")
    public String addUser(@Valid UserForm userForm,
                          RedirectAttributes redirectAttributes,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", error);
        } else {
            try {
                userService.addUser(userForm, getEmailUser());
                return "redirect:/myprofile";
            } catch (UsernameNotFoundException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        }
        return "redirect:userFields";
    }

    @PostMapping("/{user-id}/delete")
    public String deleteUser(@PathVariable("user-id") Long userId,
                             RedirectAttributes redirectAttributes) {
        if (!userService.deleteUser(userId, getEmailUser())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользовател. Попробуйте позднее");
            return "myProfile";
        } else {
            return "redirect:/user/logout";
        }
    }

    @PostMapping("/update")
    public String updateUser(UserForm userForm,
                             RedirectAttributes redirectAttributes,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", error);
            return "redirect:/change";
        }
        if (userService.updateUser(userForm, getEmailUser())) {
            return "redirect:myProfile";
        } else {
            redirectAttributes.addFlashAttribute("error", "Не удалось изменить данные");
            return "redirect:/change";
        }
    }

    @GetMapping("/change")
    public String changeFieldsUser(Model model) {
        User user = userService.getUserById(dataService.getData(getEmailUser()).getUserId());
        model.addAttribute("user", user);
        return "changeFields";
    }

    public String getEmailUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
