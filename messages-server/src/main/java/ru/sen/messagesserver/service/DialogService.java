package ru.sen.messagesserver.service;

import ru.sen.messagesserver.dto.DialogDto;
import ru.sen.messagesserver.entity.Dialog;
import ru.sen.messagesserver.exception.DialogOperationException;

import java.util.List;

/**
 * service for working with user dialogs
 */
public interface DialogService {

    /**
     * creates dialogs for users common dialog id
     *
     * @param dialogs list of dialog names
     * @param usersId list of users who will have dialogs created
     * @return dialog id
     * @throws DialogOperationException
     */
    Long createDialog(List<DialogDto> dialogs, List<Long> usersId) throws DialogOperationException;

    /**
     * deleting all dialogs from a specific user. It is mandatory to process the deletion of messages
     *
     * @param userId user Id
     * @throws DialogOperationException
     */
    void deleteDialogs(Long userId) throws DialogOperationException;

    /**
     * getting all user dialogs by their id
     *
     * @param userId user Id
     * @return list of dialogs of a specific user
     */
    List<Dialog> getAllDialogsByUserId(Long userId);

    /**
     * checking for the existence of dialogs with a common dialogId for two users
     *
     * @param createUserId create user id
     * @param userId       user id
     * @return returns the number of common dialogs
     */
    Long checkIfDialogExists(Long createUserId, Long userId);
}
