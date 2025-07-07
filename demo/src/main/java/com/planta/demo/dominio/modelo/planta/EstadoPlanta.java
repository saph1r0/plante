package com.planta.demo.dominio.modelo.planta;
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

    /** Nombre legible del estado */
    private final String nombre;

    /** Descripción detallada del estado */
    private final String descripcion;

    /**
     * Constructor para crear un estado de planta.
     *
     * @param nombre el nombre legible del estado
     * @param descripcion la descripción detallada del estado
     */
    EstadoPlanta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nombre legible del estado.
     *
     * @return el nombre del estado
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la descripción detallada del estado.
     *
     * @return la descripción del estado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Determina si el estado requiere cuidado inmediato.
     *
     * @return true si requiere atención urgente, false en caso contrario
     */
    public boolean requiereCuidadoInmediato() {
        return this == NECESITA_AGUA || this == ENFERMA || this == MARCHITA;
    }

    /**
     * Determina si el estado es positivo para la planta.
     *
     * @return true si el estado indica buena salud, false en caso contrario
     */
    public boolean esEstadoPositivo() {
        return this == SALUDABLE || this == FLORECIENDO || this == CRECIENDO;
    }

    /**
     * Determina si el estado requiere fertilización.
     *
     * @return true si necesita fertilizante, false en caso contrario
     */
    public boolean requiereFertilizacion() {
        return this == NECESITA_FERTILIZANTE;
    }

    /**
     * Obtiene la prioridad del estado para ordenamiento.
     * Estados que requieren cuidado inmediato tienen mayor prioridad.
     *
     * @return nivel de prioridad (1 = más urgente, 3 = menos urgente)
     */
    public int getPrioridad() {
        if (requiereCuidadoInmediato()) {
            return 1; // Alta prioridad
        } else if (requiereFertilizacion()) {
            return 2; // Media prioridad
        } else {
            return 3; // Baja prioridad
        }
    }

    /**
     * Representación en cadena del estado.
     *
     * @return el nombre legible del estado
     */
    @Override
    public String toString() {
        return nombre;
    }
}