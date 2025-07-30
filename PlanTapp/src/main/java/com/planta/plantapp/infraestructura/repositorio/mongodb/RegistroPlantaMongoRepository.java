package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.planta.RegistroPlanta;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroPlantaMongoRepository extends MongoRepository<RegistroPlanta, String> {
    
    // Buscar por usuario
    List<RegistroPlanta> findByUsuarioIdOrderByFechaRegistroDesc(String usuarioId);
    
    // Buscar por estado y usuario
    List<RegistroPlanta> findByEstadoAndUsuarioId(EstadoPlanta estado, String usuarioId);
    
    // Contar por usuario
    long countByUsuarioId(String usuarioId);
    
    // Buscar por rango de fechas
    List<RegistroPlanta> findByFechaRegistroBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Último registro de un usuario
    RegistroPlanta findFirstByUsuarioIdOrderByFechaRegistroDesc(String usuarioId);
    
    // Registros que necesitan atención
    @Query("{'estado': {'$in': ['NECESITA_AGUA', 'ENFERMA', 'MARCHITA']}, 'usuarioId': ?0}")
    List<RegistroPlanta> findRegistrosQueNecesitanAtencion(String usuarioId);
    List<RegistroPlanta> findByUsuarioId(String usuarioId);
}
