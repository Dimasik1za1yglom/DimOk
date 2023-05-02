package ru.sen.messagesserver.service;

/**
 * the repository was created to intercept errors that may occur in services with errors in transactions
 * it is an intermediate link to remove error handling from controllers
 */
public interface ErrorInterceptorService {

    /**
     * checking for deleting a dialog from a specific user
     *
     * @param userId id of the user whose dialog  need to be deleted
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkIfDeletingDialogSuccessful(Long userId);
}
