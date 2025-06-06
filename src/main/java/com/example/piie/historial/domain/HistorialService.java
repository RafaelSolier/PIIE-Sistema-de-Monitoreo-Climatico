package com.example.piie.historial.domain;

import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.historial.dto.HistorialDto;
import com.example.piie.historial.infraestructure.HistorialRepository;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.nodo.infrastructure.NodoRepository;
import com.example.piie.parametro.domain.Parametro;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import com.example.piie.persona.domain.Persona;
import com.example.piie.persona.infraestructure.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialService {

    private final HistorialRepository historialRepository;
    private final PersonaRepository personaRepository;
    private final ParametroRepository parametroRepository;
    private final NodoRepository nodoRepository;

    /**
     * Lista todos los registros de historial.
     */
    public List<HistorialDto> findAll() {
        return historialRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un historial por ID.
     */
    public HistorialDto findById(Long id) {
        Historial entidad = historialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial con id " + id + " no encontrado"));
        return toDto(entidad);
    }

    /**
     * Crea un nuevo registro de historial.
     */
    public HistorialDto create(HistorialDto dto) {
        // 1) Validar entidades relacionadas
        Persona persona = personaRepository.findById(dto.getIdPersona())
                .orElseThrow(() -> new ResourceNotFoundException("Persona con id " + dto.getIdPersona() + " no encontrada"));
        Parametro parametro = parametroRepository.findById(dto.getIdParametro())
                .orElseThrow(() -> new ResourceNotFoundException("Parametro con id " + dto.getIdParametro() + " no encontrado"));
        Nodo nodo = nodoRepository.findById(dto.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("Nodo con id " + dto.getIdNodo() + " no encontrado"));

        // 2) Construir la entidad
        Historial entidad = new Historial();
        entidad.setPersona(persona);
        entidad.setParametro(parametro);
        entidad.setNodo(nodo);
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());

        Historial guardada = historialRepository.save(entidad);
        return toDto(guardada);
    }

    /**
     * Actualiza un registro de historial.
     */
    public HistorialDto update(Long id, HistorialDto dto) {
        Historial existente = historialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial con id " + id + " no encontrado"));

        Persona persona = personaRepository.findById(dto.getIdPersona())
                .orElseThrow(() -> new ResourceNotFoundException("Persona con id " + dto.getIdPersona() + " no encontrada"));
        Parametro parametro = parametroRepository.findById(dto.getIdParametro())
                .orElseThrow(() -> new ResourceNotFoundException("Parametro con id " + dto.getIdParametro() + " no encontrado"));
        Nodo nodo = nodoRepository.findById(dto.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("Nodo con id " + dto.getIdNodo() + " no encontrado"));

        existente.setPersona(persona);
        existente.setParametro(parametro);
        existente.setNodo(nodo);
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());

        Historial actualizado = historialRepository.save(existente);
        return toDto(actualizado);
    }

    /**
     * Elimina un registro de historial por ID.
     */
    public void delete(Long id) {
        Historial existente = historialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial con id " + id + " no encontrado"));
        historialRepository.delete(existente);
    }

    /* ---------- Métodos auxiliares (Entidad ↔ DTO) ---------- */

    private HistorialDto toDto(Historial entidad) {
        return new HistorialDto(
                entidad.getIdHistorial(),
                entidad.getPersona().getIdPersona(),
                entidad.getParametro().getIdParametro(),
                entidad.getNodo().getIdNodo(),
                entidad.getFechaInicio(),
                entidad.getFechaFin()
        );
    }
}