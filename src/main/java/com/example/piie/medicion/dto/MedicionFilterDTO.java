package com.example.piie.medicion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicionFilterDTO {
    private Long idNodo;
    private Long idParametro;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}