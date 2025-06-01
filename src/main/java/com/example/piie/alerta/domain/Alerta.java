package com.example.piie.alerta.domain;

import com.example.piie.nodo.domain.Nodo;
import com.example.piie.registroAlerta.domain.RegistroAlerta;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "alerta")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nodo")
    private Nodo nodo;

    private Double valorMax;
    private Double valorMin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String descripcion;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL)
    private List<RegistroAlerta> registros = new ArrayList<>();

    // getters y setters
}

