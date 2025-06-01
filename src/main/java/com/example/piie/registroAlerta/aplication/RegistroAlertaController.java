package com.example.piie.registroAlerta.aplication;

import com.example.piie.registroAlerta.domain.RegistroAlertaService;
import com.example.piie.registroAlerta.dto.RegistroAlertaDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registro-alerta")
@RequiredArgsConstructor
@Validated
public class RegistroAlertaController {

    private final RegistroAlertaService registroAlertaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegistroAlertaDto> getAll() {
        return registroAlertaService.findAll();
    }

    @GetMapping("/{idAlerta}/{fecha}")
    @PreAuthorize("hasRole('ADMIN')")
    public RegistroAlertaDto getById(
            @PathVariable Long idAlerta,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha
    ) {
        return registroAlertaService.findById(idAlerta, fecha);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegistroAlertaDto> create(@Valid @RequestBody RegistroAlertaDto dto) {
        RegistroAlertaDto creado = registroAlertaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{idAlerta}/{fecha}")
    @PreAuthorize("hasRole('ADMIN')")
    public RegistroAlertaDto update(
            @PathVariable Long idAlerta,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
            @Valid @RequestBody RegistroAlertaDto dto
    ) {
        return registroAlertaService.update(idAlerta, fecha, dto);
    }

    @DeleteMapping("/{idAlerta}/{fecha}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long idAlerta,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha
    ) {
        registroAlertaService.delete(idAlerta, fecha);
        return ResponseEntity.noContent().build();
    }
}