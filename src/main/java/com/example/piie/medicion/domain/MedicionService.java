package com.example.piie.medicion.domain;

import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.medicion.dto.MedicionDto;
import com.example.piie.medicion.dto.MedicionFilterDTO;
import com.example.piie.medicion.infrastructure.MedicionRepository;
import com.example.piie.nodo.infrastructure.NodoRepository;
import com.example.piie.medicion.dto.MedicionCreateDTO;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.parametro.domain.Parametro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicionService {

    private final MedicionRepository medicionRepository;

    private final NodoRepository nodoRepository;

    private final ParametroRepository parametroRepository;

    private final ModelMapper modelMapper;


    public MedicionDto createMedicion(@Valid MedicionCreateDTO medicionCreateDTO) {
        Nodo nodo = nodoRepository.findById(medicionCreateDTO.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un nodo con ID: "+medicionCreateDTO.getIdNodo()));

        Parametro parametro = parametroRepository.findById(medicionCreateDTO.getIdParametro())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un parametro con ID: "+medicionCreateDTO.getIdParametro()));

        Medicion medicion = modelMapper.map(medicionCreateDTO, Medicion.class);
        medicion.setNodo(nodo);
        medicion.setParametro(parametro);

        return modelMapper.map(medicionRepository.save(medicion), MedicionDto.class);
    }

    public MedicionDto getMedicionById(Long id) {
        Medicion medicion = medicionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medición no encontrada"));
        return modelMapper.map(medicion, MedicionDto.class);
    }

    public List<MedicionDto> getAllMediciones() {
        return medicionRepository.findAll().stream()
                .map(n -> modelMapper.map(n, MedicionDto.class))
                .collect(Collectors.toList());
    }

    // READ by Nodo
    public List<MedicionDto> getMedicionesByNodo(Long idNodo) {
        return medicionRepository.findByNodoIdNodo(idNodo)
                .stream()
                .map(n -> modelMapper.map(n, MedicionDto.class))
                .collect(Collectors.toList());
    }

    // READ by Parametro
    public List<MedicionDto> getMedicionesByParametro(Long idParametro) {
        return medicionRepository.findByParametroIdParametro(idParametro)
                .stream()
                .map(n -> modelMapper.map(n, MedicionDto.class))
                .collect(Collectors.toList());
    }


    // DELETE
    public void deleteMedicion(Long id) {
        if (!medicionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medición no encontrada por ID: " + id);
        }
        medicionRepository.deleteById(id);
    }

    public List<MedicionDto> getMedicionesByFilters(MedicionFilterDTO filter) {
        // Validación de fechas
        if (filter.getFechaInicio() != null && filter.getFechaFin() != null &&
                filter.getFechaInicio().isAfter(filter.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        List<Medicion> mediciones = medicionRepository.findByFilters(
                filter.getIdNodo(),
                filter.getIdParametro(),
                filter.getFechaInicio(),
                filter.getFechaFin());

        return mediciones.stream()
                .map(n -> modelMapper.map(n, MedicionDto.class))
                .collect(Collectors.toList());
    }
}
