package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz del servicio de aplicación para la gestión de bitácoras.
 * Define las operaciones de alto nivel disponibles para la gestión de bitácoras.
 */
public interface IServicioBitacora {

    /**
     * Registra una nueva entrada en la bitácora.
     * @param descripcion Descripción del cuidado realizado
     * @param foto URL o path de la foto (opcional)
     * @param plantaId ID de la planta
     * @param tipoCuidado Tipo de cuidado realizado
     * @param observaciones Observaciones adicionales (opcional)
     * @return La bitácora creada
     */
    Bitacora registrarEntrada(String descripcion, String foto, String plantaId, String tipoCuidado, String observaciones);

    /**
     * Obtiene una bitácora por su ID.
     * @param id ID de la bitácora
     * @return La bitácora encontrada
     */
    Bitacora obtenerBitacora(String id);

    /**
     * Lista todas las bitácoras del sistema.
     * @return Lista de todas las bitácoras
     */
    List<Bitacora> listarTodasLasBitacoras();

    /**
     * Lista las bitácoras de una planta específica.
     * @param plantaId ID de la planta
     * @return Lista de bitácoras de la planta
     */
    List<Bitacora> listarBitacorasPorPlanta(String plantaId);

    /**
     * Lista las bitácoras por tipo de cuidado.
     * @param tipoCuidado Tipo de cuidado a filtrar
     * @return Lista de bitácoras del tipo especificado
     */
    List<Bitacora> listarBitacorasPorTipoCuidado(String tipoCuidado);

    /**
     * Lista las bitácoras en un rango de fechas.
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de bitácoras en el rango
     */
    List<Bitacora> listarBitacorasPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Lista las bitácoras de una planta en un rango de fechas.
     * @param plantaId ID de la planta
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de bitácoras filtradas
     */
    List<Bitacora> listarBitacorasPorPlantaYFechas(String plantaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Actualiza una bitácora existente.
     * @param id ID de la bitácora
     * @param descripcion Nueva descripción
     * @param foto Nueva foto (opcional)
     * @param tipoCuidado Nuevo tipo de cuidado
     * @param observaciones Nuevas observaciones
     * @return La bitácora actualizada
     */
    Bitacora actualizarBitacora(String id, String descripcion, String foto, String tipoCuidado, String observaciones);

    /**
     * Elimina una bitácora.
     * @param id ID de la bitácora a eliminar
     */
    void eliminarBitacora(String id);

    /**
     * Obtiene el historial reciente de una planta.
     * @param plantaId ID de la planta
     * @param limite Número máximo de entradas a obtener
     * @return Lista de las últimas bitácoras
     */
    List<Bitacora> obtenerHistorialReciente(String plantaId, int limite);

    /**
     * Obtiene estadísticas de cuidados de una planta.
     * @param plantaId ID de la planta
     * @return Número total de entradas en la bitácora
     */
    long obtenerTotalCuidados(String plantaId);

    /**
     * Exporta las bitácoras de una planta a formato de reporte.
     * @param plantaId ID de la planta
     * @return Datos para el reporte
     */
    List<Bitacora> exportarBitacorasPlanta(String plantaId);
}