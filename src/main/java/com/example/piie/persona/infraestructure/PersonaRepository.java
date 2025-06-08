package com.example.piie.persona.infraestructure;

import com.example.piie.persona.domain.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Page<Persona> findAll(Pageable pageable);
    boolean existsByDni(String dni);
}
