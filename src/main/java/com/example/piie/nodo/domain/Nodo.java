package com.example.piie.nodo.domain;

import com.example.piie.estacion.domain.Estacion;
import com.example.piie.medicion.domain.Medicion;
import com.example.piie.alerta.domain.Alerta;
import com.example.piie.parametro.domain.Parametro;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "nodo")
public class Nodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estacion")
    private Estacion estacion;

    @ManyToMany
    @JoinTable(
            name = "nodo_parametro",
            joinColumns = @JoinColumn(name = "id_nodo"),
            inverseJoinColumns = @JoinColumn(name = "id_parametro")
    )
    private List<Parametro> parametros = new ArrayList<>();

    @Column(nullable = false)
    private String estado; // "ACTIVO", "INACTIVO", "RETIRADO"

    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaInstalacion;
    private String descripcion;

    @OneToMany(mappedBy = "nodo", cascade = CascadeType.ALL)
    private List<Medicion> mediciones = new ArrayList<>();

    @OneToMany(mappedBy = "nodo", cascade = CascadeType.ALL)
    private List<Alerta> alertas = new ArrayList<>();

    // getters y setters
}
