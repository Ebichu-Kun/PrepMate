package com.strawHat.backend.exception;

public class InterviewSessionNotFoundException extends RuntimeException{
    public InterviewSessionNotFoundException(String msg)
    {
        super(msg);
    }
}
