package com.planta.userplantsservice.infraestructura.repositorio.mongodb.mongo;

import com.planta.userplantsservice.infraestructura.documento.RegistroPlantaDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RegistroPlantaRepository extends MongoRepository<RegistroPlantaDocumento, String> {
    List<RegistroPlantaDocumento> findByUsuarioId(String usuarioId);
}