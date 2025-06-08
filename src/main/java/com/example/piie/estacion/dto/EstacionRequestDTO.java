package com.example.piie.estacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotNull(message = "La dirección no puede estar vacía")
    private Double latitud;
    @NotNull(message = "La dirección no puede estar vacía")
    private Double longitud;

}