package com.example.piie.parametro.infraestructure;

import com.example.piie.parametro.domain.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
