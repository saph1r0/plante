package com.planta.plantapp.dominio.modelo;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.EstadoNotificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;

import java.util.List;

/**
 * Repositorio del dominio para gestionar notificaciones.
 * Define las operaciones que el modelo necesita sin depender de la infraestructura.
 */
public interface INotificacionRepositorio {

    /**
     * Obtiene una notificación por su ID.
     * @param id Identificador de la notificación
     * @return Notificación correspondiente o null si no existe
     */
    Notificacion obtenerPorId(String id);

    /**
     * Lista todas las notificaciones de un usuario.
     * @param usuarioId ID del usuario
     * @return Lista de notificaciones
     */
    List<Notificacion> listarPorUsuario(String usuarioId);

    /**
     * Lista notificaciones por usuario y estado.
     * @param usuarioId ID del usuario
     * @param estado Estado de la notificación
     * @return Lista filtrada de notificaciones
     */
    List<Notificacion> listarPorUsuarioYEstado(String usuarioId, EstadoNotificacion estado);

    /**
     * Lista notificaciones por tipo.
     * @param usuarioId ID del usuario
     * @param tipo Tipo de notificación
     * @return Lista filtrada por tipo
     */
    List<Notificacion> listarPorTipo(String usuarioId, TipoNotificacion tipo);

    /**
     * Guarda una nueva notificación o actualiza una existente.
     * @param notificacion Instancia a guardar
     */
    void guardar(Notificacion notificacion);

    /**
     * Elimina una notificación por su ID.
     * @param id Identificador de la notificación
     */
    void eliminar(String id);

    /**
     * Lista todas las notificaciones pendientes de envío.
     * @return Lista de notificaciones pendientes
     */
    List<Notificacion> listarPendientes();

    /**
     * Marca múltiples notificaciones como leídas.
     * @param usuarioId ID del usuario
     */
    void marcarTodasComoLeidas(String usuarioId);
}

