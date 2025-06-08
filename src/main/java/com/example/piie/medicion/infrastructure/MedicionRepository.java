package com.example.piie.medicion.infrastructure;

import com.example.piie.medicion.domain.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
    List<Medicion> findByNodoIdNodo(Long idNodo);
    List<Medicion> findByParametroIdParametro(Long idParametro);

}
