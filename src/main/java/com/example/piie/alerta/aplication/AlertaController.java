package com.example.piie.alerta.aplication;

import com.example.piie.alerta.domain.AlertaService;
import com.example.piie.alerta.dto.AlertaDto;
import com.example.piie.alerta.dto.AlertaResponseDto;
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
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@Validated
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AlertaResponseDto> getAll(Pageable pageable) {
        return alertaService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AlertaResponseDto getById(@PathVariable Long id) {
        return alertaService.findById(id);
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<AlertaDto> create(@Valid @RequestBody AlertaDto dto) {
//        AlertaDto creado = alertaService.create(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
//    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlertaResponseDto> create(@Valid @RequestBody AlertaDto dto) {
        AlertaResponseDto creado = alertaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public AlertaDto update(@PathVariable Long id, @Valid @RequestBody AlertaDto dto) {
//        return alertaService.update(id, dto);
//    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AlertaResponseDto update(@PathVariable Long id, @Valid @RequestBody AlertaDto dto) {
        return alertaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
