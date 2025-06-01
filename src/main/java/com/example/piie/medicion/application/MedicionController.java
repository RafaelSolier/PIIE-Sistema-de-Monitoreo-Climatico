package com.example.piie.medicion.application;

import com.example.piie.medicion.domain.Medicion;
import com.example.piie.medicion.domain.MedicionService;
import com.example.piie.medicion.dto.MedicionCreateDTO;
import com.example.piie.medicion.dto.MedicionFilterDTO;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicion")
@RequiredArgsConstructor
public class MedicionController {

    private final MedicionService medicionService;

    @PostMapping("/nodo")
    public ResponseEntity<Medicion> createMedicion(@Valid @RequestBody MedicionCreateDTO medicionCreateDTO) {
        Medicion createdMedicion = medicionService.createMedicion(medicionCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedicion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicion> getMedicionById(@PathVariable Long id) {
        return ResponseEntity.ok(medicionService.getMedicionById(id));
    }

    @GetMapping
    public ResponseEntity<List<Medicion>> getAllMediciones() {
        return ResponseEntity.ok(medicionService.getAllMediciones());
    }

    @GetMapping("/nodo/{idNodo}")
    public ResponseEntity<List<Medicion>> getMedicionesByNodo(@PathVariable Long idNodo) {
        return ResponseEntity.ok(medicionService.getMedicionesByNodo(idNodo));
    }

    @GetMapping("/parametro/{idParametro}")
    public ResponseEntity<List<Medicion>> getMedicionesByParametro(@PathVariable Long idParametro) {
        return ResponseEntity.ok(medicionService.getMedicionesByParametro(idParametro));
    }


}
