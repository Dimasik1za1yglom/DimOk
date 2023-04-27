package ru.sen.messagesserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.messagesserver.entity.Message;

import java.util.List;

/**
 * The interface interacts with the message user in the database.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByDialogId(Long dialogId, Pageable pageable);

    void deleteMessagesByDialogId(Long dialogId);
}
