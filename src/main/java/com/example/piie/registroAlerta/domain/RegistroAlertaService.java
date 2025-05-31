package com.example.piie.registroAlerta.domain;


import com.example.piie.alerta.domain.Alerta;
import com.example.piie.alerta.infraestructure.AlertaRepository;
import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.registroAlerta.dto.RegistroAlertaDto;
import com.example.piie.registroAlerta.infraestructure.RegistroAlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para RegistroAlerta (CRUD). La PK es compuesta (idAlerta, fecha).
 */
@Service
@RequiredArgsConstructor
public class RegistroAlertaService {

    private final RegistroAlertaRepository registroAlertaRepository;
    private final AlertaRepository alertaRepository;

    /**
     * Lista todos los registros de alerta.
     */
    public List<RegistroAlertaDto> findAll() {
        return registroAlertaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un registro de alerta por PK compuesta (idAlerta + fecha).
     */
    public RegistroAlertaDto findById(Long idAlerta, java.time.LocalDateTime fecha) {
        RegistroAlertaId pk = new RegistroAlertaId(idAlerta, fecha);
        RegistroAlerta entidad = registroAlertaRepository.findById(pk)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "RegistroAlerta con idAlerta " + idAlerta + " y fecha " + fecha + " no encontrado"));
        return toDto(entidad);
    }

    /**
     * Crea un nuevo registro de alerta.
     */
    public RegistroAlertaDto create(RegistroAlertaDto dto) {
        Alerta alerta = alertaRepository.findById(dto.getIdAlerta())
                .orElseThrow(() -> new ResourceNotFoundException("Alerta con id " + dto.getIdAlerta() + " no encontrada"));

        RegistroAlerta entidad = new RegistroAlerta();
        RegistroAlertaId pk = new RegistroAlertaId(dto.getIdAlerta(), dto.getFecha());
        entidad.setId(pk);
        entidad.setAlerta(alerta);
        entidad.setValor(dto.getValor());
        entidad.setDescripcion(dto.getDescripcion());

        RegistroAlerta guardado = registroAlertaRepository.save(entidad);
        return toDto(guardado);
    }

    /**
     * Actualiza un registro de alerta existente.
     */
    public RegistroAlertaDto update(Long idAlerta, java.time.LocalDateTime fecha, RegistroAlertaDto dto) {
        RegistroAlertaId pk = new RegistroAlertaId(idAlerta, fecha);
        RegistroAlerta existente = registroAlertaRepository.findById(pk)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "RegistroAlerta con idAlerta " + idAlerta + " y fecha " + fecha + " no encontrado"));

        existente.setValor(dto.getValor());
        existente.setDescripcion(dto.getDescripcion());

        RegistroAlerta actualizado = registroAlertaRepository.save(existente);
        return toDto(actualizado);
    }

    /**
     * Elimina un registro de alerta por PK compuesta.
     */
    public void delete(Long idAlerta, java.time.LocalDateTime fecha) {
        RegistroAlertaId pk = new RegistroAlertaId(idAlerta, fecha);
        RegistroAlerta existente = registroAlertaRepository.findById(pk)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "RegistroAlerta con idAlerta " + idAlerta + " y fecha " + fecha + " no encontrado"));
        registroAlertaRepository.delete(existente);
    }

    /* ---------- Métodos auxiliares (Entidad ↔ DTO) ---------- */

    private RegistroAlertaDto toDto(RegistroAlerta entidad) {
        return new RegistroAlertaDto(
                entidad.getId().getIdAlerta(),
                entidad.getId().getFecha(),
                entidad.getValor(),
                entidad.getDescripcion()
        );
    }
}
