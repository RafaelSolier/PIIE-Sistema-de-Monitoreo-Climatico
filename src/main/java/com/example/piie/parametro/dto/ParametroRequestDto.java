package com.example.piie.parametro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParametroRequestDto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "Debe añadir una descripción")
    @Size(min = 3, max = 100, message = "La descripción no puede exceder los 100 caracteres")
    private String descripcion;

    @NotBlank(message = "La unidad no puede estar vacía")
    private String unidad;
}
