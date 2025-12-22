package com.planta.plantapp.aplicacion.observadores;

import com.planta.plantapp.dominio.modelo.eventos.CuidadoAgregadoEvento;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.observer.ObservadorEvento;
import com.planta.plantapp.aplicacion.servicios.ServicioNotificacionImpl;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EnvioInAppEstrategia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Observador que escucha eventos de cuidados agregados y crea notificaciones.
 * Aplica el patrón Observer.
 */
@Component
public class NotificadorCuidadoAgregado implements ObservadorEvento<CuidadoAgregadoEvento> {

    private static final Logger logger = LoggerFactory.getLogger(NotificadorCuidadoAgregado.class);

    private final ServicioNotificacionImpl servicioNotificacion;
    private final EnvioInAppEstrategia estrategiaInApp;

    @Autowired
    public NotificadorCuidadoAgregado(ServicioNotificacionImpl servicioNotificacion,
                                     EnvioInAppEstrategia estrategiaInApp) {
        this.servicioNotificacion = servicioNotificacion;
        this.estrategiaInApp = estrategiaInApp;
    }

    @Override
    public void actualizar(CuidadoAgregadoEvento evento) {
        logger.info("Procesando evento: {}", evento);

        String titulo = "Nuevo Cuidado Agregado";
        String mensaje = String.format("Se agregó el cuidado '%s' a la planta '%s'",
                evento.getCuidado().getDescripcion(),
                evento.getPlanta().getNombreComun());

        // Crear notificación in-app
        servicioNotificacion.crearNotificacion(
                titulo,
                mensaje,
                TipoNotificacion.INFORMATIVA,
                "global", // TODO: Obtener del contexto de usuario
                estrategiaInApp
        );

        logger.info("Notificación creada para cuidado agregado");
    }
}

