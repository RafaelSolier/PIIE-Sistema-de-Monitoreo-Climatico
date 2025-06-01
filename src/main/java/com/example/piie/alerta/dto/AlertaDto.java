package com.example.piie.alerta.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDto {
    private Long idAlerta;

    @NotNull(message = "El idNodo es obligatorio")
    private Long idNodo;

    @NotNull(message = "El valorMax es obligatorio")
    private Double valorMax;

    @NotNull(message = "El valorMin es obligatorio")
    private Double valorMin;

    @NotNull(message = "La fechaInicio es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fechaFin es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fechaFin;

    private String descripcion;
}