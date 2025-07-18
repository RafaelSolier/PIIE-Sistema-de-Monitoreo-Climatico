package com.example.piie.parametro.domain;

import com.example.piie.nodo.domain.Nodo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "parametro")
public class Parametro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParametro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ParametroEnum nombre;

    private String unidad;

    private String descripcion;

    @ManyToMany(mappedBy = "parametros")
    @JsonBackReference
    private List<Nodo> nodos = new ArrayList<>();

    // getters y setters
}
