package com.example.piie.nodo.domain;

import com.example.piie.estacion.domain.Estacion;
import com.example.piie.estacion.infrastructure.EstacionRepository;
import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.nodo.dto.NodoCreateDTO;
import com.example.piie.nodo.dto.NodoResponseDTO;
import com.example.piie.nodo.dto.NodoUpdateDTO;
import com.example.piie.nodo.infrastructure.NodoRepository;
import com.example.piie.parametro.domain.Parametro;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NodoService {

    private final NodoRepository nodoRepository;
    private final EstacionRepository estacionRepository;
    private final ParametroRepository parametroRepository;
    private final ModelMapper modelMapper;

    public NodoResponseDTO getNodoById(Long id) {
        Nodo nodo = nodoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el nodo " + id));
        return modelMapper.map(nodo, NodoResponseDTO.class);
    }

    public List<NodoResponseDTO> getAllNodos() {
        return nodoRepository.findAll().stream()
                .map(r -> modelMapper.map(r, NodoResponseDTO.class)).collect(Collectors.toList());
    }

    public NodoResponseDTO createNodo(@Valid NodoCreateDTO nodoCreateDTO) {
        // Validar y obtener estación
        Estacion estacion = estacionRepository.findById(nodoCreateDTO.getIdEstacion())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la estación "+ nodoCreateDTO.getIdEstacion()));

        // Validar y obtener parámetros
        List<Parametro> parametros = parametroRepository.findByNombreIn(nodoCreateDTO.getParametros());

        // Mapear DTO a entidad
        Nodo nodo = modelMapper.map(nodoCreateDTO, Nodo.class);
        nodo.setEstacion(estacion);
        nodo.setParametros(parametros);
        nodo.setFechaRegistro(LocalDateTime.now());

        // Guardar en BD
        Nodo savedNodo = nodoRepository.save(nodo);

        return modelMapper.map(savedNodo, NodoResponseDTO.class);
    }

    public List<NodoResponseDTO> getNodosByEstacion(Long idEstacion) {
        Estacion estacion = estacionRepository.findById(idEstacion)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la estación con ID: " + idEstacion));
        List<Nodo> nodos = nodoRepository.findByEstacion(estacion);
        return nodos.stream().map(r -> modelMapper.map(r, NodoResponseDTO.class)).collect(Collectors.toList());
    }

    public List<NodoResponseDTO> getNodosByParametro(Long idParametro) {
        Parametro parametro = parametroRepository.findById(idParametro)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el parámetro con ID: " + idParametro));

        List<Nodo> nodos = nodoRepository.findByParametrosContaining(parametro);
        return nodos.stream()
                .map(n -> modelMapper.map(n, NodoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public NodoResponseDTO updateNodo(Long id, @Valid NodoUpdateDTO nodoUpdateDTO) {
        Nodo nodo = nodoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nodo no encontrado con id: " + id));

        // Actualizar campos básicos
        if (nodoUpdateDTO.getEstado() != null) {
            nodo.setEstado(nodoUpdateDTO.getEstado());
        }
        if (nodoUpdateDTO.getFechaInstalacion() != null) {
            nodo.setFechaInstalacion(nodoUpdateDTO.getFechaInstalacion());
        }
        if (nodoUpdateDTO.getDescripcion() != null) {
            nodo.setDescripcion(nodoUpdateDTO.getDescripcion());
        }

        // Actualizar parámetros si vienen en el DTO
        if (nodoUpdateDTO.getParametros() != null && !nodoUpdateDTO.getParametros().isEmpty()) {
            List<Parametro> parametros = parametroRepository.findByNombreIn(nodoUpdateDTO.getParametros());
            if (parametros.size() != nodoUpdateDTO.getParametros().size()) {
                throw new ResourceNotFoundException("Algunos parámetros no existen");
            }
            nodo.setParametros(parametros);
        }

        Nodo updatedNodo = nodoRepository.save(nodo);
        return modelMapper.map(updatedNodo, NodoResponseDTO.class);
    }

    public void deleteNodo(Long id) {
        if (!nodoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nodo no encontrado con id: " + id);
        }
        nodoRepository.deleteById(id);
    }

}