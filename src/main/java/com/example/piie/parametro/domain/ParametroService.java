package com.example.piie.parametro.domain;

import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.parametro.infrastructure.ParametroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParametroService {
    @Autowired
    private ParametroRepository parametroRepository;

    public void createParametro(Parametro parametro) {
        parametroRepository.save(parametro);
    }

    public List<Parametro> findAll() {
        return parametroRepository.findAll();
    }

    public Parametro findById(Long id) {
        return parametroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el parámetro: "+ id));
    }

    public Parametro update(Long id, Parametro parametro) {
        Parametro updated = parametroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el parámetro "+ id));
        if (parametro.getUnidad() != null) {
            updated.setUnidad(parametro.getUnidad());
        }
        if (parametro.getNombre() != null) {
            updated.setNombre(parametro.getNombre());
        }
        if (parametro.getDescripcion() != null) {
            updated.setDescripcion(parametro.getDescripcion());
        }
        return parametroRepository.save(updated);
    }

    public void delete(Long id) {
        if (parametroRepository.findById(id).isPresent()) {
            parametroRepository.deleteById(id);
        }else {
            throw new RuntimeException(new ResourceNotFoundException("No se encontró el parámetro" + id));
        }
    }
}
