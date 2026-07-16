package com.strawHat.backend.exception;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(String msg)
    {
        super(msg);
    }
}
