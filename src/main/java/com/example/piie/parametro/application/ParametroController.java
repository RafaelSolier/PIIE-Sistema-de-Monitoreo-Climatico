package com.example.piie.parametro.application;

import com.example.piie.parametro.domain.Parametro;
import com.example.piie.parametro.domain.ParametroService;
import com.example.piie.parametro.dto.ParametroDto;
import com.example.piie.parametro.dto.ParametroRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parametros")
@RequiredArgsConstructor
@Validated
public class ParametroController {

    private final ParametroService parametroService;
    private final ModelMapper modelMapper;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<ParametroDto>> getAllParametros() {
        List<Parametro> parametros = parametroService.findAll();
        List<ParametroDto> response = parametros.stream()
                .map(p -> modelMapper.map(p, ParametroDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<ParametroDto> getParametroById(@PathVariable Long id) {
        Parametro parametro = parametroService.findById(id);
        return ResponseEntity.ok(modelMapper.map(parametro, ParametroDto.class));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametroDto> createParametro(@Valid @RequestBody ParametroRequestDto parametroRequestDto) {
        Parametro parametro = modelMapper.map(parametroRequestDto, Parametro.class);
        Parametro creado = parametroService.createParametro(parametro);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(creado, ParametroDto.class));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametroDto> updateParametro(@PathVariable Long id, @Valid @RequestBody ParametroRequestDto parametroRequestDto) {
        Parametro parametro = modelMapper.map(parametroRequestDto, Parametro.class);
        Parametro actualizado = parametroService.update(id, parametro);
        return ResponseEntity.ok(modelMapper.map(actualizado, ParametroDto.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParametro(@PathVariable Long id) {
        parametroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}