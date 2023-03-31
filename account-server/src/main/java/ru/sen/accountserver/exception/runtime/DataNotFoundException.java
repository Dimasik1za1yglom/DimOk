package ru.sen.accountserver.exception.runtime;

/**
 * an exception will occur when receiving an object of the AuthorizationData.class
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
