package com.example.piie.historial.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialDto {
    private Long idHistorial;

    @NotNull(message = "El idPersona es obligatorio")
    private Long idPersona;

    @NotNull(message = "El idParametro es obligatorio")
    private Long idParametro;

    @NotNull(message = "El idNodo es obligatorio")
    private Long idNodo;

    @NotNull(message = "La fechaInicio es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fechaFin es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaFin;
}