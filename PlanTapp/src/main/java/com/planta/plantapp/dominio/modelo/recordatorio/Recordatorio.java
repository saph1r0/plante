package com.planta.plantapp.dominio.modelo.recordatorio;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa un recordatorio asociado a una planta.
 * No depende de ningún framework.
 */
public class Recordatorio {

    private Long id;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private EstadoRecordatorio estado;
    private TipoCuidado tipoCuidado;
    private Planta planta;

    public Recordatorio(String mensaje, LocalDateTime fechaEnvio, TipoCuidado tipoCuidado, Planta planta) {
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.tipoCuidado = tipoCuidado;
        this.planta = planta;
        this.estado = EstadoRecordatorio.ACTIVO;
    }

    public void marcarComoCompletado() {
        this.estado = EstadoRecordatorio.COMPLETADO;
    }

    public void posponer(LocalDateTime nuevaFecha) {
        this.fechaEnvio = nuevaFecha;
        this.estado = EstadoRecordatorio.POSPUESTO;
    }

    public void cancelar() {
        this.estado = EstadoRecordatorio.CANCELADO;
    }

    public boolean estaVencido() {
        return fechaEnvio.isBefore(LocalDateTime.now()) && estado.esActivo();
    }

    public boolean requiereAtencion() {
        return estado.requiereAccion();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public EstadoRecordatorio getEstado() {
        return estado;
    }

    public TipoCuidado getTipoCuidado() {
        return tipoCuidado;
    }

    public Planta getPlanta() {
        return planta;
    }
    public void setEstado(EstadoRecordatorio estado) {
        this.estado = estado;
    }
    


    public void actualizarMensaje(String nuevoMensaje) {
        this.mensaje = nuevoMensaje;
    }

    public void actualizarFechaEnvio(LocalDateTime nuevaFecha) {
        this.fechaEnvio = nuevaFecha;
    }
}
