package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación de estrategia para notificaciones in-app (dentro de la aplicación).
 */
@Component
public class EnvioInAppEstrategia implements EstrategiaEnvioNotificacion {

    private static final Logger logger = LoggerFactory.getLogger(EnvioInAppEstrategia.class);

    @Override
    public boolean enviar(Notificacion notificacion) {
        try {
            // Lógica de envío de notificación in-app
            logger.info("Guardando notificación in-app: {} para usuario {}",
                       notificacion.getTitulo(), notificacion.getUsuarioId());

            // Las notificaciones in-app simplemente se guardan en la base de datos
            // y se muestran cuando el usuario abre la aplicación

            return true;
        } catch (Exception e) {
            logger.error("Error al guardar notificación in-app: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getNombre() {
        return "In-App";
    }

    @Override
    public boolean estaDisponible() {
        return true; // Siempre disponible
    }
}

