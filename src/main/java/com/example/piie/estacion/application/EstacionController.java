package com.example.piie.estacion.application;

import com.example.piie.estacion.domain.Estacion;
import com.example.piie.estacion.domain.EstacionService;
import com.example.piie.estacion.dto.EstacionDTO;
import com.example.piie.estacion.dto.EstacionRequestDTO;
import com.example.piie.estacion.dto.EstacionResponseDTO;
import com.example.piie.estacion.infrastructure.EstacionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estaciones")
public class EstacionController {
    @Autowired
    private EstacionService estacionService;

    @GetMapping()
    public List<EstacionDTO> findAll() {
        return estacionService.findAll();
    }

    @GetMapping
    public List<EstacionResponseDTO> findByNombre(@Valid @RequestBody EstacionRequestDTO estacionRequestDTO) {
        return estacionService.findAllByNombre(estacionRequestDTO.getNombre());
    }
}
