package ru.sen.messagesserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sen.messagesserver.entity.Dialog;

import java.util.List;
import java.util.Optional;

public interface DialogRepository extends JpaRepository<Dialog, Long> {

    List<Dialog> findAllByUserId(Long userId);

    int countDialogByDialogId(Long dialogId);

    void deleteByDialogIdAndUserId(Long dialogId, Long userId);

    @Query(nativeQuery = true,
            value = "select dialog_id from dialogs where user_id = :createUserId or user_id = :userId group by dialog_id having count(*) > 1")
    Optional<Object> getDialogIdByUsersId(Long createUserId, Long userId);
}
