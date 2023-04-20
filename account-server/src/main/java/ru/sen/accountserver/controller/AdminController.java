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
@RequestMapping("/user/admin")
public class AdminController implements AdminApi {

    private final UserService userService;
    private final ErrorInterceptorService interceptorService;

    @Override
    public String getUser(Long userId, Model model, RedirectAttributes redirectAttributes) {
        log.info("admin/profile/{user-id}: request to receive the user's page by id {}", userId);
        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            log.info("/profile/{user-id}: getting a user page was successful: {}", user);
            return "admin/adminUserProfile";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Пользователь отсутсвует");
            log.error("/profile/{user-id}: Getting a user page is failed: {}", e.getMessage());
            return "redirect:/admin/users/all";
        }
    }

    @Override
    public String deleteUser(Long userId, RedirectAttributes redirectAttributes) {
        if (interceptorService.checkIfDeletingUserSuccessful(userId, getUserEmail())) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пользователя. Попробуйте позднее");
            log.error("/delete: Error on deleting a user under id: {}", userId);
            return String.format("redirect:/admin/profile/%d", userId);
        } else {
            log.info("/delete: Deleting the user was successful, exiting the session");
            return "redirect:/admin/users/all";
        }
    }

    @Override
    public String updateUser(UserDto userDto,
                             BindingResult bindingResult,
                             Long userId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("user", userDto);
            model.addAttribute("errors", errors);
            log.info("/update: Errors were received when filling out the form for change user page fields: {}", errors);
            return "admin/adminChangeFields";
        }
        if (interceptorService.checkIfUpdateUserSuccessful(userDto, userId)) {
            log.info("/update: user data update was successful");
            return "redirect:/admin/users/all";
        } else {
            redirectAttributes.addFlashAttribute("error", "Не удалось изменить данные");
            log.error("/update: Sending a message that the user's data could not be updated");
            return String.format("redirect:/admin/%d/change", userId);
        }
    }

    @Override
    public String changeUserFields(Long userId, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            log.info("/change: getting a form of fields for changing user data");
            return "admin/adminChangeFields";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить данные пользователя");
            log.error("/change: Errors occurred when change user data: {}", e.getMessage());
            return "redirect:admin/adminUserProfile";
        }
    }

    private String getUserEmail() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

}
