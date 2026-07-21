package com.strawHat.backend.exception;

/** Thrown when a requested DSA problem does not exist or does not belong to the current user. */
public class DSAProblemNotFoundException extends RuntimeException {
    public DSAProblemNotFoundException(String message) {
        super(message);
    }
}
