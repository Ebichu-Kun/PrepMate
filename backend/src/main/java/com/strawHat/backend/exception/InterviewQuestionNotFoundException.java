package com.strawHat.backend.exception;

public class InterviewQuestionNotFoundException
        extends RuntimeException {

    public InterviewQuestionNotFoundException(String message) {
        super(message);
    }
}