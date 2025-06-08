package com.example.piie.parametro.dto;

import com.example.piie.parametro.domain.ParametroEnum;
import com.example.piie.parametro.domain.ValidEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Data
public class ParametroRequestDto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @ValidEnum(enumClass = ParametroEnum.class,  message = "Tipo de parámetro inválido")
    private String nombre;

    @NotBlank(message = "Debe añadir una descripción")
    @Size(min = 3, max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    @NotBlank(message = "La unidad no puede estar vacía")
    private String unidad;
}

