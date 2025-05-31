package com.example.piie.registroAlerta.domain;

import com.example.piie.alerta.domain.Alerta;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "registro_alerta")
public class RegistroAlerta {
    @EmbeddedId
    private RegistroAlertaId id;
    @MapsId("idAlerta")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alerta")
    private Alerta alerta;

    private Double valor;
    private String descripcion;

    // getters y setters
}
