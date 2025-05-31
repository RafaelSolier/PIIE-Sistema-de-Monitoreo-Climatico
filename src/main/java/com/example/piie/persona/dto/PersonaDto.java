package com.example.piie.persona.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO para exponer y recibir datos de Persona.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDto {
    private Long idPersona;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Size(max = 15, message = "El celular no puede tener más de 15 dígitos")
    private String celular;

    @NotBlank(message = "El sexo es obligatorio")
    @Size(min = 1, max = 1, message = "El sexo debe ser 'M' o 'F'")
    private String sexo;
}