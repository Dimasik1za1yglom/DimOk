package ru.sen.messagesserver.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.jwt.exception.AuthException;
import ru.sen.messagesserver.jwt.service.AuthService;
import ru.sen.messagesserver.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AuthService authService;

    @GetMapping("/chat/{dialog-id}")
    public String chat(@PathVariable("dialog-id") Long dialogId,
                       HttpServletRequest request,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /chat");
        try {
            Long createUserId = authService.getIdUserByRefreshToken(request);
            log.info("getting the token from the request was successful:  create user id {}", createUserId);
            List<Message> messages = messageService.getAllMessageByDialogId(dialogId);
            model.addAttribute("userId", createUserId);
            model.addAttribute("messages", messages);
            model.addAttribute("dialogId", dialogId);
            log.info("/chat: receiving a request chat messages was successful: {}", messages);
            return "user/chat";
        } catch (EntityNotFoundException | AuthException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об существующем диалоге, попробуйте позднее");
            log.error("/chat: An error occurred with the chat message page: {}", e.getMessage());
            return String.format("/chat/%d", dialogId);
        }

    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message sendMessage(@Valid MessageDto chatMessage) {
        log.info("/chat receiving a message: {}", chatMessage);
        Message message = messageService.addMessage(chatMessage, LocalDateTime.now());
        log.info("Add message {} user was successful", chatMessage);
        return message;
    }
}
