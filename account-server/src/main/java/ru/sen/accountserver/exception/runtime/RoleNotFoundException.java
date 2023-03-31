package ru.sen.accountserver.exception.runtime;

/**
 * an exception will occur when receiving an object of the Role.class
 */
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
