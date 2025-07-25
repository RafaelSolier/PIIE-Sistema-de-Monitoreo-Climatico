package com.example.piie.nodo.domain;

import com.example.piie.estacion.domain.Estacion;
import com.example.piie.estacion.infrastructure.EstacionRepository;
import com.example.piie.exception.DuplicateTokenException;
import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.nodo.dto.NodoCreateDTO;
import com.example.piie.nodo.dto.NodoResponseDTO;
import com.example.piie.nodo.dto.NodoUpdateDTO;
import com.example.piie.nodo.infrastructure.NodoRepository;
import com.example.piie.parametro.domain.Parametro;
import com.example.piie.parametro.domain.ParametroEnum;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
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
        Nodo newNodo = new Nodo();
        newNodo.setEstacion(estacion);
        newNodo.setParametros(parametros);
        newNodo.setEstado(nodoCreateDTO.getEstado());
//        newNodo.setToken(nodoCreateDTO.getToken());

        newNodo.setFechaRegistro(LocalDateTime.now());
        newNodo.setDescripcion(nodoCreateDTO.getDescripcion());

        if (nodoCreateDTO.getFechaInstalacion() != null) {
            newNodo.setFechaInstalacion(nodoCreateDTO.getFechaInstalacion());
        }
        // Guardar en BD
        Nodo savedNodo = nodoRepository.save(newNodo);
        //System.out.println("Nodo created" + savedNodo);

        NodoResponseDTO n =  modelMapper.map(savedNodo, NodoResponseDTO.class);
        //System.out.println("Nodo created" + n);

        return n;
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
        try {
            Nodo nodo = nodoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Nodo no encontrado con id: " + id));

            // Verificar si el token ya existe en otro nodo (opcional)
            if (nodoUpdateDTO.getToken() != null && !nodoUpdateDTO.getToken().equals(nodo.getToken())) {
                if (nodoRepository.existsByTokenAndIdNodoNot(nodoUpdateDTO.getToken(), id)) {
                    throw new DuplicateTokenException("El token ya está siendo usado por otro nodo");
                }
            }

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

            if (nodoUpdateDTO.getToken() != null) {
                nodo.setToken(nodoUpdateDTO.getToken());
            }

            Nodo updatedNodo = nodoRepository.save(nodo);
            return modelMapper.map(updatedNodo, NodoResponseDTO.class);
        }catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("token") && ex.getMessage().contains("already exists")) {
                throw new DuplicateTokenException("El token ya existe. Genere un nuevo token único.");
            }
            throw ex; // Re-lanzar si es otra violación
        }

    }

    public void deleteNodo(Long id) {
        if (!nodoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nodo no encontrado con id: " + id);
        }
        Nodo nodo = nodoRepository.findById(id).get();
        // Desasociar parámetros primero
        nodo.getParametros().clear();
        nodoRepository.save(nodo);

        nodoRepository.deleteById(id);
    }

}