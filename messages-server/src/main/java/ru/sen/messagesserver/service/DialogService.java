package ru.sen.messagesserver.service;

import ru.sen.messagesserver.dto.DialogDto;
import ru.sen.messagesserver.entity.Dialog;
import ru.sen.messagesserver.exception.DialogOperationException;

import java.util.List;

public interface DialogService {

    Long createDialog(DialogDto dialogDto, List<Long> usersId) throws DialogOperationException;

    void deleteDialog(Long userId, Long dialogId) throws DialogOperationException;

    List<Dialog> getAllDialogsByUserId(Long userId);

    boolean checkIfDialogExists(Long createUserId, Long userId);
}
