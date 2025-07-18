package com.example.piie.parametro.infrastructure;

import com.example.piie.parametro.domain.Parametro;
import com.example.piie.parametro.domain.ParametroEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long> {
  
    List<Parametro> findByNombreIn(List<ParametroEnum> nombre);

    Parametro findByNombre(ParametroEnum nombre);
}
