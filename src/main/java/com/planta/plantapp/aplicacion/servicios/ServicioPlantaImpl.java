package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.excepciones.PlantaNotFoundException;
import com.planta.plantapp.aplicacion.excepciones.PlantaServiceException;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class ServicioPlantaImpl implements IServicioPlanta {

    private static final Logger logger = LoggerFactory.getLogger(ServicioPlantaImpl.class);
    private final IPlantaRepositorio repositorioPlanta;

    @Autowired
    public ServicioPlantaImpl(IPlantaRepositorio repositorioPlanta) {
        this.repositorioPlanta = repositorioPlanta;
    }

    @Override
    public List<Planta> obtenerTodas() {
        logger.info("Obteniendo todas las plantas");

        try {
            return repositorioPlanta.listarPorUsuario("global");
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Planta> obtenerPorId(String id) {
        logger.info("Buscando planta con ID {}", id);

        try {
            return Optional.ofNullable(repositorioPlanta.obtenerPorId(id));
        } catch (Exception e) {
            throw new PlantaServiceException("Error al buscar planta con ID: " + id, e);
        }
    }

    @Override
    public Planta guardar(Planta planta) {
        logger.info("Guardando planta: {}", planta.getNombreComun());

        try {
            repositorioPlanta.guardar(planta);
            return planta;
        } catch (Exception e) {
            throw new PlantaServiceException("Error al guardar planta", e);
        }
    }

    @Override
    public void eliminar(String id) {
        logger.info("Eliminando planta con ID {}", id);

        try {
            repositorioPlanta.eliminar(id);
        } catch (Exception e) {
            throw new PlantaServiceException("Error al eliminar planta", e);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        logger.info("Buscando plantas del tipo {}", tipo);

        try {
            return repositorioPlanta.buscarPorTipo(tipo);
        } catch (Exception e) {
            throw new PlantaServiceException("Error al buscar plantas por tipo", e);
        }
    }

    @Override
    public List<Planta> buscarPorUsuario(Long usuarioId) {
        logger.info("Buscando plantas del usuario {}", usuarioId);

        try {
            return repositorioPlanta.listarPorUsuario(usuarioId.toString());
        } catch (Exception e) {
            throw new PlantaServiceException("Error al buscar plantas por usuario", e);
        }
    }

    public Planta obtenerPorId(Long id) {
        return obtenerPorId(id.toString())
                .orElseThrow(() -> new PlantaNotFoundException("Planta no encontrada con ID " + id));
    }

    public void eliminar(Long id) {
        eliminar(id.toString());
    }

    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuencia, String notas) {
        try {
            Planta planta = repositorioPlanta.obtenerPorId(plantaId.toString());

            if (planta == null) {
                throw new PlantaNotFoundException("Planta no encontrada con ID " + plantaId);
            }
            Cuidado cuidado = new Cuidado(tipo, tipo.getDescripcion(), frecuencia);
            cuidado.setNotas(notas);

            planta.agregarCuidado(cuidado);

            repositorioPlanta.guardar(planta);

        } catch (PlantaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PlantaServiceException("Error al agregar cuidado", e);
        }
    }

    public List<Planta> listarPorUsuario(Long usuarioId) {
        return buscarPorUsuario(usuarioId);
    }

    public int contarPorEstado(String estado) {
        logger.info("Contando plantas con estado {}", estado);

        try {
            return (int) obtenerTodas().stream()
                    .filter(p -> estado.equalsIgnoreCase(p.getEstado()))
                    .count();
        } catch (PlantaNotFoundException e) {
            throw e;
        } catch (Exception e){
            throw new PlantaServiceException("Error al contar por estado", e);
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        logger.info("Buscando plantas por nombre {}", nombre);

        try {
            return repositorioPlanta.buscarPorNombre(nombre, usuarioId);
        } catch (Exception e) {
            throw new PlantaServiceException("Error al buscar por nombre", e);
        }
    }

    public void actualizarEstado(String plantaId, String estado) {
        logger.info("Actualizando estado de planta {}", plantaId);

        try {
            repositorioPlanta.actualizarEstado(plantaId, estado);
        } catch (Exception e) {
            throw new PlantaServiceException("Error al actualizar estado", e);
        }
    }

    public Long contarPorUsuario(String usuarioId) {
        return repositorioPlanta.contarPorUsuario(usuarioId);
    }
}
