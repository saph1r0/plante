package com.planta.plantapp.dominio.modelo.cuidado;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de dominio que representa un cuidado aplicado a una planta.
 */
public class Cuidado {

    private TipoCuidado tipo;

    private String descripcion;
    private Integer frecuenciaDias;
    private LocalDateTime fechaAplicacion;
    private LocalDateTime fechaProxima;
    private String notas;

    public Cuidado(TipoCuidado tipo, String descripcion, Integer frecuenciaDias) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.frecuenciaDias = frecuenciaDias;
        this.fechaAplicacion = LocalDateTime.now();
        programarProximo();
    }

    // Para que MongoDB pueda deserializar
    public Cuidado() {
        // Constructor vacío requerido por MongoDB
    }

    public Cuidado(TipoCuidado tipo, String descripcion, int frecuenciaDias) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.frecuenciaDias = frecuenciaDias;
    }

    public void programarProximo() {
        if (frecuenciaDias != null && frecuenciaDias > 0) {
            this.fechaProxima = this.fechaAplicacion.plusDays(frecuenciaDias);
        }
    }

    public boolean esPendiente() {
        return fechaProxima != null && fechaProxima.isAfter(LocalDateTime.now());
    }

    // Getters y setters

    public TipoCuidado getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuidado tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getFrecuenciaDias() {
        return frecuenciaDias;
    }

    public void setFrecuenciaDias(Integer frecuenciaDias) {
        this.frecuenciaDias = frecuenciaDias;
    }

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDateTime getFechaProxima() {
        return fechaProxima;
    }

    public void setFechaProxima(LocalDateTime fechaProxima) {
        this.fechaProxima = fechaProxima;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    // Métodos de utilidad

    @Override
    public String toString() {
        return "Cuidado{" +
                "tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' +
                ", frecuenciaDias=" + frecuenciaDias +
                ", fechaAplicacion=" + fechaAplicacion +
                ", fechaProxima=" + fechaProxima +
                ", notas='" + notas + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuidado)) return false;
        Cuidado cuidado = (Cuidado) o;
        return tipo == cuidado.tipo &&
                Objects.equals(descripcion, cuidado.descripcion) &&
                Objects.equals(fechaAplicacion, cuidado.fechaAplicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, descripcion, fechaAplicacion);
    }
}