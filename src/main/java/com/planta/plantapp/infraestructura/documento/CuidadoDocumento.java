package com.planta.plantapp.infraestructura.documento;

import java.time.LocalDateTime;

public class CuidadoDocumento {

    private String tipo;
    private String descripcion;
    private int frecuenciaDias;
    private LocalDateTime fechaAplicacion;
    private LocalDateTime fechaProxima;

    public CuidadoDocumento() {}

    public CuidadoDocumento(
            String tipo,
            String descripcion,
            int frecuenciaDias,
            LocalDateTime fechaAplicacion,
            LocalDateTime fechaProxima
    ) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.frecuenciaDias = frecuenciaDias;
        this.fechaAplicacion = fechaAplicacion;
        this.fechaProxima = fechaProxima;
    }

    // getters & setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getFrecuenciaDias() { return frecuenciaDias; }
    public void setFrecuenciaDias(int frecuenciaDias) { this.frecuenciaDias = frecuenciaDias; }

    public LocalDateTime getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDateTime getFechaProxima() { return fechaProxima; }
    public void setFechaProxima(LocalDateTime fechaProxima) {
        this.fechaProxima = fechaProxima;
    }
}
