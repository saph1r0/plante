package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.infraestructura.documento.PlantaDocumento;
import com.planta.plantapp.infraestructura.mapper.PlantaDocumentoMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementación de IPlantaRepositorio usando MongoDB.
 * Aplica principios SOLID y Clean Code.
 */
@Repository
public class PlantaRepositorioImpl implements IPlantaRepositorio {

    private static final Logger logger = LoggerFactory.getLogger(PlantaRepositorioImpl.class);

    // Constantes para evitar magic strings (Refactoring: Replace Magic Strings)
    private static final String CAMPO_ID = "_id";
    private static final String CAMPO_USUARIO_ID = "usuarioId";
    private static final String CAMPO_NOMBRE_COMUN = "nombreComun";
    private static final String CAMPO_ESTADO = "estado";
    private static final String CAMPO_ETIQUETAS = "etiquetas.nombre";

    private final MongoTemplate mongoTemplate;
    private final PlantaDocumentoMapper mapper;

    public PlantaRepositorioImpl(MongoTemplate mongoTemplate, PlantaDocumentoMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    @Override
    public Planta obtenerPorId(String id) {
        // Refactoring: Guard Clauses para validaciones tempranas
        if (id == null || id.isBlank()) {
            logger.warn("Intento de buscar planta con ID nulo o vacío");
            return null;
        }

        try {
            PlantaDocumento documento = mongoTemplate.findById(id, PlantaDocumento.class);
            return documento != null ? mapper.documentoADominio(documento) : null;
        } catch (Exception e) {
            logger.error("Error al obtener planta por ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public List<Planta> listarPorUsuario(String usuarioId) {
        // Refactoring: Guard Clauses
        if (usuarioId == null || usuarioId.isBlank()) {
            logger.warn("Intento de listar plantas con usuarioId nulo o vacío");
            return List.of();
        }

        try {
            Query query = crearQueryPorUsuario(usuarioId);
            List<PlantaDocumento> documentos = mongoTemplate.find(query, PlantaDocumento.class);
            return convertirListaADominio(documentos);
        } catch (Exception e) {
            logger.error("Error al listar plantas del usuario {}: {}", usuarioId, e.getMessage());
            return List.of();
        }
    }

    @Override
    public void guardar(Planta planta) {
        // Refactoring: Guard Clauses + Extract Method
        if (!esPlantaValida(planta)) {
            logger.error("Intento de guardar planta inválida");
            throw new IllegalArgumentException("La planta no puede ser nula y debe tener nombre");
        }

        try {
            PlantaDocumento documento = mapper.dominioADocumento(planta);
            mongoTemplate.save(documento);
            logger.info("Planta guardada exitosamente: {}", planta.getNombreComun());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar planta en MongoDB", e);
        }
    }

    @Override
    public void eliminar(String id) {
        // Refactoring: Guard Clauses
        if (id == null || id.isBlank()) {
            logger.warn("Intento de eliminar planta con ID nulo o vacío");
            return;
        }

        try {
            Query query = new Query(Criteria.where(CAMPO_ID).is(id));
            mongoTemplate.remove(query, PlantaDocumento.class);
            logger.info("Planta eliminada: {}", id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al eliminar planta de MongoDB", e);
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        // Refactoring: Guard Clauses
        if (nombre == null || nombre.isBlank()) {
            logger.warn("Intento de buscar con nombre nulo o vacío");
            return List.of();
        }

        try {
            Query query = crearQueryBusquedaPorNombre(nombre, usuarioId);
            List<PlantaDocumento> documentos = mongoTemplate.find(query, PlantaDocumento.class);
            return convertirListaADominio(documentos);
        } catch (Exception e) {
            logger.error("Error al buscar plantas por nombre {}: {}", nombre, e.getMessage());
            return List.of();
        }
    }

    @Override
    public void actualizarEstado(String plantaId, String estadoPlanta) {
        // Refactoring: Guard Clauses
        if (plantaId == null || plantaId.isBlank() || estadoPlanta == null) {
            logger.warn("Parámetros inválidos para actualizar estado");
            throw new IllegalArgumentException("ID de planta y estado son requeridos");
        }

        try {
            Query query = new Query(Criteria.where(CAMPO_ID).is(plantaId));
            Update update = new Update().set(CAMPO_ESTADO, estadoPlanta);
            mongoTemplate.updateFirst(query, update, PlantaDocumento.class);
            logger.info("Estado actualizado para planta {}: {}", plantaId, estadoPlanta);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar estado en MongoDB", e);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        // Refactoring: Guard Clauses
        if (tipo == null || tipo.isBlank()) {
            logger.warn("Intento de buscar con tipo nulo o vacío");
            return List.of();
        }

        try {
            Query query = crearQueryPorTipo(tipo);
            List<PlantaDocumento> documentos = mongoTemplate.find(query, PlantaDocumento.class);
            return convertirListaADominio(documentos);
        } catch (Exception e) {
            logger.error("Error al buscar plantas por tipo {}: {}", tipo, e.getMessage());
            return List.of();
        }
    }

    @Override
    public Long contarPorUsuario(String usuarioId) {
        // Refactoring: Guard Clauses
        if (usuarioId == null || usuarioId.isBlank()) {
            logger.warn("Intento de contar con usuarioId nulo o vacío");
            return 0L;
        }

        try {
            Query query = crearQueryPorUsuario(usuarioId);
            return mongoTemplate.count(query, PlantaDocumento.class);
        } catch (Exception e) {
            logger.error("Error al contar plantas del usuario {}: {}", usuarioId, e.getMessage());
            return 0L;
        }
    }

    // ========================================
    // Métodos Privados Extraídos (Refactoring: Extract Method)
    // ========================================

    /**
     * Valida que la planta sea válida para ser guardada.
     * Refactoring: Extract Method para mejorar legibilidad
     */
    private boolean esPlantaValida(Planta planta) {
        return planta != null
                && planta.getNombreComun() != null
                && !planta.getNombreComun().isBlank();
    }

    /**
     * Crea una query de MongoDB para buscar por usuario.
     * Refactoring: Extract Method para reutilización
     */
    private Query crearQueryPorUsuario(String usuarioId) {
        return new Query(Criteria.where(CAMPO_USUARIO_ID).is(usuarioId));
    }

    /**
     * Crea una query para búsqueda por nombre (case-insensitive).
     * Refactoring: Extract Method
     */
    private Query crearQueryBusquedaPorNombre(String nombre, String usuarioId) {
        Criteria criteria = Criteria.where(CAMPO_NOMBRE_COMUN)
                .regex(nombre, "i"); // búsqueda case-insensitive

        if (usuarioId != null && !usuarioId.isBlank()) {
            criteria.and(CAMPO_USUARIO_ID).is(usuarioId);
        }

        return new Query(criteria);
    }

    /**
     * Crea una query para buscar por tipo (etiquetas).
     * Refactoring: Extract Method
     */
    private Query crearQueryPorTipo(String tipo) {
        return new Query(Criteria.where(CAMPO_ETIQUETAS).regex(tipo, "i"));
    }

    /**
     * Convierte lista de documentos a lista de entidades de dominio.
     * Refactoring: Extract Method para evitar código duplicado
     */
    private List<Planta> convertirListaADominio(List<PlantaDocumento> documentos) {
        return documentos.stream()
                .map(mapper::documentoADominio).toList();
    }
}