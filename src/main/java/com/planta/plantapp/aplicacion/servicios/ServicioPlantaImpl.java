package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para gestionar plantas.
 * Orquesta la lógica entre el dominio y la infraestructura.
 */
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
        logger.info("🌱 Servicio: Obteniendo todas las plantas...");
        try {
            List<Planta> plantas = repositorioPlanta.listarPorUsuario("global");
            logger.info("✅ {} plantas obtenidas", plantas.size());
            return plantas;
        } catch (Exception e) {
            logger.error("❌ Error en servicio al obtener plantas: {}", e.getMessage(), e);
            throw new RuntimeException("Error al obtener plantas", e);
        }
    }

    @Override
    public Optional<Planta> obtenerPorId(String id) {
        logger.info("🔍 Buscando planta con ID: {}", id);
        try {
            Planta planta = repositorioPlanta.obtenerPorId(id);
            return Optional.ofNullable(planta);
        } catch (Exception e) {
            logger.error("❌ Error al buscar planta: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Planta guardar(Planta planta) {
        logger.info("💾 Guardando planta: {}", planta.getNombreComun());
        try {
            repositorioPlanta.guardar(planta);
            return planta;
        } catch (Exception e) {
            logger.error("❌ Error al guardar planta: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar planta", e);
        }
    }

    @Override
    public void eliminar(String id) {
        logger.info("🗑️ Eliminando planta con ID: {}", id);
        try {
            repositorioPlanta.eliminar(id);
        } catch (Exception e) {
            logger.error("❌ Error al eliminar planta: {}", e.getMessage(), e);
            throw new RuntimeException("Error al eliminar planta", e);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        logger.info("🔍 Buscando plantas de tipo: {}", tipo);
        try {
            return repositorioPlanta.buscarPorTipo(tipo);
        } catch (Exception e) {
            logger.error("❌ Error al buscar por tipo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar plantas por tipo", e);
        }
    }

    @Override
    public List<Planta> buscarPorUsuario(Long usuarioId) {
        logger.info("👤 Buscando plantas del usuario: {}", usuarioId);
        try {
            return repositorioPlanta.listarPorUsuario(usuarioId.toString());
        } catch (Exception e) {
            logger.error("❌ Error al buscar por usuario: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar plantas por usuario", e);
        }
    }

    // Métodos adicionales que ya tenías (NO SE MODIFICA LA LÓGICA)

    public Planta obtenerPorId(Long id) {
        return repositorioPlanta.obtenerPorId(id.toString());
    }

    public void eliminar(Long id) {
        repositorioPlanta.eliminar(id.toString());
    }

    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        logger.info("🌿 Agregando cuidado a planta {}", plantaId);
        try {
            Planta planta = repositorioPlanta.obtenerPorId(plantaId.toString());
            if (planta != null) {
                repositorioPlanta.guardar(planta);
                logger.info("✅ Cuidado agregado");
            } else {
                throw new IllegalArgumentException("Planta no encontrada con ID: " + plantaId);
            }
        } catch (Exception e) {
            logger.error("❌ Error al agregar cuidado: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Planta> listarPorUsuario(Long usuarioId) {
        return repositorioPlanta.listarPorUsuario(usuarioId.toString());
    }

    public int contarPorEstado(String estado) {
        logger.info("📊 Contando plantas con estado: {}", estado);
        try {
            List<Planta> todas = obtenerTodas();
            return (int) todas.stream()
                    .filter(p -> estado.equalsIgnoreCase(p.getEstado()))
                    .count();
        } catch (Exception e) {
            logger.error("❌ Error al contar por estado: {}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre, String usuarioId) {
        logger.info("🔍 Buscando plantas por nombre: {}", nombre);
        try {
            return repositorioPlanta.buscarPorNombre(nombre, usuarioId);
        } catch (Exception e) {
            logger.error("❌ Error al buscar por nombre: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar plantas por nombre", e);
        }
    }

    public void actualizarEstado(String plantaId, String estado) {
        logger.info("🔄 Actualizando estado de planta {}", plantaId);
        try {
            repositorioPlanta.actualizarEstado(plantaId, estado);
        } catch (Exception e) {
            logger.error("❌ Error al actualizar estado: {}", e.getMessage(), e);
            throw new RuntimeException("Error al actualizar estado", e);
        }
    }

    public Long contarPorUsuario(String usuarioId) {
        return repositorioPlanta.contarPorUsuario(usuarioId);
    }
}
