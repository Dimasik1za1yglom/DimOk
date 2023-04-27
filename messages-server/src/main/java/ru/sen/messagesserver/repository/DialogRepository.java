package ru.sen.messagesserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sen.messagesserver.entity.Dialog;

import java.util.List;

public interface DialogRepository extends JpaRepository<Dialog, Long> {

    List<Dialog> findAllByUserId(Long userId);

    int countDialogByDialogId(Long dialogId);

    void deleteByDialogIdAndUserId(Long dialogId, Long userId);
}
