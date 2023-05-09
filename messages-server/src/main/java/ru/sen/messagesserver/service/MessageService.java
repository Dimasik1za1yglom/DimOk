package ru.sen.messagesserver.service;

import ru.sen.messagesserver.dto.MessageDto;
import ru.sen.messagesserver.entity.MessageOutput;

import java.time.LocalDateTime;
import java.util.List;

/**
 * service for working with user messages
 */
public interface MessageService {

    /**
     * the method adds a message to the database, and returns a message for output
     *
     * @param messageDto the received value of the message is the user id and the dialog id
     * @param dateTime   time of message creation
     * @return output messages, additionally contains who wrote the message
     */
    MessageOutput addMessage(MessageDto messageDto, LocalDateTime dateTime);

    /**
     * deletes all dialog messages. It is used only when both users have deleted their dialogs
     *
     * @param dialogId dialog id
     */
    void deleteAllMessageByDialogId(Long dialogId);

    /**
     * retrieves a list of messages by dialog id and converts them to a list of response messages
     *
     * @param dialogId dialog id
     * @return list of response messages
     */
    List<MessageOutput> getAllMessageByDialogId(Long dialogId);

    Long getDialogIdByUsers(List<Long> usersId);
}
