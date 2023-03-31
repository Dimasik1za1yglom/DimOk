package ru.sen.accountserver.exception;

/**
 * an exception will occur if delete of the User class object to the database fails
 */
public class UserDeleteProcessException extends Exception{
    public UserDeleteProcessException(String message) {
        super(message);
    }
}
