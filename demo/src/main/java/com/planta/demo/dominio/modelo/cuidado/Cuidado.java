package com.planta.demo.dominio.modelo.cuidado;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "cuidados")
public class Cuidado {

    @Id
    private String id;

    private TipoCuidado tipo;

    private String descripcion;

    private Integer frecuenciaDias;

    private LocalDateTime fechaAplicacion;

    private LocalDateTime fechaProxima;

    private String notas;

    public Cuidado() {
        this.fechaAplicacion = LocalDateTime.now();
        this.fechaProxima = null;
    }

    public Cuidado(TipoCuidado tipo, String descripcion, Integer frecuenciaDias) {
        this();
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.frecuenciaDias = frecuenciaDias;
        this.programarProximo();
    }

    public void programarProximo() {
        if (frecuenciaDias != null) {
            this.fechaProxima = this.fechaAplicacion.plusDays(frecuenciaDias);
        }
    }

    public boolean esPendiente() {
        return fechaProxima != null && fechaProxima.isAfter(LocalDateTime.now());
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

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
}
