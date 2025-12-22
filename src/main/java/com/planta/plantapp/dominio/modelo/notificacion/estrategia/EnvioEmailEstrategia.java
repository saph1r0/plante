package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación de estrategia para envío de notificaciones por email.
 */
@Component
public class EnvioEmailEstrategia implements EstrategiaEnvioNotificacion {

    private static final Logger logger = LoggerFactory.getLogger(EnvioEmailEstrategia.class);

    @Override
    public boolean enviar(Notificacion notificacion) {
        try {
            // Lógica de envío de email
            logger.info("Enviando notificación por email: {} a usuario {}",
                       notificacion.getTitulo(), notificacion.getUsuarioId());

            // TODO: Implementar integración con servicio de email (JavaMail, SendGrid, etc.)

            return true;
        } catch (Exception e) {
            logger.error("Error al enviar email: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getNombre() {
        return "Email";
    }

    @Override
    public boolean estaDisponible() {
        // Verificar si el servicio de email está configurado
        return true; // Por ahora siempre disponible
    }
}

