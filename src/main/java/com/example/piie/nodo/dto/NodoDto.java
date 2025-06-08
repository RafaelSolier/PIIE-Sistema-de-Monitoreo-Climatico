package com.example.piie.nodo.dto;

import com.example.piie.parametro.dto.ParametroDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NodoDto {
    private Long idNodo;
    private String estacionNombre;
    private String estado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaInstalacion;
    private String descripcion;
    private List<ParametroDto> parametros;


}