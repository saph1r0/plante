package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.infraestructura.documento.PlantaDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlantaMongoRepositorio extends MongoRepository<PlantaDocumento, String> {
}
