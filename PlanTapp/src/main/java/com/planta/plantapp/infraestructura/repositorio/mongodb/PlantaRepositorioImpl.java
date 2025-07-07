package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.infraestructura.documento.PlantaDocumento;
import com.planta.plantapp.infraestructura.mapper.PlantaMapper;
import com.planta.plantapp.infraestructura.repositorio.mongodb.mongo.PlantaMongoRepositorio;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de IPlantaRepositorio usando MongoDB (infraestructura).
 */
@Repository
public class PlantaRepositorioImpl implements IPlantaRepositorio {
    private final PlantaMongoRepositorio mongoRepositorio;

    public PlantaRepositorioImpl() {
        // Constructor por defecto (inyecciones si es necesario)
    }
    public PlantaRepositorioImpl(PlantaMongoRepositorio mongoRepositorio) {
        this.mongoRepositorio = mongoRepositorio;
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
    public Planta guardar(Planta planta) {
        PlantaDocumento documento = PlantaMapper.aDocumento(planta);
        PlantaDocumento guardado = mongoRepositorio.save(documento);
        return PlantaMapper.aDominio(guardado);
    }

    @Override
    public void eliminar(String id) {
        mongoRepositorio.deleteById(id);
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre) {
        // MongoRepository no tiene esto por defecto, pero puedes extenderlo
        List<PlantaDocumento> documentos = mongoRepositorio.findAll(); // luego optimizaremos con búsqueda personalizada
        return documentos.stream()
                .filter(p -> p.getNombreComun().toLowerCase().contains(nombre.toLowerCase()))
                .map(PlantaMapper::aDominio)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Planta> buscarPorId(String id) {
        return mongoRepositorio.findById(id).map(PlantaMapper::aDominio);
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
