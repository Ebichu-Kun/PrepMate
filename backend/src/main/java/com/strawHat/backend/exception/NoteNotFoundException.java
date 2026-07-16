package com.strawHat.backend.exception;

public class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(String msg)
    {
        super(msg);
    }
}
