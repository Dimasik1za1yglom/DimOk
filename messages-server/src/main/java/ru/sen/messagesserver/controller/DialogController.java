package ru.sen.messagesserver.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.messagesserver.controller.api.DialogApi;
import ru.sen.messagesserver.dto.DialogDto;
import ru.sen.messagesserver.entity.Dialog;
import ru.sen.messagesserver.exception.DialogOperationException;
import ru.sen.messagesserver.jwt.exception.AuthException;
import ru.sen.messagesserver.jwt.service.AuthService;
import ru.sen.messagesserver.service.DialogService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/dialog")
public class DialogController implements DialogApi {

    private final AuthService authService;
    private final DialogService dialogService;

    @Override
    public String getAllDialog(Long userId, Model model, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /use/dialog/my by user id {}", userId);
        try {
            List<Dialog> dialogs = dialogService.getAllDialogsByUserId(userId);
            if (dialogs.isEmpty()) {
                log.info("The user by id {} has no dialogs", userId);
                model.addAttribute("error", "У  вас нету пока диалогов");
            } else {
                model.addAttribute("dialogs", dialogs);
                log.info("Was successful get all dialogs by user id {}: {}", userId, dialogs);
            }
            return "user/myDialogs";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об существующих диалогах, попробуйте позднее");
            log.error("/post/my: An error occurred with the posts page: {}", e.getMessage());
            return "redirect:http://localhost:8082/user/myprofile";
        }
    }

    @Override
    public String createDialog(HttpServletRequest request, DialogDto dialogDto, Long userId, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /users/dialog create dialog is userId: {}", userId);
        try {
            Long createUserId = authService.getIdUserByRefreshToken(request);
            log.info("getting the token from the request was successful:  create user id {}", userId);
            Long dialogId = dialogService.createDialog(dialogDto, List.of(createUserId, userId));
            log.info("/users/dialog: create dialog id {} user {} was successful", dialogId, createUserId);
            return String.format("/chat/%d", dialogId);
        } catch (AuthException | DialogOperationException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно создать диалог, попробуйте позднее");
            log.error("getting the token from the request was failed: {}", e.getMessage());
            return String.format("redirect:http://localhost:8082/user/profile/%d", userId);
        }
    }
}
