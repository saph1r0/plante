package com.planta.plantapp.dominio.modelo;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;

import java.util.Date;
import java.util.List;

/**
 * Repositorio del dominio que define las operaciones disponibles
 * para gestionar bitácoras asociadas a plantas.
 *
 * Esta interfaz pertenece al dominio del problema y no define detalles de implementación.
 */
public interface IBitacoraRepositorio {

    /**
     * Guarda una nueva bitácora en el sistema.
     * @param bitacora Bitácora a guardar
     */
    void guardar(Bitacora bitacora);

    /**
     * Obtiene una bitácora por su identificador único.
     * @param id Identificador de la bitácora
     * @return Bitacora correspondiente o null si no existe
     */
    Bitacora obtenerPorId(String id);

    /**
     * Lista todas las bitácoras registradas.
     * @return Lista completa de bitácoras
     */
    List<Bitacora> listarTodas();

    /**
     * Elimina una bitácora según su ID.
     * @param id Identificador de la bitácora a eliminar
     */
    void eliminar(String id);

    /**
     * Actualiza una bitácora existente.
     * @param bitacora Bitácora con los cambios aplicados
     */
    void actualizar(Bitacora bitacora);

    /**
     * Lista todas las bitácoras asociadas a una planta específica.
     * @param plantaId ID de la planta
     * @return Lista de bitácoras asociadas
     */
    List<Bitacora> listarPorPlanta(String plantaId);

    /**
     * Lista todas las bitácoras registradas por un usuario específico.
     * @param usuarioId ID del usuario
     * @return Lista de bitácoras creadas por el usuario
     */
    List<Bitacora> listarPorUsuario(String usuarioId);

    /**
     * Lista todas las bitácoras dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha final del rango
     * @return Lista de bitácoras en el rango
     */
    List<Bitacora> listarPorFecha(Date fechaInicio, Date fechaFin);

    /**
     * Lista las bitácoras por tipo de actividad (riego, poda, etc.).
     * @param tipoActividad Nombre del tipo de actividad
     * @return Lista de bitácoras filtradas
     */
    List<Bitacora> listarPorTipoActividad(String tipoActividad);

    /**
     * Busca bitácoras que contengan una descripción parcial o total.
     * @param descripcion Texto a buscar
     * @return Lista de coincidencias
     */
    List<Bitacora> buscarPorDescripcion(String descripcion);

    /**
     * Cuenta cuántas bitácoras existen para una planta.
     * @param plantaId ID de la planta
     * @return Número total de registros
     */
    Long contarRegistrosPorPlanta(String plantaId);

    /**
     * Obtiene la última bitácora registrada para una planta.
     * @param plantaId ID de la planta
     * @return Último registro de bitácora o null si no hay registros
     */
    Bitacora obtenerUltimoRegistro(String plantaId);
}