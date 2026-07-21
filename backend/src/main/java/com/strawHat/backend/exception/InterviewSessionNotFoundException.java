package com.strawHat.backend.exception;

/** Thrown when a requested interview session does not exist or does not belong to the current user. */
public class InterviewSessionNotFoundException extends RuntimeException {
    public InterviewSessionNotFoundException(String message) {
        super(message);
    }
}
