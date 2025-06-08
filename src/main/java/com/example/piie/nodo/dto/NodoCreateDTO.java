package com.example.piie.nodo.dto;

import com.example.piie.nodo.domain.EstadoEnum;
import com.example.piie.parametro.domain.ParametroEnum;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodoCreateDTO {
    @NotNull(message = "El ID de estación es obligatorio")
    private Long idEstacion;

    @NotEmpty(message = "Debe tener al menos un parámetro")
    private List<ParametroEnum> parametros;

    @NotNull(message = "El estado es obligatorio")
    // @EnumNamePattern(regexp = "ACTIVO|INACTIVO|MANTENIMIENTO",
    //         message = "Debe ser uno de: ACTIVO, INACTIVO o MANTENIMIENTO")
    private EstadoEnum estado;

    @FutureOrPresent(message = "La fecha de instalación debe ser presente o futura")
    private LocalDateTime fechaInstalacion;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
}