package com.example.carrental.exception;

public class UnavailableCarException extends RuntimeException {
    public UnavailableCarException(String message) {
        super(message);
    }
}
