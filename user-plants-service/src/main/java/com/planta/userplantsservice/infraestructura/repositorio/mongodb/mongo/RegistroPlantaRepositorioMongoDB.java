package com.planta.userplantsservice.infraestructura.repositorio.mongodb.mongo;

import com.planta.userplantsservice.infraestructura.documento.RegistroPlantaDocumento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RegistroPlantaRepositorioMongoDB {

    private static final Logger logger = LoggerFactory.getLogger(RegistroPlantaRepositorioMongoDB.class);
    private final RegistroPlantaRepository repository; // 👈 Usamos la interfaz ahora

    public RegistroPlantaRepositorioMongoDB(RegistroPlantaRepository repository) {
        this.repository = repository;
    }

    public RegistroPlantaDocumento guardar(RegistroPlantaDocumento registro) {
        logger.info("Guardando registro para usuario: {}", registro.getUsuarioId());
        return repository.save(registro);
    }

    public List<RegistroPlantaDocumento> listarPorUsuario(String usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }

    public RegistroPlantaDocumento obtenerPorId(String id) {
        return repository.findById(id).orElse(null);
    }
}