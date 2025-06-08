package com.example.piie.exception;

public class EstacionAlreadyExistsException extends RuntimeException {
    public EstacionAlreadyExistsException(String message) {
        super(message);
    }
}
