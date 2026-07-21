package com.strawHat.backend.exception;

/** Thrown during registration when the given email is already in use. */
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
