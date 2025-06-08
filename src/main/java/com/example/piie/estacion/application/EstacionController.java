package com.example.piie.estacion.application;

import com.example.piie.estacion.domain.EstacionService;
import com.example.piie.estacion.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estaciones")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // Requiere autenticación para todos los endpoints
public class EstacionController {

    final private EstacionService estacionService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<EstacionesDTO>> findAll() {
        List<EstacionesDTO> estaciones = estacionService.findAll();
        return ResponseEntity.ok(estaciones);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<EstacionResponseDTO>> findByNombre(
            @Valid @RequestBody EstacionRequestDTO estacionRequestDTO) {
        List<EstacionResponseDTO> estaciones = estacionService
                .findAllByNombre(estacionRequestDTO.getNombre());
        return ResponseEntity.ok(estaciones);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<EstacionResponseDTO> findById(@PathVariable Long id) {
        EstacionResponseDTO estacion = estacionService.findById(id);
        return ResponseEntity.ok(estacion);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionResponseDTO> create(@Valid @RequestBody EstacionRequestDTO estacionRequestDTO) {
        EstacionResponseDTO nuevaEstacion = estacionService.save(estacionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEstacion);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EstacionUpdateDTO estacionUpdateDTO) {
        EstacionResponseDTO estacionActualizada = estacionService.update(id, estacionUpdateDTO);
        return ResponseEntity.ok(estacionActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}