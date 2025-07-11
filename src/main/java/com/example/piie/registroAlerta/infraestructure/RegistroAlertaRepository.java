package com.example.piie.registroAlerta.infraestructure;

import com.example.piie.registroAlerta.domain.RegistroAlerta;
import com.example.piie.registroAlerta.domain.RegistroAlertaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroAlertaRepository extends JpaRepository<RegistroAlerta, RegistroAlertaId> {
//    List<RegistroAlerta> findByIdAlerta(RegistroAlertaId idAlerta);
}
