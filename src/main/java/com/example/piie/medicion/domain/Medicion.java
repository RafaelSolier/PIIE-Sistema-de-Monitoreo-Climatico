package com.example.piie.medicion.domain;

import com.example.piie.nodo.domain.Nodo;
import com.example.piie.parametro.domain.Parametro;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "medicion")
public class Medicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nodo")
    private Nodo nodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parametro")
    private Parametro parametro;

    private LocalDateTime fecha;
    private Double valor;

    // getters y setters
}
