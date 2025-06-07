package com.example.piie.usuario.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Integer rol; // Por ejemplo 0 = CLIENTE, 1 = ADMIN

    @NotBlank(message = "El DNI es obligatorio")

    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Size(min = 9,max=9, message = "El celular no puede superar 15 dígitos")
    private String celular;

    @NotBlank(message = "El sexo es obligatorio")
    @Size(min = 1, max = 1, message = "El sexo debe ser 'M' o 'F'")
    private String sexo;
}