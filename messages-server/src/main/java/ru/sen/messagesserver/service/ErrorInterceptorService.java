package ru.sen.messagesserver.service;


import ru.sen.messagesserver.dto.DialogDto;

import java.util.List;

public interface ErrorInterceptorService {

    boolean checkIfCreateDialogSuccessful(DialogDto dialogDto, List<Long> usersId);

    boolean checkIfDeletingDialogSuccessful(Long userId, Long dialogId);
}
