package com.example.piie.nodo.dto;

import com.example.piie.nodo.domain.EstadoEnum;
import com.example.piie.parametro.domain.ParametroEnum;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodoUpdateDTO {
    private EstadoEnum estado;

    private LocalDateTime fechaInstalacion;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    private List<ParametroEnum> parametros;

    @Size(min = 32, max = 32, message = "El token debe de tener una extensión de 32 caracteres")
    private String token;
}
