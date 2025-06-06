package com.example.piie.medicion.dto;

import com.example.piie.parametro.domain.ParametroEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicionCreateDTO {
    @NotNull(message = "El ID de nodo es obligatorio")
    private Long idNodo;

    @NotNull(message = "El ID de parámetro es obligatorio")
    private Long idParametro;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.0", message = "El valor no puede ser negativo")
    private Double valor;
}