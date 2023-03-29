package ru.sen.accountserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.sen.accountserver.services.ErrorInterceptorService;
import ru.sen.accountserver.services.UserService;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthorizationDataService dataService;
    private final ErrorInterceptorService interceptorService;

    @GetMapping("/myprofile")
    public String getProfile(Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            if (!userService.verifyUser(getEmailUser())) {
                log.info("/myprofile: Checking that the user's page is empty. " +
                        "Redirecting to a page with fields filled in");
                return "userFields";
            } else {
                User user = dataService.getData(getEmailUser()).getUser();
                model.addAttribute("user", user);
                log.info("/myprofile: Checking that the user's page is full. Redirection to the user's page.");
                return "myProfile";
            }
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error("/myprofile: An error occurred with the user's page: {}", e.getMessage());
            return "registration";
        }
    }


    @PostMapping("/add")
    public String addUser(@Valid UserForm userForm, BindingResult bindingResult,
                          Model model) {
        log.info("receiving a request for /add");
        if (bindingResult.hasErrors()) {
            log.warn("error entering values into the form");
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            model.addAttribute("error", error);
            log.info("/add: Errors were received when filling out the form for creating user page fields: {}", error);
            return "userFields";
        }

        if (interceptorService.checkingAddingUser(userForm, getEmailUser())) {
            log.info("/add: Adding fields to the user's page was successful");
            return "redirect:/user/myprofile";
        } else {
            String error = "Добавление полей пользователя не удалось. Попробуйте позднее";
            model.addAttribute("error", error);
            log.error("/add: Errors occurred when adding user data: {}", error);
            return "userFields";
        }
    }

    @PostMapping("/{user-id}/delete")
    public String deleteUser(@PathVariable("user-id") Long userId,
                             RedirectAttributes redirectAttributes) {
        if (!interceptorService.checkingDeletingUser(userId, getEmailUser())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользователя. Попробуйте позднее");
            log.error("/delete: Error on deleting a user under id: {}", userId);
            return "redirect:/user/myprofile";
        } else {
            log.info("/delete: Deleting the user was successful, exiting the session");
            return "redirect:/user/logout";
        }
    }

    @PostMapping("/update")
    public String updateUser(@Valid UserForm userForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            model.addAttribute("error", error);
            log.info("/update: Errors were received when filling out the form for change user page fields: {}", error);
            return "redirect:/user/change";
        }
        if (interceptorService.checkingUpdateUser(userForm, getEmailUser())) {
            log.info("/update: user data update was successful");
            return "redirect:/user/myprofile";
        } else {
            model.addAttribute("error", "Не удалось изменить данные");
            log.error("/update: Sending a message that the user's data could not be updated");
            return "redirect:/user/change";
        }
    }

    @GetMapping("/change")
    public String changeFieldsUser(Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = dataService.getData(getEmailUser()).getUser();
            model.addAttribute("user", user);
            log.info("/change: getting a form of fields for changing user data");
            return "changeFields";
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Заполните пожалуйста поля");
            log.error("/change: Errors occurred when change user data: {}", e.getMessage());
            return "redirect:changeFields";
        }
    }

    public String getEmailUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
