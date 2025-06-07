package com.example.piie.estacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionUpdateDTO {
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    // @NotNull(message = "La dirección no puede estar vacía")
    private Double latitud;

    // @NotNull(message = "La dirección no puede estar vacía")
    private Double longitud;

    @Pattern(regexp = "^(\\+51)?(1\\d{7}|[4-9]\\d{7}|9\\d{8})$", message = "El número debe ser un teléfono fijo o celular válido de Perú")
    @Size(max = 13, message = "El teléfono no puede exceder los 13 caracteres")
    private String telefono;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
}
