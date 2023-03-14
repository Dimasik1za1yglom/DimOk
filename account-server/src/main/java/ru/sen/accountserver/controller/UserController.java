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
import ru.sen.accountserver.services.UserService;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthorizationDataService dataService;

    @GetMapping("/myprofile")
    public String getProfile(Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            if (!userService.userVerification(getEmailUser())) {
                log.info("Checking that the user's page is empty. Redirecting to a page with fields filled in");
                return "redirect:/change";
            } else {
                User user = userService.getUserById(dataService.getData(getEmailUser()).getUserId());
                model.addAttribute("user", user);
                log.info("Checking that the user's page is full. Redirection to the user's page.");
            }
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error("An error occurred with the user's page: {}", e.getMessage());
            return "registration";
        }
        return "myProfile";
    }


    @PostMapping("/add")
    public String addUser(@Valid UserForm userForm,
                          RedirectAttributes redirectAttributes,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", error);
            log.info("Errors were received when filling out the form for creating user page fields: {}", error);
        } else {
            try {
                userService.addUser(userForm, getEmailUser());
                log.info("Adding fields to the user's page was successful");
                return "redirect:/myprofile";
            } catch (UsernameNotFoundException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                log.error("Errors occurred when adding user data: {}", e.getMessage());
            }
        }
        return "redirect:userFields";
    }

    @PostMapping("/{user-id}/delete")
    public String deleteUser(@PathVariable("user-id") Long userId,
                             RedirectAttributes redirectAttributes) {
        if (!userService.deleteUser(userId, getEmailUser())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользователя. Попробуйте позднее");
            log.error("Error on deleting a user under id: {}", userId);
            return "myProfile";
        } else {
            log.info("Deleting the user was successful, exiting the session");
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
            log.info("Errors were received when filling out the form for change user page fields: {}", error);
            return "redirect:/change";
        }
        if (userService.updateUser(userForm, getEmailUser())) {
            log.info("user data update was successful");
            return "redirect:myProfile";
        } else {
            redirectAttributes.addFlashAttribute("error", "Не удалось изменить данные");
            log.error("Sending a message that the user's data could not be updated");
            return "redirect:/change";
        }
    }

    @GetMapping("/change")
    public String changeFieldsUser(Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(dataService.getData(getEmailUser()).getUserId());
            model.addAttribute("user", user);
            log.info("getting a form of fields for changing user data");
            return "changeFields";
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Заполните пожалуйста поля");
            log.error("Errors occurred when change user data: {}", e.getMessage());
            return "redirect:changeFields";
        }
    }

    public String getEmailUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
