package ru.sen.accountserver.jwt.exception;

public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }
}
