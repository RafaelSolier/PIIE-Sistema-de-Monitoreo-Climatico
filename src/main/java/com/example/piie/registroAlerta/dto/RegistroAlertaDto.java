package com.example.piie.registroAlerta.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAlertaDto {
    @NotNull(message = "El idAlerta es obligatorio")
    private Long idAlerta;

    @NotNull(message = "La fecha es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fecha;

    @NotNull(message = "El valor es obligatorio")
    private Double valor;

    private String descripcion;
}