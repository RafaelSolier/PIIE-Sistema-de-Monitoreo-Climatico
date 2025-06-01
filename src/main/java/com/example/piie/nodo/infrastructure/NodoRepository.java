package com.example.piie.nodo.infrastructure;

import com.example.piie.estacion.domain.Estacion;
import com.example.piie.nodo.domain.Nodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodoRepository extends JpaRepository<Nodo, Long> {
    List<Nodo> findByEstacion(Estacion estacion);
}
