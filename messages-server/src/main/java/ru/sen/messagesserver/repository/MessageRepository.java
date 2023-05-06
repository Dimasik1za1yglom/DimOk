package ru.sen.messagesserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sen.messagesserver.entity.Message;

import java.util.List;

/**
 * The interface interacts with the message user in the database.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * request to receive all messages by dialogId
     *
     * @param dialogId dialog id
     * @param pageable instructions for how many recent messages to receive
     * @return the list of messages sorted
     */
    List<Message> findAllByDialogId(Long dialogId, Pageable pageable);

    /**
     * deletes all messages by dialog Id
     *
     * @param dialogId dialog Id
     */
    void deleteMessagesByDialogId(Long dialogId);

    /**
     * request to get dialogid among the messages of two users if there are any
     * @param oneUserId user id
     * @param twoUserId user id
     * @return dialog id
     */
    @Query(nativeQuery = true,
            value = "select dialog_id from messages where user_id = 3 or user_id = 2 group by dialog_id")
    Long getDialogIdByTwoUserId(Long oneUserId, Long twoUserId);
}
