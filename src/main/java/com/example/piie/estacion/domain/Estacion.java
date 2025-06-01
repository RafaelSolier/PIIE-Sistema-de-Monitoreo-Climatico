package com.example.piie.estacion.domain;

import com.example.piie.nodo.domain.Nodo;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "estacion")
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstacion;

    @Column(nullable = false, unique = true)
    private String nombre;

    private Double latitud;

    private Double longitud;

    private String telefono;

    private String descripcion;

    @OneToMany(mappedBy = "estacion", cascade = CascadeType.ALL)
    private List<Nodo> nodos = new ArrayList<>();

    // getters y setters
}

