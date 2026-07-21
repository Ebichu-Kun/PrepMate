package com.strawHat.backend.exception;

/** Thrown when a requested interview question does not exist. */
public class InterviewQuestionNotFoundException extends RuntimeException {
    public InterviewQuestionNotFoundException(String message) {
        super(message);
    }
}
