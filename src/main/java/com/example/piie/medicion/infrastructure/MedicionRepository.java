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

    // Metodo para búsqueda con filtros
    @Query("SELECT m FROM Medicion m WHERE " +
            "(:idNodo IS NULL OR m.nodo.idNodo = :idNodo) AND " +
            "(:idParametro IS NULL OR m.parametro.idParametro = :idParametro) AND " +
            "(:fechaInicio IS NULL OR m.fecha >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR m.fecha <= :fechaFin)")
    List<Medicion> findByFilters(
            @Param("idNodo") Long idNodo,
            @Param("idParametro") Long idParametro,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}
