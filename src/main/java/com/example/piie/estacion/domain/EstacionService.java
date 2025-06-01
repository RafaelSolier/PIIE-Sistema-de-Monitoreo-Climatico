package com.example.piie.estacion.domain;

import com.example.piie.estacion.dto.EstacionDTO;
import com.example.piie.estacion.dto.EstacionResponseDTO;
import com.example.piie.estacion.dto.EstacionUpdateDTO;
import com.example.piie.estacion.infrastructure.EstacionRepository;
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

    public Estacion findById(Long id) {
        return estacionRepository.findById(id).get();
    }

    public Estacion save(Estacion estacion) {
        return estacionRepository.save(estacion);
    }

    public EstacionDTO update(Long id, EstacionUpdateDTO estacionUpdateDTO) {
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
        return modelMapper.map(updatedEstacion, EstacionDTO.class);
    }

    public void delete(Estacion estacion) {
        estacionRepository.delete(estacion);
    }

    public List<EstacionResponseDTO> findAllByNombre(String nombre) {
        List<Estacion> estaciones = estacionRepository.findAllByNombre(nombre);
        return estaciones.stream().map(estacion -> modelMapper.map(estacion, EstacionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
