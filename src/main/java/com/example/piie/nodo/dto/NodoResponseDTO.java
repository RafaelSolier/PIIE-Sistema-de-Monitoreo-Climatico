package com.example.piie.nodo.dto;

import com.example.piie.nodo.domain.EstadoEnum;
import com.example.piie.parametro.domain.ParametroEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NodoResponseDTO {

        private Long idNodo;
        private Long idEstacion;
        private List<ParametroEnum> parametros;
        private EstadoEnum estado;
        private LocalDateTime fechaRegistro;
        private LocalDateTime fechaInstalacion;
        private String descripcion;
    }