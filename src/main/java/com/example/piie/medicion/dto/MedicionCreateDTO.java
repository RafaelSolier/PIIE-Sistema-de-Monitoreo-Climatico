package com.example.piie.medicion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicionCreateDTO {
    @NotNull(message = "El ID de nodo es obligatorio")
    private Long idNodo;

    private Double temperatura;
    private Double humedad;
    private Double presion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotBlank(message = "El token es obligatorio")
    @Size(min = 32, max = 32, message = "El token debe de tener una extensión de 32 caracteres")
    private String token;
}