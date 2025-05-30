package com.example.piie.estacion.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Data
@Entity
@Table(name = "estacion")
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstacion;

    @Column(nullable = false)
    private String nombre;

    private String direccion;

    private String telefono;

    private String descripcion;

    @OneToMany(mappedBy = "estacion", cascade = CascadeType.ALL)
    private List<Nodo> nodos = new ArrayList<>();

    // getters y setters
}

