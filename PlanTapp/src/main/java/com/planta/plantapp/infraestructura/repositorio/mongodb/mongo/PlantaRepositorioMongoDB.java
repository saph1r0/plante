package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Planta obtenerPorId(String id) {
        System.out.println("🔍 Buscando planta con ID: " + id);
        Planta planta = mongoTemplate.findById(id, Planta.class);
        if (planta != null) {
            System.out.println("✅ Planta encontrada: " + planta.getNombreComun());
        } else {
            System.out.println("❌ No se encontró planta con ID: " + id);
        }
        return planta;
    }

    @Override
    public List<Planta> listarPorUsuario(String usuarioId) {
        System.out.println("🔍 Buscando plantas para usuario: " + usuarioId);

        List<Planta> plantas;
        if ("global".equals(usuarioId)) {
            // Si es "global", devolver todas las plantas de la base
            plantas = mongoTemplate.findAll(Planta.class);
            System.out.println("🌍 Obteniendo TODAS las plantas: " + plantas.size());
        } else {
            // Buscar por usuario específico
            Query query = new Query(Criteria.where("usuarioId").is(usuarioId));
            plantas = mongoTemplate.find(query, Planta.class);
            System.out.println("👤 Plantas del usuario " + usuarioId + ": " + plantas.size());
        }

        // Log de las plantas encontradas para debug
        plantas.forEach(p -> System.out.println("  🌱 " + p.getNombreComun() + " (ID: " + p.getId() + ")"));

        return plantas;
    }

    @Override
    public void guardar(Planta planta) {
        System.out.println("💾 Guardando planta: " + planta.getNombreComun());
        try {
            mongoTemplate.save(planta);
            System.out.println("✅ Planta guardada exitosamente con ID: " + planta.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al guardar planta: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void eliminar(String id) {
        System.out.println("🗑️ Eliminando planta con ID: " + id);
        try {
            Query query = new Query(Criteria.where("id").is(id));
            mongoTemplate.remove(query, Planta.class);
            System.out.println("✅ Planta eliminada exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar planta: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        System.out.println("🔍 Buscando plantas con nombre: '" + nombre + "' para usuario: " + usuarioId);

        Criteria criteria = new Criteria();

        // Búsqueda por nombre (insensible a mayúsculas/minúsculas)
        Pattern pattern = Pattern.compile(nombre, Pattern.CASE_INSENSITIVE);
        criteria.and("nombre").regex(pattern);

        // Filtrar por usuario si no es "global"
        if (!"global".equals(usuarioId)) {
            criteria.and("usuarioId").is(usuarioId);
        }

        Query query = new Query(criteria);
        List<Planta> plantas = mongoTemplate.find(query, Planta.class);

        System.out.println("📊 Plantas encontradas: " + plantas.size());
        return plantas;
    }

    @Override
    public void actualizarEstado(String plantaId, String estadoPlanta) {
        System.out.println("🔄 Actualizando estado de planta " + plantaId + " a: " + estadoPlanta);

        try {
            Query query = new Query(Criteria.where("id").is(plantaId));
            Update update = new Update().set("estado", estadoPlanta);
            mongoTemplate.updateFirst(query, update, Planta.class);
            System.out.println("✅ Estado actualizado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar estado: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        System.out.println("🔍 Buscando plantas de tipo: " + tipo);

        try {
            Query query = new Query(Criteria.where("tipo").is(tipo));
            List<Planta> plantas = mongoTemplate.find(query, Planta.class);
            System.out.println("📊 Plantas de tipo '" + tipo + "' encontradas: " + plantas.size());
            return plantas;
        } catch (Exception e) {
            System.err.println("❌ Error al buscar por tipo: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Long contarPorUsuario(String usuarioId) {
        System.out.println("🔢 Contando plantas del usuario: " + usuarioId);

        try {
            Query query;
            if ("global".equals(usuarioId)) {
                query = new Query(); // Contar todas
            } else {
                query = new Query(Criteria.where("usuarioId").is(usuarioId));
            }

            long count = mongoTemplate.count(query, Planta.class);
            System.out.println("📊 Total de plantas: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("❌ Error al contar plantas: " + e.getMessage());
            throw e;
        }
    }
}