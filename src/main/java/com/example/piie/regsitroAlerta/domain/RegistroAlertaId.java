package com.example.piie.regsitroAlerta.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class RegistroAlertaId implements Serializable {
    private Long idAlerta;
    private LocalDateTime fecha;
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