package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementación MongoDB del repositorio de plantas.
 * Conecta con tu base de datos MongoDB para gestionar las plantitas 🌱
 */
@Repository
@Primary
public class PlantaRepositorioMongoDB implements IPlantaRepositorio {

    public static final String GLOBAL = "global";
    public static final String USUARIOID = "usuarioId";

    private static final Logger logger = LoggerFactory.getLogger(PlantaRepositorioMongoDB.class);

    private final MongoTemplate mongoTemplate;

    public PlantaRepositorioMongoDB(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Planta obtenerPorId(String id) {
        logger.debug("Buscando planta con ID: {}", id);
        Planta planta = mongoTemplate.findById(id, Planta.class);
        if (planta != null) {
            logger.debug("Planta encontrada: {}", planta.getNombreComun());
        } else {
            logger.debug("No se encontró planta con ID: {}", id);
        }
        return planta;
    }

    @Override
    public List<Planta> listarPorUsuario(String usuarioId) {
        logger.debug("Buscando plantas para usuario: {}", usuarioId);

        List<Planta> plantas;
        if (GLOBAL.equals(usuarioId)) {
            // Si es "global", devolver todas las plantas de la base
            plantas = mongoTemplate.findAll(Planta.class);
            logger.info("Obteniendo TODAS las plantas: {}", plantas.size());
        } else {
            // Buscar por usuario específico
            Query query = new Query(Criteria.where(usuarioId).is(usuarioId));
            plantas = mongoTemplate.find(query, Planta.class);
            logger.debug("Plantas del usuario {}: {}", usuarioId, plantas.size());
        }

        // Log de las plantas encontradas para debug
        plantas.forEach(p -> logger.debug(" {} (ID: {})", p.getNombreComun(), p.getId()));

        return plantas;
    }

    @Override
    public void guardar(Planta planta) {
        logger.debug("Guardando planta: {}", planta.getNombreComun());
        try {
            mongoTemplate.save(planta);
            logger.info("Planta guardada exitosamente con ID: {}", planta.getId());
        } catch (Exception e) {
            String mensaje = String.format("Error al guardar la planta '%s': %s",
                    planta.getNombreComun(), e.getMessage());
            logger.error("{}", mensaje, e);
        }
    }

    @Override
    public void eliminar(String id) {
        logger.debug("Eliminando planta con ID: {}", id);
        try {
            Query query = new Query(Criteria.where("id").is(id));
            mongoTemplate.remove(query, Planta.class);
            logger.info("Planta eliminada exitosamente");
        } catch (Exception e) {
            String mensaje = String.format("Error al eliminar la planta con ID '%s': %s",
                    id, e.getMessage());
            logger.error("{}", mensaje, e);
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        logger.debug("Buscando plantas con nombre: '{}' para usuario: {}", nombre, usuarioId);

        Criteria criteria = new Criteria();

        // Búsqueda por nombre (insensible a mayúsculas/minúsculas)
        Pattern pattern = Pattern.compile(nombre, Pattern.CASE_INSENSITIVE);
        criteria.and("nombre").regex(pattern);

        // Filtrar por usuario si no es "global"
        if (!GLOBAL.equals(usuarioId)) {
            criteria.and("USUARIOID").is(usuarioId);
        }

        Query query = new Query(criteria);
        List<Planta> plantas = mongoTemplate.find(query, Planta.class);

        logger.debug("Plantas encontradas: {}", plantas.size());
        return plantas;
    }

    @Override
    public void actualizarEstado(String plantaId, String estadoPlanta) {
        logger.debug("Actualizando estado de planta {} a: {}", plantaId, estadoPlanta);

        try {
            Query query = new Query(Criteria.where("id").is(plantaId));
            Update update = new Update().set("estado", estadoPlanta);
            mongoTemplate.updateFirst(query, update, Planta.class);
            logger.info("Estado actualizado exitosamente");
        } catch (Exception e) {
            logger.error("Error al actualizar estado: {}", e.getMessage(), e);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        logger.debug("Buscando plantas de tipo: {}", tipo);
        try {
            Query query = new Query(Criteria.where("tipo").is(tipo));
            return mongoTemplate.find(query, Planta.class);
        } catch (Exception e) {
            String mensaje = String.format(
                    "Error al buscar plantas de tipo '%s': %s",
                    tipo, e.getMessage()
            );
            throw new IllegalArgumentException(mensaje, e);
        }
    }


    @Override
    public Long contarPorUsuario(String usuarioId) {
        logger.debug("Contando plantas del usuario: {}", usuarioId);

        try {
            Query query;
            if (GLOBAL.equals(usuarioId)) {
                query = new Query(); // Contar todas
            } else {
                query = new Query(Criteria.where(usuarioId).is(usuarioId));
            }

            return mongoTemplate.count(query, Planta.class);
        } catch (Exception e) {
            String mensaje = String.format("Error al contar plantas del usuario '%s': %s",
                    usuarioId, e.getMessage());
            throw new IllegalStateException(mensaje, e);
        }
    }
}