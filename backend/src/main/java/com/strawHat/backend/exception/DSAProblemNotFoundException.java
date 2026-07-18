package com.strawHat.backend.exception;

public class DSAProblemNotFoundException
        extends RuntimeException {

    public DSAProblemNotFoundException(String message) {
        super(message);
    }
}