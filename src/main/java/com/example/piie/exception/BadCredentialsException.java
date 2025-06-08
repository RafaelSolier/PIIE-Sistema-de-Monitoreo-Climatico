package com.example.piie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }
    public BadCredentialsException() { super("CREDENCIALES_INVALIDAS\nUsuario o contraseña incorrectos");}
}
