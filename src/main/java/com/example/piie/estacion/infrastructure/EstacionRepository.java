package com.example.piie.estacion.infrastructure;

import com.example.piie.estacion.domain.Estacion;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {

    List<Estacion> findAllByNombre(String nombre);

    Estacion findByNombre(String nombre);
}
