package ru.sen.messagesserver.service;

import ru.sen.messagesserver.dto.DialogDto;
import ru.sen.messagesserver.entity.Dialog;
import ru.sen.messagesserver.exception.DialogOperationException;

import java.util.List;

public interface DialogService {

    void createDialog(DialogDto dialogDto, List<Long> usersId) throws DialogOperationException;

    List<Dialog> getAllDialogsByUserId(Long userId);
}
