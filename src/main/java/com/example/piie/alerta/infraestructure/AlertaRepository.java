package com.example.piie.alerta.infraestructure;

import com.example.piie.alerta.domain.Alerta;
import com.example.piie.persona.domain.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    Page<Alerta> findAll(Pageable pageable);
}
