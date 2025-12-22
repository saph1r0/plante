package com.planta.plantapp.dominio.modelo.notificacion;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa una notificación.
 * Aplica el patrón Strategy para diferentes tipos de notificaciones.
 */
public class Notificacion {

    private String id;
    private String titulo;
    private String mensaje;
    private TipoNotificacion tipo;
    private EstadoNotificacion estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
    private String usuarioId;
    private String referenciaId; // ID de la entidad relacionada (planta, recordatorio, etc.)
    private TipoReferencia tipoReferencia;

    public Notificacion(String titulo, String mensaje, TipoNotificacion tipo, String usuarioId) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.estado = EstadoNotificacion.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Notificacion() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoNotificacion.PENDIENTE;
    }

    public void marcarComoEnviada() {
        this.estado = EstadoNotificacion.ENVIADA;
        this.fechaEnvio = LocalDateTime.now();
    }

    public void marcarComoLeida() {
        this.estado = EstadoNotificacion.LEIDA;
    }

    public void marcarComoFallida() {
        this.estado = EstadoNotificacion.FALLIDA;
    }

    public boolean estaPendiente() {
        return this.estado == EstadoNotificacion.PENDIENTE;
    }

    public boolean estaEnviada() {
        return this.estado == EstadoNotificacion.ENVIADA;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(String referenciaId) {
        this.referenciaId = referenciaId;
    }

    public TipoReferencia getTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(TipoReferencia tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }
}

