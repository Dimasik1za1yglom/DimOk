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
    public void addMessage(MessageDto messageDto, LocalDateTime dateTime, Long userId)
            throws MessageOperationException {
        log.info("add message: {} by userId: {}", messageDto, userId);
        try {
            messageRepository.save(messageMapper.messageDtoToMessage(messageDto, dateTime, userId));
            log.info("Adding a new message by userId {} was successful", userId);
        } catch (Exception e) {
            log.error("Adding a new message by userId {} is failed: {}", userId, e.getMessage());
            throw new MessageOperationException("Throwing exception for demoing rollback");
        }

    }

    @Override
    public List<Message> getAllMessageByDialogId(Long dialogId) {
        log.info("get list<Message> by dialogId: {}", dialogId);
        return messageRepository.findAllByDialogId(dialogId, PageRequest.of(0, 5, Sort.Direction.DESC,
                "timeCreation"));
    }
}
