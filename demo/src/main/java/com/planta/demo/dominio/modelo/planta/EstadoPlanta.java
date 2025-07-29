package com.planta.demo.dominio.modelo.planta;

/**
 * Value Object que representa el estado actual de una planta.
 * 
 */
public enum EstadoPlanta {
    
    /**
     * Estado óptimo: La planta está en perfectas condiciones.
     * Indica que no requiere atención inmediata.
     */
    SALUDABLE("Planta en perfecto estado"),
    
    /**
     * Estado de atención: La planta necesita ser regada.
     * Requiere acción en las próximas 24-48 horas.
     */
    NECESITA_AGUA("Requiere riego inmediato"),
    
    /**
     * Estado crítico: La planta presenta signos de enfermedad.
     * Requiere diagnóstico y tratamiento especializado.
     */
    ENFERMA("Presenta signos de enfermedad"),
    
    /**
     * Estado crítico: La planta está marchitándose.
     * Requiere atención inmediata para su supervivencia.
     */
    MARCHITA("Estado crítico, necesita atención urgente"),
    
    /**
     * Estado positivo: La planta está en proceso de floración.
     * Indica salud óptima y condiciones favorables.
     */
    FLORECIENDO("En proceso de floración");

    private final String descripcion;

    /**
     * Constructor que garantiza inmutabilidad del Value Object.
     * 
     * @param descripcion descripción legible para usuarios
     */
    EstadoPlanta(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripción del estado orientada al usuario.
     * 
     * @return descripción legible del estado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Determina si el estado representa una condición crítica.
     * 
     * Regla de negocio: Estados críticos requieren atención inmediata
     * para evitar pérdida de la planta.
     * 
     * @return true si el estado es crítico, false en caso contrario
     */
    public boolean esEstadoCritico() {
        return this == ENFERMA || this == MARCHITA;
    }
    
    /**
     * Determina si el estado requiere algún tipo de acción.
     * 
     * Regla de negocio: Estados que requieren intervención del usuario.
     * 
     * @return true si requiere acción, false si está bien
     */
    public boolean requiereAccion() {
        return this != SALUDABLE && this != FLORECIENDO;
    }
    
    /**
     * Determina si el estado indica buena salud de la planta.
     * 
     * Regla de negocio: Estados que indican condiciones favorables.
     * 
     * @return true si el estado es positivo, false en caso contrario
     */
    public boolean esEstadoPositivo() {
        return this == SALUDABLE || this == FLORECIENDO;
    }
    
    /**
     * Determina si el estado requiere cuidado inmediato.
     * 
     * Método de compatibilidad con el código existente.
     * 
     * @return true si requiere cuidado inmediato, false en caso contrario
     */
    public boolean requiereCuidadoInmediato() {
        return requiereAccion();
    }
    
    /**
     * Representación completa del estado para logging y debugging.
     * 
     * @return string detallado del estado
     */
    @Override
    public String toString() {
        return name() + " - " + descripcion;
    }
}
