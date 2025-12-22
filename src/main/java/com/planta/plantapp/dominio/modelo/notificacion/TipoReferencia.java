package com.planta.plantapp.dominio.modelo.notificacion;

/**
 * Enum que representa el tipo de entidad relacionada con una notificación.
 */
public enum TipoReferencia {
    PLANTA("Planta"),
    RECORDATORIO("Recordatorio"),
    BITACORA("Bitácora"),
    CUIDADO("Cuidado"),
    USUARIO("Usuario");

    private final String descripcion;

    TipoReferencia(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

