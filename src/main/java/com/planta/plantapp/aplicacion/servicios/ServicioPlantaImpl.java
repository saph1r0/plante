package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para gestionar plantas.
 * Orquesta la lógica entre el dominio y la infraestructura.
 */
@Service
public class ServicioPlantaImpl implements IServicioPlanta {

    private final IPlantaRepositorio repositorioPlanta;

    @Autowired
    public ServicioPlantaImpl(IPlantaRepositorio repositorioPlanta) {
        this.repositorioPlanta = repositorioPlanta;
    }

    @Override
    public List<Planta> obtenerTodas() {
        try {
            // Usar "global" para obtener todas las plantas de MongoDB
            return repositorioPlanta.listarPorUsuario("global");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener plantas: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Planta> obtenerPorId(String id) {
        try {
            Planta planta = repositorioPlanta.obtenerPorId(id);
            return Optional.ofNullable(planta);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Planta guardar(Planta planta) {
        try {
            repositorioPlanta.guardar(planta);
            return planta;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar planta: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(String id) {
        try {
            repositorioPlanta.eliminar(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar planta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        try {
            return repositorioPlanta.buscarPorTipo(tipo);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar plantas por tipo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Planta> buscarPorUsuario(Long usuarioId) {
        try {
            return repositorioPlanta.listarPorUsuario(usuarioId.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar plantas por usuario: " + e.getMessage(), e);
        }
    }

    // Métodos adicionales que tenías en tu implementación original

    public Planta obtenerPorId(Long id) {
        return repositorioPlanta.obtenerPorId(id.toString());
    }

    public void eliminar(Long id) {
        repositorioPlanta.eliminar(id.toString());
    }

    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        try {
            Planta planta = repositorioPlanta.obtenerPorId(plantaId.toString());
            if (planta != null) {
                // Aquí se puede agregar la lógica del dominio para agregar el cuidado
                // Por ahora solo guardamos la planta
                repositorioPlanta.guardar(planta);
            } else {
                throw new IllegalArgumentException("Planta no encontrada con ID: " + plantaId);
            }
        } catch (Exception e) {
            System.err.println("Error al agregar cuidado: " + e.getMessage());
            throw e;
        }
    }

    public List<Planta> listarPorUsuario(Long usuarioId) {
        return repositorioPlanta.listarPorUsuario(usuarioId.toString());
    }

    public int contarPorEstado(String estado) {
        try {
            List<Planta> todas = obtenerTodas();
            return (int) todas.stream()
                    .filter(p -> estado.equalsIgnoreCase(p.getEstado()))
                    .count();
        } catch (Exception e) {
            System.err.println("Error al contar por estado: " + e.getMessage());
            return 0;
        }
    }

    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        try {
            return repositorioPlanta.buscarPorNombre(nombre, usuarioId);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar plantas por nombre: " + e.getMessage(), e);
        }
    }

    public void actualizarEstado(String plantaId, String estado) {
        try {
            repositorioPlanta.actualizarEstado(plantaId, estado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar estado: " + e.getMessage(), e);
        }
    }

    public Long contarPorUsuario(String usuarioId) {
        return repositorioPlanta.contarPorUsuario(usuarioId);
    }
}