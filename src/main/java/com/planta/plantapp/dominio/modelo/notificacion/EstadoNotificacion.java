package com.planta.plantapp.dominio.modelo.notificacion;

/**
 * Enum que representa el estado de una notificación.
 */
public enum EstadoNotificacion {
    PENDIENTE("Pendiente de Envío"),
    ENVIADA("Enviada"),
    LEIDA("Leída por el Usuario"),
    FALLIDA("Fallo en el Envío");

    private final String descripcion;

    EstadoNotificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean requiereAccion() {
        return this == PENDIENTE || this == FALLIDA;
    }
}

