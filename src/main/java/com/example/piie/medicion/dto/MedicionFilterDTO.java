package com.example.piie.medicion.dto;

import com.example.piie.parametro.domain.ParametroEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicionFilterDTO {
    private Long idNodo;
    private ParametroEnum parametro;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}