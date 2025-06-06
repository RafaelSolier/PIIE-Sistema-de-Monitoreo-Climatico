package com.example.piie.nodo.application;

import com.example.piie.nodo.domain.NodoService;
import com.example.piie.nodo.dto.NodoCreateDTO;
import com.example.piie.nodo.dto.NodoResponseDTO;
import com.example.piie.nodo.dto.NodoUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nodos")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class NodoController {

    private final NodoService nodoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<NodoResponseDTO>> getAllNodos() {
        List<NodoResponseDTO> nodos = nodoService.getAllNodos();
        return ResponseEntity.ok(nodos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<NodoResponseDTO> getNodoById(@PathVariable Long id) {
        NodoResponseDTO nodo = nodoService.getNodoById(id);
        return ResponseEntity.ok(nodo);
    }

    @GetMapping("/estacion/{idEst}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<NodoResponseDTO>> getNodosByEstacion(@PathVariable Long idEst) {
        List<NodoResponseDTO> nodos = nodoService.getNodosByEstacion(idEst);
        return ResponseEntity.ok(nodos);
    }

    @GetMapping("/parametro/{idPar}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<NodoResponseDTO>> getNodosByParametro(@PathVariable Long idPar) {
        // Implementar este metodo en el servicio si no existe
        List<NodoResponseDTO> nodos = nodoService.getNodosByParametro(idPar);
        return ResponseEntity.ok(nodos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NodoResponseDTO> createNodo(@Valid @RequestBody NodoCreateDTO nodoCreateDTO) {
        NodoResponseDTO nuevoNodo = nodoService.createNodo(nodoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoNodo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NodoResponseDTO> updateNodo(@PathVariable Long id, @Valid @RequestBody NodoUpdateDTO nodoUpdateDTO) {
        NodoResponseDTO nodoActualizado = nodoService.updateNodo(id, nodoUpdateDTO);
        return ResponseEntity.ok(nodoActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNodo(@PathVariable Long id) {
        nodoService.deleteNodo(id);
        return ResponseEntity.noContent().build();
    }
}
