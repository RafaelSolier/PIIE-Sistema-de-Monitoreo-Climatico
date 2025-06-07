package com.example.piie.medicion.application;

import com.example.piie.medicion.domain.MedicionService;
import com.example.piie.medicion.dto.MedicionCreateDTO;
import com.example.piie.medicion.dto.MedicionDto;
import com.example.piie.medicion.dto.MedicionFilterDTO;
import jakarta.validation.Valid;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/mediciones")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MedicionController {

    private final MedicionService medicionService;
    private final ModelMapper modelMapper;

    // Últimas mediciones por nodo (agrupadas por parámetro)
    @GetMapping("/nodo/{idNodo}/last")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> getUltimasMedicionesPorNodo(@PathVariable Long idNodo) {
        List<MedicionDto> mediciones = medicionService.getMedicionesByNodo(idNodo);

        return ResponseEntity.ok(mediciones);
    }

    // Todas las mediciones de un nodo
    @GetMapping("/nodo/{idNodo}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> getMedicionesPorNodo(@PathVariable Long idNodo) {
        List<MedicionDto> mediciones = medicionService.getMedicionesByNodo(idNodo);
        return ResponseEntity.ok(mediciones);
    }

    // Detalle de una medición específica
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<MedicionDto> getMedicionById(@PathVariable Long id) {
        MedicionDto medicion = medicionService.getMedicionById(id);
        return ResponseEntity.ok(medicion);
    }

    // Filtrar mediciones (POST recomendado para filtros complejos)
    @PostMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> filtrarMediciones(@Valid @RequestBody MedicionFilterDTO filter) {
        List<MedicionDto> mediciones = medicionService.getMedicionesByFilters(filter);
        return ResponseEntity.ok(mediciones);
    }

    // Alternativa GET para filtros (opcional)
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> filtrarMedicionesGet(@Valid @RequestBody MedicionFilterDTO filter) {

        List<MedicionDto> mediciones = medicionService.getMedicionesByFilters(filter);
        return ResponseEntity.ok(mediciones);
    }

    // Registrar nueva medición (acceso solo para módulos externos)
    @PostMapping
    @PreAuthorize("hasRole('MODULO')")
    public ResponseEntity<MedicionDto> createMedicion(@Valid @RequestBody MedicionCreateDTO medicionCreateDTO) {
        MedicionDto nuevaMedicion = medicionService.createMedicion(medicionCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMedicion);
    }
}
