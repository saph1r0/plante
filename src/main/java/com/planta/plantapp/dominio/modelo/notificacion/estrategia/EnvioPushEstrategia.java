package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación de estrategia para envío de notificaciones push.
 */
@Component
public class EnvioPushEstrategia implements EstrategiaEnvioNotificacion {

    private static final Logger logger = LoggerFactory.getLogger(EnvioPushEstrategia.class);

    @Override
    public boolean enviar(Notificacion notificacion) {
        try {
            // Lógica de envío de notificación push
            logger.info("Enviando notificación push: {} a usuario {}",
                       notificacion.getTitulo(), notificacion.getUsuarioId());

            // TODO: Implementar integración con servicio push (Firebase, OneSignal, etc.)

            return true;
        } catch (Exception e) {
            logger.error("Error al enviar notificación push: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getNombre() {
        return "Push";
    }

    @Override
    public boolean estaDisponible() {
        // Verificar si el servicio push está configurado
        return true; // Por ahora siempre disponible
    }
}

