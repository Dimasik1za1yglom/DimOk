package ru.sen.messagesserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.service.MessageService;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/newMessage")
    public Message save(MessageDto messageDto) {
        try {
            return messageService.addMessage(messageDto, LocalDateTime.now(), 3L);
        } catch (Exception e) {
            log.error("Adding a new message {} is failed: {}", messageDto, e.getMessage());
        }
    }

}
