package com.example.piie.persona.domain;

import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.persona.dto.PersonaDto;
import com.example.piie.persona.infraestructure.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    /**
     * Lista todas las personas.
     */
//    public List<PersonaDto> findAll() {
//        return personaRepository.findAll()
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
    public Page<PersonaDto> findAll(Pageable pageable) {
        return personaRepository.findAll(pageable).map(this::toDto);
    }

    /**
     * Obtiene una persona por ID, o lanza ResourceNotFoundException si no existe.
     */
    public PersonaDto findById(Long id) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona con id " + id + " no encontrada"));
        return toDto(entidad);
    }

    /**
     * Crea una nueva persona.
     */
//    public PersonaDto create(PersonaDto dto) {
//        Persona entidad = toEntity(dto);
//        Persona guardada = personaRepository.save(entidad);
//        return toDto(guardada);
//    }
    public PersonaDto create(PersonaDto dto) {
        if (personaRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        Persona entidad = toEntity(dto);
        Persona guardada = personaRepository.save(entidad);
        return toDto(guardada);
    }

    /**
     * Actualiza una persona existente.
     */
    public PersonaDto update(Long id, PersonaDto dto) {
        Persona existente = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona con id " + id + " no encontrada"));

        existente.setDni(dto.getDni());
        existente.setNombre(dto.getNombre());
        existente.setApellidos(dto.getApellidos());
        existente.setCelular(dto.getCelular());
        existente.setSexo(dto.getSexo());

        Persona actualizada = personaRepository.save(existente);
        return toDto(actualizada);
    }

    /**
     * Elimina una persona por ID.
     */
    public void delete(Long id) {
        Persona existente = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona con id " + id + " no encontrada"));
        personaRepository.delete(existente);
    }

    /* ---------- Métodos auxiliares (Entidad ↔ DTO) ---------- */

    private PersonaDto toDto(Persona entidad) {
        return new PersonaDto(
                entidad.getIdPersona(),
                entidad.getDni(),
                entidad.getNombre(),
                entidad.getApellidos(),
                entidad.getCelular(),
                entidad.getSexo()
        );
    }

    private Persona toEntity(PersonaDto dto) {
        Persona entidad = new Persona();
        entidad.setDni(dto.getDni());
        entidad.setNombre(dto.getNombre());
        entidad.setApellidos(dto.getApellidos());
        entidad.setCelular(dto.getCelular());
        entidad.setSexo(dto.getSexo());
        return entidad;
    }
}
