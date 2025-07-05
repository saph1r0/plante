package com.planta.plantapp.dominio.modelo.recordatorio;

/**
 * Enumeración que representa los estados de un recordatorio
 */
public enum EstadoRecordatorio {
    ACTIVO("Activo", "El recordatorio está activo y pendiente"),
    COMPLETADO("Completado", "El recordatorio fue completado"),
    POSPUESTO("Pospuesto", "El recordatorio fue pospuesto"),
    CANCELADO("Cancelado", "El recordatorio fue cancelado"),
    VENCIDO("Vencido", "El recordatorio venció sin ser completado");

    private final String nombre;
    private final String descripcion;

    EstadoRecordatorio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean esActivo() {
        return this == ACTIVO;
    }

    public boolean esCompletado() {
        return this == COMPLETADO;
    }

    public boolean requiereAccion() {
        return this == ACTIVO || this == POSPUESTO || this == VENCIDO;
    }

    public boolean puedeSerModificado() {
        return this == ACTIVO || this == POSPUESTO;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
