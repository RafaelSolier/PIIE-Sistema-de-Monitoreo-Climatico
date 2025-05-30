package com.example.piie.historial.domain;

import com.example.piie.nodo.domain.Nodo;
import com.example.piie.parametro.domain.Parametro;
import com.example.piie.persona.domain.Persona;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historial")
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parametro")
    private Parametro parametro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nodo")
    private Nodo nodo;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    // getters y setters
}
