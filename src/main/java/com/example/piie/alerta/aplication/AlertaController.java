package com.example.piie.alerta.aplication;

import com.example.piie.alerta.domain.AlertaService;
import com.example.piie.alerta.dto.AlertaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@Validated
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AlertaDto> getAll() {
        return alertaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AlertaDto getById(@PathVariable Long id) {
        return alertaService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlertaDto> create(@Valid @RequestBody AlertaDto dto) {
        AlertaDto creado = alertaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AlertaDto update(@PathVariable Long id, @Valid @RequestBody AlertaDto dto) {
        return alertaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
