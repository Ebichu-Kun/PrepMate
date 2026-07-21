package com.strawHat.backend.exception;

/** Thrown when a requested note does not exist or does not belong to the current user. */
public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
