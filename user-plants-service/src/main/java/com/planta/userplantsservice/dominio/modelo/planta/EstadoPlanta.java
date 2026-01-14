package com.planta.userplantsservice.dominio.modelo.planta;
/**
 * Enumeración que representa los posibles estados de una planta
 */
public enum EstadoPlanta {
    SALUDABLE("Saludable", "La planta está en buen estado"),
    NECESITA_AGUA("Necesita Agua", "La planta requiere riego"),
    NECESITA_FERTILIZANTE("Necesita Fertilizante", "La planta requiere fertilización"),
    ENFERMA("Enferma", "La planta presenta signos de enfermedad"),
    MARCHITA("Marchita", "La planta está marchita"),
    FLORECIENDO("Floreciendo", "La planta está en período de floración"),
    CRECIENDO("Creciendo", "La planta está en fase de crecimiento activo"),
    DORMIDA("Dormida", "La planta está en período de dormancia");

    private final String nombre;
    private final String descripcion;

    EstadoPlanta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean requiereCuidadoInmediato() {
        return this == NECESITA_AGUA || this == ENFERMA || this == MARCHITA;
    }

    public boolean esEstadoPositivo() {
        return this == SALUDABLE || this == FLORECIENDO || this == CRECIENDO;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
