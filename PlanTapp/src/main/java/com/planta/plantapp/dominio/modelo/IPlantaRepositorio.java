package com.planta.plantapp.dominio.modelo;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.RegistroPlanta;

import java.util.Date;
import java.util.List;

/**
 * Repositorio del dominio que define las operaciones disponibles
 * para gestionar tanto el catálogo de plantas como los registros personales.
 */
public interface IPlantaRepositorio {

    // ========================================
    // CATÁLOGO DE PLANTAS (Información fija)
    // ========================================

    /**
     * Guarda una planta en el catálogo.
     * @param planta Planta a guardar en el catálogo
     */
    void guardar(Planta planta);

    /**
     * Obtiene una planta del catálogo por su identificador único.
     * @param id Identificador de la planta en el catálogo
     * @return Planta del catálogo o null si no existe
     */
    Planta obtenerPorId(String id);

    /**
     * Lista todas las plantas del catálogo.
     * @return Lista completa del catálogo
     */
    List<Planta> listarTodas();

    /**
     * Busca plantas en el catálogo por nombre común.
     * Usado para el buscador del frontend.
     * @param nombre Nombre a buscar (parcial o completo)
     * @return Lista de plantas que coinciden
     */
    List<Planta> buscarPorNombre(String nombre);

    /**
     * Busca plantas del catálogo por tipo.
     * @param tipo Tipo de planta a filtrar
     * @return Lista de plantas del tipo especificado
     */
    List<Planta> buscarPorTipo(String tipo);

    /**
     * Elimina una planta del catálogo (uso administrativo).
     * @param id Identificador de la planta a eliminar
     */
    void eliminar(String id);

    // ========================================
    // REGISTROS PERSONALES DE PLANTAS
    // ========================================

    /**
     * Guarda un registro personal de planta.
     * @param registro Registro personal a guardar
     */
    void guardarRegistro(RegistroPlanta registro);

    /**
     * Lista todos los registros personales de un usuario.
     * Este es el método principal para "Mis Plantas".
     * @param usuarioId ID del usuario
     * @return Lista de registros del usuario
     */
    List<RegistroPlanta> listarRegistrosPorUsuario(String usuarioId);

    /**
     * Actualiza un registro personal existente.
     * @param registro Registro con los cambios aplicados
     */
    void actualizarRegistro(RegistroPlanta registro);

    /**
     * Elimina un registro personal.
     * @param registroId ID del registro a eliminar
     */
    void eliminarRegistro(int registroId);

    /**
     * Lista registros por estado de planta.
     * @param estado Estado a filtrar
     * @param usuarioId ID del usuario
     * @return Lista de registros en el estado especificado
     */
    List<RegistroPlanta> listarRegistrosPorEstado(String estado, String usuarioId);

    /**
     * Cuenta cuántos registros tiene un usuario.
     * @param usuarioId ID del usuario
     * @return Número total de plantas registradas por el usuario
     */
    Long contarRegistrosPorUsuario(String usuarioId);

    // ========================================
    // MÉTODOS HEREDADOS (para compatibilidad)
    // ========================================

    /**
     *             Razón: El nuevo método retorna {@code RegistroPlanta} que contiene más información
     *             que solo la {@code Planta}, incluyendo estado, fechas y cuidados aplicados.
     * @param usuarioId ID del usuario
     * @return Lista de plantas (sin información de registro)
     * @see #listarRegistrosPorUsuario(String)
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    default List<Planta> listarPorUsuario(String usuarioId) {
        // Por compatibilidad, se mantiene pero se recomienda usar registros
        // TODO: Remover en versión 3.0.0 - Marzo 2025
        return listarTodas();
    }

    // ========================================
    // BITÁCORAS
    // ========================================

    /**
     * Lista todas las bitácoras registradas para una planta específica.
     * @param plantaId ID de la planta
     * @return Lista de bitácoras asociadas
     */
    List<Bitacora> listarBitacorasPorPlanta(String plantaId);

    /**
     * Lista bitácoras por usuario (de todos sus registros).
     * @param usuarioId ID del usuario
     * @return Lista de bitácoras del usuario
     */
    List<Bitacora> listarBitacorasPorUsuario(String usuarioId);

    /**
     * Lista bitácoras dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha final del rango
     * @return Lista de bitácoras en el rango
     */
    List<Bitacora> listarBitacorasPorFecha(Date fechaInicio, Date fechaFin);

    // ========================================
    // MÉTODOS ADICIONALES
    // ========================================

    /**
     * Busca plantas del catálogo por descripción.
     * @param descripcion Texto a buscar en la descripción
     * @return Lista de coincidencias
     */
    List<Planta> buscarPorDescripcion(String descripcion);

    /**
     * Obtiene el último registro de un usuario.
     * @param usuarioId ID del usuario
     * @return Último registro creado o null si no tiene registros
     */
    RegistroPlanta obtenerUltimoRegistro(String usuarioId);

    // ========================================
    // MÉTODOS DE ESTADÍSTICAS
    // ========================================

    /**
     * Cuenta plantas del catálogo por tipo.
     * @param tipo Tipo a contar
     * @return Número de plantas del tipo en el catálogo
     */
    Long contarPlantasPorTipo(String tipo);

    /**
     * Obtiene las plantas más populares (más registradas por usuarios).
     * @param limite Número máximo de resultados
     * @return Lista de plantas ordenadas por popularidad
     */
    List<Planta> obtenerPlantasPopulares(int limite);
}
