package com.example.piie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict exception")
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
