package com.example.piie.usuario.domain;

import com.example.piie.persona.domain.Persona;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    private Long idPersona;    // FK a Persona
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @Column(nullable = false)
    private String rol;        // e.g. "CLIENTE", "ADMIN"

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasenia; // almacenada encriptada

    // getters y setters
}