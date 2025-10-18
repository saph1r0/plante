package com.planta.plantapp.dominio.modelo.planta.dto;


/**
 * DTO para cuidados de plantas
 */
public class CuidadoDTO {
    private String tipo;
    private String descripcion;
    private int frecuencia;

    // Constructores
    public CuidadoDTO() {}

    public CuidadoDTO(String tipo, String descripcion, int frecuencia) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getFrecuenciaDias() { return frecuencia; }
    public void setFrecuencia(int frecuencia) { this.frecuencia = frecuencia; }
}