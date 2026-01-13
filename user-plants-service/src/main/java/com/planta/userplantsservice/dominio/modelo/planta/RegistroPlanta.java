package com.planta.userplantsservice.dominio.modelo.planta;

import java.util.Date;
import java.util.Objects;

public class RegistroPlanta {

    private String id;
    private String apodo;
    private Date fechaRegistro;
    private EstadoPlanta estado;

    private String usuarioId;

    public RegistroPlanta(String apodo, String usuarioId) {
        this.apodo = apodo;
        this.usuarioId = usuarioId;
        this.fechaRegistro = new Date();
        this.estado = EstadoPlanta.SALUDABLE;
    }

    // Métodos de dominio simplificados
    public void cambiarEstado(EstadoPlanta nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public EstadoPlanta getEstado() { return estado; }
    public void setEstado(EstadoPlanta estado) { this.estado = estado; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroPlanta)) return false;
        RegistroPlanta that = (RegistroPlanta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}