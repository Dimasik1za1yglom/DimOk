package ru.sen.messagesserver.service;

import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    Message addMessage(MessageDto messageDto, LocalDateTime dateTime, Long userId);

    void deleteAllMessageByDialogId(Long dialogId);

    List<Message> getAllMessageByDialogId(Long dialogId);
}
