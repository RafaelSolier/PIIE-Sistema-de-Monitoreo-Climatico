package com.example.piie.parametro.dto;

/**
 * DTO para representar un parámetro (e.g. Temperatura, Humedad).
 */
public class ParametroDto {
    private Long idParametro;
    private String nombre;
    private String unidad;

    public ParametroDto() {}

    public ParametroDto(Long idParametro, String nombre, String unidad) {
        this.idParametro = idParametro;
        this.nombre = nombre;
        this.unidad = unidad;
    }

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
