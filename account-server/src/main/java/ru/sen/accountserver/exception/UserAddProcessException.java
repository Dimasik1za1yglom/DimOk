package ru.sen.accountserver.exception;

/**
 * an exception will occur if the add of the User class object to the database fails
 */
public class UserAddProcessException extends Exception{
    public UserAddProcessException(String message) {
        super(message);
    }
}

