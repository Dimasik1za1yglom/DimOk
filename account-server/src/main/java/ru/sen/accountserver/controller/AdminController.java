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
import ru.sen.accountserver.controller.api.AdminApi;
import ru.sen.accountserver.dto.UserDto;
import ru.sen.accountserver.entity.User;
import ru.sen.accountserver.security.details.UserDetailsImpl;
import ru.sen.accountserver.services.ErrorInterceptorService;
import ru.sen.accountserver.services.UserService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController implements AdminApi {

    private final UserService userService;
    private final ErrorInterceptorService interceptorService;

    @Override
    public String deleteUser(Long userId, RedirectAttributes redirectAttributes) {
        if (!interceptorService.checkIfDeletingUserSuccessful(userId, getUserEmail())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользователя. Попробуйте позднее");
            log.error("/delete: Error on deleting a user under id: {}", userId);
            return "redirect: ";
        } else {
            log.info("/delete: Deleting the user was successful, exiting the session");
            return;
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
    public String changeUserFields(Long userId, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(userId);
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
