package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementación de IPlantaRepositorio usando MongoDB (infraestructura).
 */
@Repository
public class PlantaRepositorioImpl implements IPlantaRepositorio {

    public PlantaRepositorioImpl() {
        // Constructor por defecto (inyecciones si es necesario)
    }

    @Override
    public Planta obtenerPorId(String id) {
        // TODO: Implementar búsqueda en MongoDB por ID
        return null;
    }

    @Override
    public List<Planta> listarPorUsuario(String usuarioId) {
        // TODO: Consultar MongoDB por usuarioId
        return null;
    }

    @Override
    public void guardar(Planta planta) {
        // TODO: Insertar o actualizar la planta en MongoDB
    }

    @Override
    public void eliminar(String id) {
        // TODO: Eliminar por ID en MongoDB
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        // TODO: Buscar plantas por nombre y usuarioId
        return null;
    }

    @Override
    public void actualizarEstado(String plantaId, String estadoPlanta) {
        // TODO: Actualizar solo el campo estado de la planta
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        // TODO: Buscar plantas por tipo (etiqueta o especie)
        return null;
    }

    @Override
    public Long contarPorUsuario(String usuarioId) {
        // TODO: Contar plantas asociadas al usuario
        return 0L;
    }
}