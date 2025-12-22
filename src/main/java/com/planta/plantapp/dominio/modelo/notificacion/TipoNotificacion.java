package com.planta.plantapp.dominio.modelo.notificacion;

/**
 * Enum que representa los diferentes tipos de notificaciones del sistema.
 */
public enum TipoNotificacion {
    RECORDATORIO_CUIDADO("Recordatorio de Cuidado"),
    ALERTA_URGENTE("Alerta Urgente"),
    BITACORA_NUEVA("Nueva Entrada en Bitácora"),
    PLANTA_REGISTRADA("Planta Registrada"),
    ESTADO_PLANTA_CAMBIADO("Estado de Planta Modificado"),
    INFORMATIVA("Información General");

    private final String descripcion;

    TipoNotificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}


