package com.planta.plantapp.dominio.modelo.servicios;

import com.planta.plantapp.dominio.modelo.INotificacionRepositorio;
import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EstrategiaEnvioNotificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de dominio para gestión de notificaciones.
 * Aplica el patrón Strategy para seleccionar la estrategia de envío adecuada.
 */
public class ServicioNotificacionDominio {

    private static final Logger logger = LoggerFactory.getLogger(ServicioNotificacionDominio.class);

    private final INotificacionRepositorio repositorio;
    private final List<EstrategiaEnvioNotificacion> estrategias;

    public ServicioNotificacionDominio(INotificacionRepositorio repositorio,
                                       List<EstrategiaEnvioNotificacion> estrategias) {
        this.repositorio = repositorio;
        this.estrategias = estrategias != null ? estrategias : new ArrayList<>();
    }

    /**
     * Crea y envía una notificación usando la estrategia apropiada.
     */
    public Notificacion crearYEnviar(String titulo, String mensaje, TipoNotificacion tipo,
                                     String usuarioId, EstrategiaEnvioNotificacion estrategia) {

        Notificacion notificacion = new Notificacion(titulo, mensaje, tipo, usuarioId);

        // Guardar primero la notificación
        repositorio.guardar(notificacion);

        // Intentar enviar usando la estrategia
        if (estrategia != null && estrategia.estaDisponible()) {
            boolean exito = estrategia.enviar(notificacion);

            if (exito) {
                notificacion.marcarComoEnviada();
            } else {
                notificacion.marcarComoFallida();
            }

            repositorio.guardar(notificacion);
        }

        return notificacion;
    }

    /**
     * Envía notificaciones pendientes usando todas las estrategias disponibles.
     */
    public void procesarNotificacionesPendientes() {
        List<Notificacion> pendientes = repositorio.listarPendientes();

        logger.info("Procesando {} notificaciones pendientes", pendientes.size());

        for (Notificacion notificacion : pendientes) {
            for (EstrategiaEnvioNotificacion estrategia : estrategias) {
                if (estrategia.estaDisponible()) {
                    boolean exito = estrategia.enviar(notificacion);

                    if (exito) {
                        notificacion.marcarComoEnviada();
                        repositorio.guardar(notificacion);
                        break; // Salir después del primer envío exitoso
                    }
                }
            }
        }
    }

    /**
     * Marca una notificación como leída.
     */
    public void marcarComoLeida(String notificacionId) {
        Notificacion notificacion = repositorio.obtenerPorId(notificacionId);

        if (notificacion != null) {
            notificacion.marcarComoLeida();
            repositorio.guardar(notificacion);
        }
    }

    /**
     * Obtiene todas las notificaciones no leídas de un usuario.
     */
    public List<Notificacion> obtenerNoLeidas(String usuarioId) {
        return repositorio.listarPorUsuarioYEstado(usuarioId,
            com.planta.plantapp.dominio.modelo.notificacion.EstadoNotificacion.ENVIADA);
    }
}

