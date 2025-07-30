package com.planta.plantapp.dominio.modelo;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;

import java.time.LocalDateTime;
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
     * Lista las bitácoras filtradas por tipo de cuidado.
     * @param tipoCuidado Tipo de cuidado a filtrar
     * @return Lista de bitácoras del tipo especificado
     */
    List<Bitacora> listarPorTipoCuidado(String tipoCuidado);

    /**
     * Lista las bitácoras en un rango de fechas.
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de bitácoras en el rango especificado
     */
    List<Bitacora> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Lista las bitácoras de una planta en un rango de fechas.
     * @param plantaId ID de la planta
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de bitácoras filtradas
     */
    List<Bitacora> listarPorPlantaYFechas(String plantaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Cuenta el número total de bitácoras de una planta.
     * @param plantaId ID de la planta
     * @return Número de bitácoras
     */
    long contarPorPlanta(String plantaId);

    /**
     * Obtiene las últimas bitácoras de una planta.
     * @param plantaId ID de la planta
     * @param limite Número máximo de bitácoras a obtener
     * @return Lista de las últimas bitácoras
     */
    List<Bitacora> obtenerUltimasPorPlanta(String plantaId, int limite);
}
