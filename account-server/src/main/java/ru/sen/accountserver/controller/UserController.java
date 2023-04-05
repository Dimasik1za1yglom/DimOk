package ru.sen.accountserver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.accountserver.controller.api.UserApi;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.security.details.UserDetailsImpl;
import ru.sen.accountserver.services.AuthorizationDataService;
import ru.sen.accountserver.services.ErrorInterceptorService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private final AuthorizationDataService dataService;
    private final ErrorInterceptorService interceptorService;

    @Override
    public String getProfile(Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            if (dataService.checkIfUserExists(getUserEmail())) {
                log.info("/myprofile: Checking that the user's page is empty. " +
                        "Redirecting to a page with fields filled in");
                return "userFields";
            } else {
                User user = dataService.getData(getUserEmail()).getUser();
                model.addAttribute("user", user);
                log.info("/myprofile: Checking that the user's page is full. Redirection to the user's page.");
                return "myProfile";
            }
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error("/myprofile: An error occurred with the user's page: {}", e.getMessage());
            return "registration";
        }
    }

    @Override
    public String addUser(UserDto userDto, BindingResult bindingResult, Model model) {
        log.info("receiving a request for /add");
        if (bindingResult.hasErrors()) {
            log.warn("/add: Error entering values into the form");
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors);
            log.info("/add: Errors were received when filling out the form for creating user page fields: {}", errors);
            return "userFields";
        }
        if (interceptorService.checkIfAddingUserSuccessful(userDto, getUserEmail())) {
            log.info("/add: Adding fields to the user's page was successful");
            return "redirect:/user/myprofile";
        } else {
            String error = "Добавление полей пользователя не удалось. Попробуйте позднее";
            model.addAttribute("error", error);
            log.error("/add: Errors occurred when adding user data: {}", error);
            return "userFields";
        }
    }

    @Override
    public String deleteUser(Long userId, RedirectAttributes redirectAttributes) {
        if (!interceptorService.checkIfDeletingUserSuccessful(userId, getUserEmail())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользователя. Попробуйте позднее");
            log.error("/delete: Error on deleting a user under id: {}", userId);
            return "redirect:/user/myprofile";
        } else {
            log.info("/delete: Deleting the user was successful, exiting the session");
            return "redirect:/user/logout";
        }
    }

    @Override
    public String updateUser(UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors);
            log.info("/update: Errors were received when filling out the form for change user page fields: {}", errors);
            return "changeFields";
        }
        if (interceptorService.checkIfUpdateUserSuccessful(userDto, getUserEmail())) {
            log.info("/update: user data update was successful");
            return "redirect:/user/myprofile";
        } else {
            model.addAttribute("error", "Не удалось изменить данные");
            log.error("/update: Sending a message that the user's data could not be updated");
            return "redirect:/user/change";
        }
    }

    @Override
    public String changeUserFields(Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = dataService.getData(getUserEmail()).getUser();
            model.addAttribute("user", user);
            log.info("/change: getting a form of fields for changing user data");
            return "changeFields";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Заполните пожалуйста поля");
            log.error("/change: Errors occurred when change user data: {}", e.getMessage());
            return "redirect:changeFields";
        }
    }

    private String getUserEmail() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
