package ru.sen.accountserver.exception;

/**
 * an exception will occur if the update of the User class object to the database fails
 */
public class UserUpdateProcessException extends Exception{
    public UserUpdateProcessException(String message) {
        super(message);
    }
}
