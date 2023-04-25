package ru.sen.messagesserver.service;

import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.exception.MessageOperationException;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    void addMessage(MessageDto messageDto, LocalDateTime dateTime, Long userId) throws MessageOperationException;

    List<Message> getAllMessageByDialogId(Long dialogId);
}
