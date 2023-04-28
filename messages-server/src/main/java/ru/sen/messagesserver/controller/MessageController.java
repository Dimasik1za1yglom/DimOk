package ru.sen.messagesserver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.service.MessageService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/chat/{dialog-id}")
    public String chat(@PathVariable("dialog-id") Long dialogId,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /chat");
        try {
            List<Message> messages = messageService.getAllMessageByDialogId(dialogId);
            model.addAttribute("messages", messages);
            model.addAttribute("dialogId", dialogId);
            log.info("/chat: receiving a request chat messages was successful: {}", messages);
            return "chat";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об существующем диалоге, попробуйте позднее");
            log.error("/chat: An error occurred with the chat message page: {}", e.getMessage());
            return String.format("/chat/%d", dialogId);
        }

    }
}
