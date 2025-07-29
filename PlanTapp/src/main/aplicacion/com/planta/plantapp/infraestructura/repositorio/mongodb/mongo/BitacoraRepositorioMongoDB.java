package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.infraestructura.documento.BitacoraDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BitacoraRepositorioMongoDB extends MongoRepository<BitacoraDocumento, String> {
}
