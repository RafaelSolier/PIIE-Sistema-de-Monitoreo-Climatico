package com.example.piie.medicion.dto;

import java.time.LocalDateTime;

/**
 * DTO para exponer mediciones tomadas por un nodo.
 */
public class MedicionDto {
    private Long idMedicion;
    private Long nodoId;
    private Long parametroId;
    private LocalDateTime fecha;
    private Double valor;

}