package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaMongoRepository extends MongoRepository<Planta, String> {
    
    // Búsqueda por nombre (común o científico)
    @Query("{'$or': [" +
           "{'nombreComun': {'$regex': ?0, '$options': 'i'}}, " +
           "{'nombreCientifico': {'$regex': ?0, '$options': 'i'}}" +
           "]}")
    List<Planta> findByNombreContaining(String nombre);
    
    // Búsqueda por tipo
    List<Planta> findByTipoIgnoreCase(String tipo);
    
    // Búsqueda por descripción
    @Query("{'descripcion': {'$regex': ?0, '$options': 'i'}}")
    List<Planta> findByDescripcionContaining(String descripcion);
    
    // Contar por tipo
    long countByTipoIgnoreCase(String tipo);
    
    List<Planta> findByNombreComunContainingIgnoreCase(String nombre);
}
