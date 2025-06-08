package com.example.piie.medicion.domain;

import com.example.piie.exception.AuthenticationException;
import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.medicion.dto.MedicionDto;
import com.example.piie.medicion.dto.MedicionFilterDTO;
import com.example.piie.medicion.infrastructure.MedicionRepository;
import com.example.piie.nodo.infrastructure.NodoRepository;
import com.example.piie.medicion.dto.MedicionCreateDTO;
import com.example.piie.nodo.domain.Nodo;
import com.example.piie.parametro.domain.Parametro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
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

    @PersistenceContext
    private EntityManager entityManager;

    public MedicionDto createMedicion(@Valid MedicionCreateDTO medicionCreateDTO) {
        if (medicionCreateDTO.getToken() == null || medicionCreateDTO.getToken().isEmpty()) {
            throw new AuthenticationException();
        }

        Nodo nodo = nodoRepository.findById(medicionCreateDTO.getIdNodo())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un nodo con ID: "+medicionCreateDTO.getIdNodo()));

        if (!nodo.getToken().equals(medicionCreateDTO.getToken())) {
            throw new AuthenticationException();
        }

        Parametro parametro = parametroRepository.findById(medicionCreateDTO.getIdParametro())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un parametro con ID: "+medicionCreateDTO.getIdParametro()));

        Medicion medicion = new Medicion();
        medicion.setNodo(nodo);
        medicion.setParametro(parametro);
        medicion.setFecha(medicionCreateDTO.getFecha());
        medicion.setValor(medicionCreateDTO.getValor());

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

        // Usar Criteria API directamente aquí
        List<Medicion> mediciones = findMedicionesWithCriteria(filter);

        return mediciones.stream()
                .map(n -> modelMapper.map(n, MedicionDto.class))
                .collect(Collectors.toList());
    }

    private List<Medicion> findMedicionesWithCriteria(MedicionFilterDTO filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medicion> query = cb.createQuery(Medicion.class);
        Root<Medicion> root = query.from(Medicion.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filtro por nodo
        if (filter.getIdNodo() != null) {
            predicates.add(cb.equal(root.get("nodo").get("idNodo"), filter.getIdNodo()));
        }

        // Filtro por parámetro
        if (filter.getIdParametro() != null) {
            predicates.add(cb.equal(root.get("parametro").get("idParametro"), filter.getIdParametro()));
        }

        // Filtro por estación (a través del nodo)
        if (filter.getIdEstacion() != null) {
            predicates.add(cb.equal(root.get("nodo").get("estacion").get("idEstacion"), filter.getIdEstacion()));
        }

        // Filtro por fecha de inicio
        if (filter.getFechaInicio() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), filter.getFechaInicio()));
        }

        // Filtro por fecha fin
        if (filter.getFechaFin() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), filter.getFechaFin()));
        }

        // Aplicar predicados si existen
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(query).getResultList();
    }
}
