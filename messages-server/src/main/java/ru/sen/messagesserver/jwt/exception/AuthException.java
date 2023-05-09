package ru.sen.messagesserver.jwt.exception;

public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }
}
