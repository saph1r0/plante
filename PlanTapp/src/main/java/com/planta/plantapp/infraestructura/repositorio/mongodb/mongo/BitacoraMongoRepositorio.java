package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio de MongoDB para la gestión de bitácoras.
 * Extiende MongoRepository para operaciones CRUD básicas y define consultas personalizadas.
 */
@Repository
public interface BitacoraMongoRepositorio extends MongoRepository<Bitacora, String> {

    /**
     * Encuentra todas las bitácoras de una planta ordenadas por fecha descendente.
     * @param plantaId ID de la planta
     * @return Lista de bitácoras
     */
    List<Bitacora> findByPlantaIdOrderByFechaDesc(String plantaId);

    /**
     * Encuentra bitácoras por tipo de cuidado ordenadas por fecha descendente.
     * @param tipoCuidado Tipo de cuidado
     * @return Lista de bitácoras
     */
    List<Bitacora> findByTipoCuidadoOrderByFechaDesc(String tipoCuidado);

    /**
     * Encuentra bitácoras en un rango de fechas ordenadas por fecha descendente.
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de bitácoras
     */
    List<Bitacora> findByFechaBetweenOrderByFechaDesc(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Encuentra bitácoras de una planta en un rango de fechas ordenadas por fecha descendente.
     * @param plantaId ID de la planta
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de bitácoras
     */
    List<Bitacora> findByPlantaIdAndFechaBetweenOrderByFechaDesc(String plantaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Cuenta el número de bitácoras de una planta.
     * @param plantaId ID de la planta
     * @return Número de bitácoras
     */
    long countByPlantaId(String plantaId);

    /**
     * Encuentra bitácoras de una planta con paginación.
     * @param plantaId ID de la planta
     * @param pageable Configuración de paginación
     * @return Página de bitácoras
     */
    Page<Bitacora> findByPlantaId(String plantaId, Pageable pageable);

    /**
     * Encuentra bitácoras que contengan texto en la descripción.
     * @param descripcion Texto a buscar
     * @return Lista de bitácoras
     */
    List<Bitacora> findByDescripcionContainingIgnoreCaseOrderByFechaDesc(String descripcion);

    /**
     * Encuentra bitácoras de una planta que contengan texto en la descripción.
     * @param plantaId ID de la planta
     * @param descripcion Texto a buscar
     * @return Lista de bitácoras
     */
    List<Bitacora> findByPlantaIdAndDescripcionContainingIgnoreCaseOrderByFechaDesc(String plantaId, String descripcion);

    /**
     * Encuentra bitácoras con foto.
     * @return Lista de bitácoras que tienen foto
     */
    List<Bitacora> findByFotoIsNotNullOrderByFechaDesc();

    /**
     * Encuentra bitácoras de una planta con foto.
     * @param plantaId ID de la planta
     * @return Lista de bitácoras con foto
     */
    List<Bitacora> findByPlantaIdAndFotoIsNotNullOrderByFechaDesc(String plantaId);
}
