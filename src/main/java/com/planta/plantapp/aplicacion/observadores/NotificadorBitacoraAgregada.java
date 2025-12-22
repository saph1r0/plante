package com.planta.plantapp.aplicacion.observadores;

import com.planta.plantapp.dominio.modelo.eventos.BitacoraAgregadaEvento;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.observer.ObservadorEvento;
import com.planta.plantapp.aplicacion.servicios.ServicioNotificacionImpl;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EnvioInAppEstrategia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Observador que escucha eventos de bitácora y crea notificaciones.
 * Aplica el patrón Observer.
 */
@Component
public class NotificadorBitacoraAgregada implements ObservadorEvento<BitacoraAgregadaEvento> {

    private static final Logger logger = LoggerFactory.getLogger(NotificadorBitacoraAgregada.class);

    private final ServicioNotificacionImpl servicioNotificacion;
    private final EnvioInAppEstrategia estrategiaInApp;

    @Autowired
    public NotificadorBitacoraAgregada(ServicioNotificacionImpl servicioNotificacion,
                                       EnvioInAppEstrategia estrategiaInApp) {
        this.servicioNotificacion = servicioNotificacion;
        this.estrategiaInApp = estrategiaInApp;
    }

    @Override
    public void actualizar(BitacoraAgregadaEvento evento) {
        logger.info("Procesando evento: {}", evento);

        String titulo = "Nueva Entrada en Bitácora";
        String mensaje = String.format("Se agregó una nueva entrada: %s",
                evento.getBitacora().getDescripcion());

        // Crear notificación in-app
        servicioNotificacion.crearNotificacion(
                titulo,
                mensaje,
                TipoNotificacion.BITACORA_NUEVA,
                "global", // TODO: Obtener del contexto de usuario
                estrategiaInApp
        );

        logger.info("Notificación creada para nueva bitácora");
    }
}

