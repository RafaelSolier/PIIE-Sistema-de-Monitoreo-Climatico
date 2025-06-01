package com.example.piie.medicion.domain;

import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.medicion.dto.MedicionFilterDTO;
import com.example.piie.medicion.infrastructure.MedicionRepository;
import com.example.piie.nodo.infrastructure.NodoRepository;
import com.example.piie.medicion.dto.MedicionCreateDTO;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.parametro.domain.Parametro;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
@Transactional

public class MedicionService {
    @Autowired
    private MedicionRepository medicionRepository;
    @Autowired
    private NodoRepository nodoRepository;
    @Autowired
    private ParametroRepository parametroRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Medicion createMedicion(@Valid MedicionCreateDTO medicionCreateDTO) {
        Nodo nodo = nodoRepository.findById(medicionCreateDTO.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("Nodo no encontrado"));

        Parametro parametro = parametroRepository.findByNombre(medicionCreateDTO.getParametro());

        Medicion medicion = modelMapper.map(medicionCreateDTO, Medicion.class);
        medicion.setNodo(nodo);
        medicion.setParametro(parametro);

        return medicionRepository.save(medicion);
    }

    public Medicion getMedicionById(Long id) {
        Medicion medicion = medicionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medición no encontrada"));
        return medicion;
    }

    public List<Medicion> getAllMediciones() {
        return medicionRepository.findAll();
    }

    // READ by Nodo
    public List<Medicion> getMedicionesByNodo(Long idNodo) {
        return medicionRepository.findByNodoIdNodo(idNodo);
    }

    // READ by Parametro
    public List<Medicion> getMedicionesByParametro(Long idParametro) {
        return medicionRepository.findByParametroIdParametro(idParametro);
    }


    // DELETE
    public void deleteMedicion(Long id) {
        if (!medicionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medición no encontrada");
        }
        medicionRepository.deleteById(id);
    }

    public List<Medicion> getMedicionesByFilters(MedicionFilterDTO filter) {
        // Validación de fechas
        if (filter.getFechaInicio() != null && filter.getFechaFin() != null &&
                filter.getFechaInicio().isAfter(filter.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        List<Medicion> mediciones = medicionRepository.findByFilters(
                filter.getIdNodo(),
                parametroRepository.findByNombre(filter.getParametro()).getIdParametro(),
                filter.getFechaInicio(),
                filter.getFechaFin());

        return mediciones;
    }
}
