package com.example.piie.alerta.domain;

import com.example.piie.alerta.dto.AlertaDto;
import com.example.piie.alerta.infraestructure.AlertaRepository;
import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.nodo.infrastructure.NodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final NodoRepository nodoRepository;

    /**
     * Lista todas las alertas.
     */
//    public List<AlertaDto> findAll() {
//        return alertaRepository.findAll()
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
    public Page<AlertaDto> findAll(Pageable pageable) {
        return alertaRepository.findAll(pageable).map(this::toDto);
    }
    /**
     * Obtiene una alerta por ID.
     */
    public AlertaDto findById(Long id) {
        Alerta entidad = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta con id " + id + " no encontrada"));
        return toDto(entidad);
    }

    /**
     * Crea una nueva alerta.
     */
    public AlertaDto create(AlertaDto dto) {
        Nodo nodo = nodoRepository.findById(dto.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("Nodo con id " + dto.getIdNodo() + " no encontrado"));

        Alerta entidad = new Alerta();
        entidad.setNodo(nodo);
        entidad.setValorMax(dto.getValorMax());
        entidad.setValorMin(dto.getValorMin());
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
        entidad.setDescripcion(dto.getDescripcion());

        Alerta guardada = alertaRepository.save(entidad);
        return toDto(guardada);
    }

    /**
     * Actualiza una alerta existente.
     */
    public AlertaDto update(Long id, AlertaDto dto) {
        Alerta existente = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta con id " + id + " no encontrada"));

        Nodo nodo = nodoRepository.findById(dto.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("Nodo con id " + dto.getIdNodo() + " no encontrado"));

        existente.setNodo(nodo);
        existente.setValorMax(dto.getValorMax());
        existente.setValorMin(dto.getValorMin());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setDescripcion(dto.getDescripcion());

        Alerta actualizado = alertaRepository.save(existente);
        return toDto(actualizado);
    }

    /**
     * Elimina una alerta por ID.
     */
    public void delete(Long id) {
        Alerta existente = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta con id " + id + " no encontrada"));
        alertaRepository.delete(existente);
    }

    /* ---------- Métodos auxiliares (Entidad ↔ DTO) ---------- */

    private AlertaDto toDto(Alerta entidad) {
        return new AlertaDto(
                entidad.getIdAlerta(),
                entidad.getNodo().getIdNodo(),
                entidad.getValorMax(),
                entidad.getValorMin(),
                entidad.getFechaInicio(),
                entidad.getFechaFin(),
                entidad.getDescripcion()
        );
    }
}
