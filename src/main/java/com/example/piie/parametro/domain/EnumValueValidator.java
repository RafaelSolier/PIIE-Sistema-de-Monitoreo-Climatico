package com.example.piie.parametro.domain;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;


// Validador
public class EnumValueValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            if (ignoreCase) {
                if (enumConstant.name().equalsIgnoreCase(value.trim())) {
                    return true;
                }
            } else {
                if (enumConstant.name().equals(value.trim())) {
                    return true;
                }
            }
        }

        // Mensaje simplificado
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Tipo de parámetro inválido\nValores válidos: " +
                        Arrays.toString(enumConstants)
        ).addConstraintViolation();

        return false;
    }
}