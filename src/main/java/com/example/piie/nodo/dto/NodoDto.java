package com.example.piie.nodo.dto;

import com.example.piie.parametro.dto.ParametroDto;

import java.time.LocalDateTime;
import java.util.List;

public class NodoDto {
    private Long idNodo;
    private String estacionNombre;
    private String estado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaInstalacion;
    private String descripcion;
    private List<ParametroDto> parametros;

    public NodoDto() {}

    public NodoDto(Long idNodo,
                   String estacionNombre,
                   String estado,
                   LocalDateTime fechaRegistro,
                   LocalDateTime fechaInstalacion,
                   String descripcion,
                   List<ParametroDto> parametros) {
        this.idNodo = idNodo;
        this.estacionNombre = estacionNombre;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.fechaInstalacion = fechaInstalacion;
        this.descripcion = descripcion;
        this.parametros = parametros;
    }

    public Long getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(Long idNodo) {
        this.idNodo = idNodo;
    }

    public String getEstacionNombre() {
        return estacionNombre;
    }

    public void setEstacionNombre(String estacionNombre) {
        this.estacionNombre = estacionNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(LocalDateTime fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ParametroDto> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroDto> parametros) {
        this.parametros = parametros;
    }
}