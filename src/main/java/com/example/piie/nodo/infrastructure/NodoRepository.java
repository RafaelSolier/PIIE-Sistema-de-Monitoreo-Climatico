package com.example.piie.nodo.infrastructure;

import com.example.piie.estacion.domain.Estacion;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.parametro.domain.Parametro;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodoRepository extends JpaRepository<Nodo, Long> {
    List<Nodo> findByEstacion(Estacion estacion);

    List<Nodo> findByParametrosContaining(Parametro parametro);

    boolean existsByTokenAndIdNodoNot(@Size(min = 32, max = 32, message = "El token debe de tener una extensión de 32 caracteres") String token, Long id);

}
