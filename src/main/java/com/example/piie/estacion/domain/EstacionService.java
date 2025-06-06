package com.example.piie.estacion.domain;

import com.example.piie.estacion.dto.EstacionDTO;
import com.example.piie.estacion.dto.EstacionRequestDTO;
import com.example.piie.estacion.dto.EstacionResponseDTO;
import com.example.piie.estacion.dto.EstacionUpdateDTO;
import com.example.piie.estacion.infrastructure.EstacionRepository;
import com.example.piie.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstacionService {
    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<EstacionDTO> findAll() {
        return estacionRepository.findAll()
                .stream().map(e -> modelMapper.map(e, EstacionDTO.class)).collect(Collectors.toList());
    }

    public EstacionResponseDTO findById(Long id) {
        Estacion estacion =  estacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estación no encontrada con ID: " + id));
        return modelMapper.map(estacion, EstacionResponseDTO.class);
    }

    public EstacionResponseDTO save(EstacionRequestDTO estacion) {
        Estacion newEstacion = estacionRepository.save(modelMapper.map(estacion, Estacion.class));
        return modelMapper.map(newEstacion, EstacionResponseDTO.class);
    }

    public EstacionResponseDTO update(Long id, EstacionUpdateDTO estacionUpdateDTO) {
        Estacion estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la estación:  "+ id));
        if (estacionUpdateDTO.getNombre() != null) {
            estacion.setNombre(estacionUpdateDTO.getNombre());
        }
        if (estacionUpdateDTO.getLatitud() != null) {
            estacion.setLatitud(estacionUpdateDTO.getLatitud());
        }
        if (estacionUpdateDTO.getLongitud() != null) {
            estacion.setLongitud(estacionUpdateDTO.getLongitud());
        }
        if (estacionUpdateDTO.getTelefono() != null) {
            estacion.setTelefono(estacionUpdateDTO.getTelefono());
        }
        if (estacionUpdateDTO.getDescripcion() != null) {
            estacion.setDescripcion(estacionUpdateDTO.getDescripcion());
        }

        // 3. Guardar los cambios
        Estacion updatedEstacion = estacionRepository.save(estacion);

        // 4. Convertir a DTO para respuesta
        return modelMapper.map(updatedEstacion, EstacionResponseDTO.class);
    }

    public void delete(Long id) {
        if (estacionRepository.existsById(id)) {
            estacionRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("Estación no encontrada por ID: " + id);
    }

    public List<EstacionResponseDTO> findAllByNombre(String nombre) {
        List<Estacion> estaciones = estacionRepository.findAllByNombre(nombre);
        return estaciones.stream().map(estacion -> modelMapper.map(estacion, EstacionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
