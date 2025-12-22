package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.INotificacionRepositorio;
import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.notificacion.EstadoNotificacion;
import com.planta.plantapp.dominio.modelo.servicios.ServicioNotificacionDominio;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EstrategiaEnvioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de aplicación para gestión de notificaciones.
 * Aplica el patrón Facade para simplificar el acceso a la lógica de notificaciones.
 */
@Service
public class ServicioNotificacionImpl {

    private final ServicioNotificacionDominio servicioDominio;
    private final INotificacionRepositorio repositorio;

    @Autowired
    public ServicioNotificacionImpl(ServicioNotificacionDominio servicioDominio,
                                   INotificacionRepositorio repositorio) {
        this.servicioDominio = servicioDominio;
        this.repositorio = repositorio;
    }

    /**
     * Crea y envía una notificación.
     */
    public Notificacion crearNotificacion(String titulo, String mensaje, TipoNotificacion tipo,
                                         String usuarioId, EstrategiaEnvioNotificacion estrategia) {
        return servicioDominio.crearYEnviar(titulo, mensaje, tipo, usuarioId, estrategia);
    }

    /**
     * Obtiene todas las notificaciones de un usuario.
     */
    public List<Notificacion> obtenerNotificacionesUsuario(String usuarioId) {
        return repositorio.listarPorUsuario(usuarioId);
    }

    /**
     * Obtiene notificaciones no leídas de un usuario.
     */
    public List<Notificacion> obtenerNotificacionesNoLeidas(String usuarioId) {
        return servicioDominio.obtenerNoLeidas(usuarioId);
    }

    /**
     * Marca una notificación como leída.
     */
    public void marcarComoLeida(String notificacionId) {
        servicioDominio.marcarComoLeida(notificacionId);
    }

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     */
    public void marcarTodasComoLeidas(String usuarioId) {
        repositorio.marcarTodasComoLeidas(usuarioId);
    }

    /**
     * Elimina una notificación.
     */
    public void eliminarNotificacion(String notificacionId) {
        repositorio.eliminar(notificacionId);
    }

    /**
     * Procesa notificaciones pendientes.
     */
    public void procesarPendientes() {
        servicioDominio.procesarNotificacionesPendientes();
    }

    /**
     * Obtiene notificaciones por tipo.
     */
    public List<Notificacion> obtenerPorTipo(String usuarioId, TipoNotificacion tipo) {
        return repositorio.listarPorTipo(usuarioId, tipo);
    }
}

