package com.strawHat.backend.exception;

/** Thrown when login credentials (email/password) are incorrect. */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
