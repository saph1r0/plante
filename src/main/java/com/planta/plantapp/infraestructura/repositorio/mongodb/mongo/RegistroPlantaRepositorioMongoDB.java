package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio MongoDB para los registros personales de plantas 🌱
 */
@Repository
public class RegistroPlantaRepositorioMongoDB {

    private static final Logger logger = LoggerFactory.getLogger(RegistroPlantaRepositorioMongoDB.class);
    private final MongoTemplate mongoTemplate;

    public RegistroPlantaRepositorioMongoDB(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public RegistroPlantaDocumento guardar(RegistroPlantaDocumento registro) {
        logger.info("💾 Guardando registro de planta personal para usuario: {}", registro.getUsuarioId());
        return mongoTemplate.save(registro);
    }

    public List<RegistroPlantaDocumento> listarPorUsuario(String usuarioId) {
        logger.info("🔍 Buscando plantas personales del usuario: {}", usuarioId);
        Query query = new Query(Criteria.where("usuarioId").is(usuarioId));
        return mongoTemplate.find(query, RegistroPlantaDocumento.class);
    }

    public void eliminar(String id) {
        logger.info("🗑️ Eliminando registro con ID: {}", id);
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, RegistroPlantaDocumento.class);
    }

    public RegistroPlantaDocumento obtenerPorId(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, RegistroPlantaDocumento.class);
    }

}
