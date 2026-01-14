package com.planta.userplantsservice.infraestructura.repositorio.mongodb.mongo;

import com.planta.userplantsservice.dominio.modelo.planta.EstadoPlanta;
import com.planta.userplantsservice.infraestructura.documento.RegistroPlantaDocumento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class RegistroPlantaRepositoryIntTest {

    @Autowired
    private RegistroPlantaRepository repository;

    @Test
    void findByUsuarioId_DeberiaRetornarSoloPlantasDelUsuarioSolicitado() {
        repository.deleteAll();
        repository.save(new RegistroPlantaDocumento("user-A", "p1", "Rosa", "Sala", EstadoPlanta.SALUDABLE));
        repository.save(new RegistroPlantaDocumento("user-B", "p1", "Cactus", "Oficina", EstadoPlanta.SALUDABLE));

        List<RegistroPlantaDocumento> resultado = repository.findByUsuarioId("user-A");

        assertEquals(1, resultado.size());
        assertEquals("Rosa", resultado.get(0).getApodo());
        assertEquals("user-A", resultado.get(0).getUsuarioId());
    }
}