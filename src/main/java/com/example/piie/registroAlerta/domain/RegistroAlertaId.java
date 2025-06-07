package com.example.piie.registroAlerta.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

//@Embeddable
//@Data
//public class RegistroAlertaId implements Serializable {
//    private Long idAlerta;
//    private LocalDateTime fecha;
//    // equals & hashCode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RegistroAlertaId that = (RegistroAlertaId) o;
//        return Objects.equals(idAlerta, that.idAlerta) &&
//                Objects.equals(fecha, that.fecha);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(idAlerta, fecha);
//    }
//}

@Embeddable
@Data

public class RegistroAlertaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_alerta")
    private Long idAlerta;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    // Constructor con argumentos
    public RegistroAlertaId(Long idAlerta, LocalDateTime fecha) {
        this.idAlerta = idAlerta;
        this.fecha = fecha;
    }

    // Constructor sin argumentos (necesario para JPA)
    public RegistroAlertaId() {
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroAlertaId that = (RegistroAlertaId) o;
        return Objects.equals(idAlerta, that.idAlerta) &&
                Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlerta, fecha);
    }
}