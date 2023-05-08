package ru.sen.messagesserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.dto.remote.ResponseUserDto;
import ru.sen.messagesserver.entity.Message;
import ru.sen.messagesserver.entity.MessageOutput;
import ru.sen.messagesserver.exception.MessageOperationException;
import ru.sen.messagesserver.gateway.AccountGateway;
import ru.sen.messagesserver.mapper.MessageMapper;
import ru.sen.messagesserver.mapper.MessageOutputMapper;
import ru.sen.messagesserver.repository.MessageRepository;
import ru.sen.messagesserver.service.MessageService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final AccountGateway accountGateway;
    private final MessageOutputMapper messageOutputMapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = MessageOperationException.class)
    public MessageOutput addMessage(MessageDto messageDto, LocalDateTime dateTime) {
        log.info("add message: {} by user", messageDto);
        Message message = messageRepository.save(messageMapper.messageDtoToMessage(messageDto, dateTime));
        log.info("Adding a new message {} by user was successful", message);
        ResponseUserDto response = accountGateway.getUserNameByUserId(messageDto.getUserId());
        if (!response.isSuccess()) {
            log.error("Failed to get a user name from account service. There is a error: {}", response.getMessage());
            return messageOutputMapper.messageToMessageOutput(message, "error");
        } else {
            log.info("Successful to get a user name from account service: {}", response);
            return messageOutputMapper.messageToMessageOutput(message, response.getUserName());
        }
    }

    @Override
    @Transactional
    public void deleteAllMessageByDialogId(Long dialogId) {
        log.info("delete all messsages by dialog id {}: ", dialogId);
        messageRepository.deleteMessagesByDialogId(dialogId);
        log.info("delete all messsages by dialog id {} was successful", dialogId);
    }

    @Override
    public List<MessageOutput> getAllMessageByDialogId(Long dialogId) {
        log.info("get list<Message> by dialogId: {}", dialogId);
        Map<Long, String> usersName = new HashMap<>();
        List<Message> messages = messageRepository.findAllByDialogId(dialogId,
                PageRequest.of(0, 10, Sort.Direction.ASC, "timeCreation"));
        if (messages.isEmpty()) {
            log.info("The dialog does not contain messages");
            return List.of();
        }
        log.info("receiving the last 10 messages in the dialog under id {}: {}", dialogId, messages);
        List<MessageOutput> messageOutputs = messages.stream()
                .map(message -> {
                    if (!usersName.containsKey(message.getUserId())) {
                        ResponseUserDto response = accountGateway.getUserNameByUserId(message.getUserId());
                        log.info("getting a response from account service: {}", response);
                        if (!response.isSuccess()) {
                            log.error("Failed to get a user name from account service. There is a error: {}",
                                    response.getMessage());
                            return messageOutputMapper.messageToMessageOutput(message, "error");
                        } else {
                            usersName.put(message.getUserId(), response.getUserName());
                        }
                    }
                    return messageOutputMapper.messageToMessageOutput(message, usersName.get(message.getUserId()));
                }).collect(Collectors.toList());
        log.info("creating reply messages: {}", messageOutputs);
        return messageOutputs;
    }

    @Override
    public Long getDialogIdByUsers(List<Long> usersId) {
        log.info("getting a dialog id among messages by list usersId: {}", usersId);
        return messageRepository.getDialogIdByTwoUserId(usersId.get(0), usersId.get(1));
    }
}
