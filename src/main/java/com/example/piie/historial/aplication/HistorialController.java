package com.example.piie.historial.aplication;

import com.example.piie.historial.domain.HistorialService;
import com.example.piie.historial.dto.HistorialDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
@Validated
public class HistorialController {

    private final HistorialService historialService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<HistorialDto> getAll(Pageable pageable) {
        return historialService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public HistorialDto getById(@PathVariable Long id) {
        return historialService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<HistorialDto> create(@Valid @RequestBody HistorialDto dto) {
        // El service puede comprobar internamente que el cliente solo cree Historial para su propio idPersona
        HistorialDto creado = historialService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public HistorialDto update(@PathVariable Long id, @Valid @RequestBody HistorialDto dto) {
        return historialService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
