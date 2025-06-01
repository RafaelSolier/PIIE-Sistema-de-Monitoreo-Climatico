package com.example.piie.nodo.application;

import com.example.piie.estacion.domain.EstacionService;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.nodo.domain.NodoService;
import com.example.piie.nodo.dto.NodoCreateDTO;
import com.example.piie.nodo.dto.NodoResponseDTO;
import com.example.piie.nodo.dto.NodoUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/nodo")
public class NodoController {
    @Autowired
    private NodoService nodoService;
    @Autowired
    private EstacionService estacionService;

    @GetMapping
    public ResponseEntity<List<NodoResponseDTO>> getNodos() {
        List<NodoResponseDTO> nodos = nodoService.getAllNodos();
        return ResponseEntity.ok(nodos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodoResponseDTO> getNodoById(@PathVariable Long id) {
        return ResponseEntity.ok(nodoService.getNodoById(id));
    }

    @GetMapping("/ubicacion/{idEstacion}")
    public ResponseEntity<List<NodoResponseDTO>> getNodoByUbicacion(@PathVariable Long idEstacion) {
        return ResponseEntity.ok(nodoService.getNodosByEstacion(idEstacion));
    }

    @PostMapping
    public ResponseEntity<NodoResponseDTO> createNodo(@Valid @RequestBody NodoCreateDTO nodoCreateDTO) {
        NodoResponseDTO createdNodo = nodoService.createNodo(nodoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NodoResponseDTO> updateNodo(@PathVariable Long id, @Valid @RequestBody NodoUpdateDTO nodoUpdateDTO) {
        return ResponseEntity.ok(nodoService.updateNodo(id, nodoUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNodo(@PathVariable Long id) {
        nodoService.deleteNodo(id);
        return ResponseEntity.noContent().build();
    }
}
