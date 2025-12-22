package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;

/**
 * Patrón Strategy: Define la interfaz para diferentes estrategias de envío de notificaciones.
 */
public interface EstrategiaEnvioNotificacion {

    /**
     * Envía una notificación usando la estrategia específica.
     * @param notificacion La notificación a enviar
     * @return true si el envío fue exitoso, false en caso contrario
     */
    boolean enviar(Notificacion notificacion);

    /**
     * Obtiene el nombre de la estrategia.
     * @return Nombre descriptivo de la estrategia
     */
    String getNombre();

    /**
     * Verifica si la estrategia está disponible para usar.
     * @return true si la estrategia puede ser utilizada
     */
    boolean estaDisponible();
}

