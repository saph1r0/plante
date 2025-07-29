package com.planta.demo.dominio.modelo;

import com.planta.demo.dominio.modelo.recordatorio.Recordatorio;

import java.util.Date;
import java.util.List;

/**
 * Repositorio del dominio para gestionar recordatorios asociados a plantas y usuarios.
 * 
 * Define las operaciones que el modelo necesita sin depender de la infraestructura.
 */
public interface IRecordatorioRepositorio {

    /**
     * Obtiene un recordatorio por su ID.
     * @param id Identificador del recordatorio
     * @return Recordatorio correspondiente o null si no existe
     */
    Recordatorio obtenerPorId(String id);

    /**
     * Lista todos los recordatorios asociados a una planta.
     * @param plantaId ID de la planta
     * @return Lista de recordatorios
     */
    List<Recordatorio> listarPorPlanta(String plantaId);

    /**
     * Guarda un nuevo recordatorio o actualiza uno existente.
     * @param recordatorio Instancia a guardar
     */
    void guardar(Recordatorio recordatorio);

    /**
     * Elimina un recordatorio por su ID.
     * @param id Identificador del recordatorio a eliminar
     */
    void eliminar(String id);

    /**
     * Obtiene los recordatorios programados a partir de cierta fecha para un usuario.
     * @param usuarioId ID del usuario
     * @param fecha Fecha desde la cual buscar recordatorios
     * @return Lista de recordatorios próximos
     */
    List<Recordatorio> obtenerProximosPorUsuario(String usuarioId, Date fecha);

    /**
     * Marca como completado un recordatorio.
     * @param id ID del recordatorio
     */
    void marcarComoCompletado(String id);

    /**
     * Lista todos los recordatorios pendientes (activos y no vencidos) de un usuario.
     * @param usuarioId ID del usuario
     * @return Lista de recordatorios pendientes
     */
    List<Recordatorio> listarPendientesPorUsuario(String usuarioId);

    /**
     * Lista recordatorios por tipo de cuidado (riego, poda, fertilización, etc.) y usuario.
     * @param tipo Tipo de cuidado
     * @param usuarioId ID del usuario
     * @return Lista filtrada por tipo
     */
    List<Recordatorio> listarPorTipo(String tipo, String usuarioId);
}
