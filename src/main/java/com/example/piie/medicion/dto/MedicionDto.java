package com.example.piie.medicion.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para exponer mediciones tomadas por un nodo.
 */
@Data
public class MedicionDto {
    private Long idMedicion;
    private Long nodo;
    private Long parametro;
    private LocalDateTime fecha;
    private Double valor;

}