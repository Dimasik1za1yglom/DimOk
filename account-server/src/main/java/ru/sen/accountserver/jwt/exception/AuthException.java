package ru.sen.accountserver.jwt.exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
