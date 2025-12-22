package com.planta.plantapp.aplicacion.observadores;

import com.planta.plantapp.dominio.modelo.eventos.RecordatorioCreadoEvento;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.observer.ObservadorEvento;
import com.planta.plantapp.aplicacion.servicios.ServicioNotificacionImpl;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EnvioPushEstrategia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Observador que escucha eventos de recordatorios creados y genera notificaciones.
 * Aplica el patrón Observer.
 */
@Component
public class NotificadorRecordatorioCreado implements ObservadorEvento<RecordatorioCreadoEvento> {

    private static final Logger logger = LoggerFactory.getLogger(NotificadorRecordatorioCreado.class);

    private final ServicioNotificacionImpl servicioNotificacion;
    private final EnvioPushEstrategia estrategiaPush;

    @Autowired
    public NotificadorRecordatorioCreado(ServicioNotificacionImpl servicioNotificacion,
                                        EnvioPushEstrategia estrategiaPush) {
        this.servicioNotificacion = servicioNotificacion;
        this.estrategiaPush = estrategiaPush;
    }

    @Override
    public void actualizar(RecordatorioCreadoEvento evento) {
        logger.info("Procesando evento: {}", evento);

        String titulo = "Nuevo Recordatorio";
        String mensaje = evento.getRecordatorio().getMensaje();

        // Crear notificación push
        servicioNotificacion.crearNotificacion(
                titulo,
                mensaje,
                TipoNotificacion.RECORDATORIO_CUIDADO,
                "global", // TODO: Obtener del contexto de usuario
                estrategiaPush
        );

        logger.info("Notificación push creada para recordatorio");
    }
}

