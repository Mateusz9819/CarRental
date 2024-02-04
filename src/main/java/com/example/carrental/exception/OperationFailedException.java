package com.example.carrental.exception;

public class OperationFailedException extends RuntimeException {
    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
