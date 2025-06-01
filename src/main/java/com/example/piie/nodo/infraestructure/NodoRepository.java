package com.example.piie.nodo.infraestructure;

import com.example.piie.nodo.domain.Nodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodoRepository extends JpaRepository<Nodo, Long> {
}
