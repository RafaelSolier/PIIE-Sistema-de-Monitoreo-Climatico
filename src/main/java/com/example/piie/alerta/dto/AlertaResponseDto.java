package com.example.piie.alerta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AlertaResponseDto {
    private Long idAlerta;
    private Long idNodo;
    private Double valorMax;
    private Double valorMin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String descripcion;
}