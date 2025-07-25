package com.example.piie.persona.aplication;

import com.example.piie.persona.domain.PersonaService;
import com.example.piie.persona.dto.PersonaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<PersonaDto> getAll(Pageable pageable) {
        return personaService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and principal.id == #id)")
    public PersonaDto getById(@PathVariable Long id) {
        return personaService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonaDto> create(@Valid @RequestBody PersonaDto dto) {
        PersonaDto creado = personaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and principal.id == #id)")
    public PersonaDto update(@PathVariable Long id, @Valid @RequestBody PersonaDto dto) {
        return personaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}