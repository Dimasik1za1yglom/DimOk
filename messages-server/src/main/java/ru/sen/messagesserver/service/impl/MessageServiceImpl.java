package ru.sen.messagesserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.exception.MessageOperationException;
import ru.sen.messagesserver.mapper.MessageMapper;
import ru.sen.messagesserver.repository.MessageRepository;
import ru.sen.messagesserver.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = MessageOperationException.class)
    public Message addMessage(MessageDto messageDto, LocalDateTime dateTime, Long userId) {
        log.info("add message: {} by userId: {}", messageDto, userId);
        Message message = messageRepository.save(messageMapper.messageDtoToMessage(messageDto, dateTime, userId));
        log.info("Adding a new message {} by userId {} was successful", message, userId);
        return message;
    }

    @Override
    @Transactional
    public void deleteAllMessageByDialogId(Long dialogId) {
        log.info("delete all messsages by dialog id {}: ", dialogId);
        messageRepository.deleteMessagesByDialogId(dialogId);
        log.info("delete all messsages by dialog id {} was successful", dialogId);
    }

    @Override
    public List<Message> getAllMessageByDialogId(Long dialogId) {
        log.info("get list<Message> by dialogId: {}", dialogId);
        return messageRepository.findAllByDialogId(dialogId, PageRequest.of(0, 10, Sort.Direction.DESC,
                "timeCreation"));
    }
}
