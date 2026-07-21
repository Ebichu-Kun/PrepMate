package com.strawHat.backend.exception;

/** Thrown when a user cannot be found (e.g. by id or from the security context). */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
