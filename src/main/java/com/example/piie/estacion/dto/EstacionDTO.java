package com.example.piie.estacion.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía")
    private Double latitud;

    @NotBlank(message = "La dirección no puede estar vacía")
    private Double longitud;

    @Pattern(regexp = "^(\\+51)?(1\\d{7}|[4-9]\\d{7}|9\\d{8})$", message = "El número debe ser un teléfono fijo o celular válido de Perú")
    @Size(max = 13, message = "El teléfono no puede exceder los 13 caracteres")
    private String telefono;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
}