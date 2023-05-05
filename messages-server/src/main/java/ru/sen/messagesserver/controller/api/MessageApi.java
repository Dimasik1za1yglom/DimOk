package ru.sen.messagesserver.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.MessageOutput;

public interface MessageApi {

    @GetMapping("/chat/{dialog-id}")
    String chat(@PathVariable("dialog-id") Long dialogId,
                HttpServletRequest request,
                Model model,
                RedirectAttributes redirectAttributes);

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    MessageOutput sendMessage(@Valid MessageDto chatMessage);
}
