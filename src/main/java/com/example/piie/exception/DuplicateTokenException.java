package com.example.piie.exception;

public class DuplicateTokenException extends RuntimeException {
    public DuplicateTokenException(String message) {
        super(message);
    }
}