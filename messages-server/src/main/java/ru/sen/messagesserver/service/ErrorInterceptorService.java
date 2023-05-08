package ru.sen.messagesserver.service;

/**
 * the repository was created to intercept errors that may occur in services with errors in transactions
 * it is an intermediate link to remove error handling from controllers
 */
public interface ErrorInterceptorService {

    /**
     * checking for deleting a all dialogs from a specific user
     *
     * @param userId id of the user whose dialogs  need to be deleted
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfDeletingDialogSuccessful(Long userId);

    /**
     * checking for deleting a dialog by id from a specific user
     *
     * @param userId   id of the user whose dialog  need to be deleted
     * @param dialogId dialog id
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfDeletingDialogSuccessful(Long userId, Long dialogId);

    /**
     * checking for change a name dialog by user id from a specific user
     *
     * @param newNameDialog new name dialog
     * @param userId        user Id
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfChangeDialogsNameSuccessful(String newNameDialog, Long userId);
}
