package com.example.piie.estacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionResponseDTO {
    //private Long idEstacion;
    private String nombre;
    private String latitud;
    private String longitud;
    private String telefono;
    private String descripcion;
}