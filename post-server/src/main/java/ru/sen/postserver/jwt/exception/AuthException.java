package ru.sen.postserver.jwt.exception;

public class AuthException extends Exception {
    public AuthException(String message) {
        super(message);
    }
}
