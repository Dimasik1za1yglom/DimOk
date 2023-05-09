package ru.sen.messagesserver.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.messagesserver.dto.DialogDto;

public interface DialogApi {

    @GetMapping("/my/{user-id}")
    String getAllDialog(@PathVariable("user-id") Long userId,
                        Model model,
                        RedirectAttributes redirectAttributes);

    @PostMapping("/add/{user-id}")
    String createDialog(HttpServletRequest request,
                        @Valid DialogDto dialogDto,
                        @PathVariable("user-id") Long userId,
                        RedirectAttributes redirectAttributes);

    @PostMapping("/delete/{dialog-id}/{user-id}")
    String deleteDialog(@PathVariable("dialog-id") Long dialogId,
                        @PathVariable("user-id") Long userId,
                        RedirectAttributes redirectAttributes);
}
