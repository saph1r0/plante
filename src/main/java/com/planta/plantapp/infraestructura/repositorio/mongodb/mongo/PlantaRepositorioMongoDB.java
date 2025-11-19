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
 * Conecta con la base de datos MongoDB para gestionar las plantitas 🌱
 */
@Repository
@Primary
public class PlantaRepositorioMongoDB implements IPlantaRepositorio {

    private static final String GLOBAL = "global";
    private static final String USUARIO_ID_FIELD = "usuarioId";

    private static final Logger logger = LoggerFactory.getLogger(PlantaRepositorioMongoDB.class);

    private final MongoTemplate mongoTemplate;

    public PlantaRepositorioMongoDB(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Planta obtenerPorId(String id) {
        logger.debug("🔍 Buscando planta con ID: {}", id);
        Planta planta = mongoTemplate.findById(id, Planta.class);
        if (planta != null) {
            logger.debug("✅ Planta encontrada: {}", planta.getNombreComun());
        } else {
            logger.debug("❌ No se encontró planta con ID: {}", id);
        }
        return planta;
    }

    @Override
    public List<Planta> listarPorUsuario(String usuarioId) {
        logger.debug("🔍 Buscando plantas para usuario: {}", usuarioId);

        try {
            List<Planta> plantas;

            if (GLOBAL.equals(usuarioId)) {
                plantas = mongoTemplate.findAll(Planta.class);
                logger.info("🌍 Obteniendo TODAS las plantas: {}", plantas.size());
            } else {
                Query query = new Query(Criteria.where(USUARIO_ID_FIELD).is(usuarioId));
                plantas = mongoTemplate.find(query, Planta.class);
                logger.debug("👤 Plantas del usuario {}: {}", usuarioId, plantas.size());
            }

            plantas.forEach(p -> logger.debug("  🌱 {} (ID: {})", p.getNombreComun(), p.getId()));
            return plantas;

        } catch (Exception e) {
            String msg = "Error al listar plantas del usuario '" + usuarioId + "'";
            logger.error("❌ {}", msg); // NO enviar `e` aquí
            throw new IllegalStateException(msg, e); // se relanza con contexto
        }
    }

    @Override
    public void guardar(Planta planta) {
        logger.debug("💾 Guardando planta: {}", planta.getNombreComun());
        try {
            mongoTemplate.save(planta);
            logger.info("✅ Planta guardada exitosamente con ID: {}", planta.getId());
        } catch (Exception e) {
            String msg = "Error al guardar la planta '" + planta.getNombreComun() + "'";
            logger.error("❌ {}", msg);
            throw new IllegalStateException(msg, e);
        }
    }

    @Override
    public void eliminar(String id) {
        logger.debug("🗑️ Eliminando planta con ID: {}", id);
        try {
            Query query = new Query(Criteria.where("id").is(id));
            mongoTemplate.remove(query, Planta.class);
            logger.info("✅ Planta eliminada exitosamente");
        } catch (Exception e) {
            String msg = "Error al eliminar la planta con ID '" + id + "'";
            logger.error("❌ {}", msg);
            throw new IllegalStateException(msg, e);
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        logger.debug("🔍 Buscando plantas con nombre: '{}' para usuario: {}", nombre, usuarioId);

        try {
            if (nombre == null || nombre.isBlank()) {
                if (GLOBAL.equals(usuarioId)) {
                    return mongoTemplate.findAll(Planta.class);
                }
                Query query = new Query(Criteria.where(USUARIO_ID_FIELD).is(usuarioId));
                return mongoTemplate.find(query, Planta.class);
            }

            Pattern pattern = Pattern.compile(nombre, Pattern.CASE_INSENSITIVE);

            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("nombreComun").regex(pattern),
                    Criteria.where("nombreCientifico").regex(pattern)
            );

            if (!GLOBAL.equals(usuarioId)) {
                criteria.and(USUARIO_ID_FIELD).is(usuarioId);
            }

            Query query = new Query(criteria);
            List<Planta> plantas = mongoTemplate.find(query, Planta.class);

            logger.debug("📊 Plantas encontradas: {}", plantas.size());
            return plantas;

        } catch (Exception e) {
            String msg = "Error al buscar plantas por nombre para el usuario '" + usuarioId + "'";
            logger.error("❌ {}", msg);
            throw new IllegalStateException(msg, e);
        }
    }

    @Override
    public void actualizarEstado(String plantaId, String estadoPlanta) {
        logger.debug("🔄 Actualizando estado de planta {} a: {}", plantaId, estadoPlanta);

        try {
            Query query = new Query(Criteria.where("id").is(plantaId));
            Update update = new Update().set("estado", estadoPlanta);
            mongoTemplate.updateFirst(query, update, Planta.class);
            logger.info("✅ Estado actualizado exitosamente");
        } catch (Exception e) {
            String msg = "Error al actualizar estado de planta '" + plantaId + "'";
            logger.error("❌ {}", msg);
            throw new IllegalStateException(msg, e);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        logger.debug("🔍 Buscando plantas de tipo: {}", tipo);

        try {
            Query query = new Query(Criteria.where("tipo").is(tipo));
            List<Planta> plantas = mongoTemplate.find(query, Planta.class);

            logger.debug("📊 Plantas de tipo '{}' encontradas: {}", tipo, plantas.size());
            return plantas;

        } catch (Exception e) {
            String msg = "Error al buscar plantas de tipo '" + tipo + "'";
            logger.error("❌ {}", msg);
            throw new IllegalArgumentException(msg, e);
        }
    }

    @Override
    public Long contarPorUsuario(String usuarioId) {
        logger.debug("🔢 Contando plantas del usuario: {}", usuarioId);

        try {
            Query query;

            if (GLOBAL.equals(usuarioId)) {
                query = new Query();
            } else {
                query = new Query(Criteria.where(USUARIO_ID_FIELD).is(usuarioId));
            }

            long count = mongoTemplate.count(query, Planta.class);
            logger.debug("📊 Total de plantas: {}", count);
            return count;

        } catch (Exception e) {
            String msg = "Error al contar plantas del usuario '" + usuarioId + "'";
            logger.error("❌ {}", msg);
            throw new IllegalStateException(msg, e);
        }
    }
}
