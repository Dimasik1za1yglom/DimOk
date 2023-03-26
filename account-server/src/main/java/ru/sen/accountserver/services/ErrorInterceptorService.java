package ru.sen.accountserver.services;

import ru.sen.accountserver.forms.UserForm;

/**
 * the repository was created to intercept errors that may occur in services with errors in transactions
 * it is an intermediate link to remove error handling from controllers
 */
public interface ErrorInterceptorService {

    /**
     * checking that the user's addition transaction was successful
     *
     * @param userForm turns an object into a class object User.class
     * @param email    it is needed to find the user that
     *                 will need to be changed according to his authorization data
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkingAddingUser(UserForm userForm, String email);

    /**
     * checking for a successful transaction to delete the user and his data with exception interception
     *
     * @param userToDeleteId id of the user to be deleted
     * @param email          it is needed to find the user that
     *                       will need to be changed according to his authorization data
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkingDeletingUser(Long userToDeleteId, String email);

    /**
     * checking for a successful transaction to update the user and his data with exception interception
     *
     * @param userForm turns an object into a class object User.class
     * @param email    it is needed to find the user that
     *                 will need to be changed according to his authorization data
     * @return handles transaction errors, if an exception is thrown, it returns false, otherwise true
     */
    boolean checkingUpdateUser(UserForm userForm, String email);
}
