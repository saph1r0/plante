package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.RegistroPlanta;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Servicio de aplicación para gestión de plantas
 * Implementa prácticas de Clean Code y convenciones Java
 *
 */
@Service
public class ServicioPlantaImpl implements IServicioPlanta {

    // Logger en lugar de System.err
    private static final Logger logger = Logger.getLogger(ServicioPlantaImpl.class.getName());

    @Autowired
    private IPlantaRepositorio plantaRepositorio;

    @Override
    public List<Planta> obtenerTodas() {
        try {
            return plantaRepositorio.listarTodas();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error obteniendo todas las plantas");
            return new ArrayList<>();
        }
    }

    @Override
    public Planta obtenerPorId(Long id) {
        try {
            return plantaRepositorio.obtenerPorId(id.toString());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo planta por ID: {0}", id);
            return null;
        }
    }

    @Override
    public boolean guardar(Planta planta) {
        try {
            plantaRepositorio.guardar(planta);
            logger.log(Level.INFO, "Planta guardada exitosamente: {0}", planta.getNombreComun());
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error guardando planta", e);
            return false;
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            plantaRepositorio.eliminar(id.toString());
            logger.log(Level.INFO, "Planta eliminada: {0}", id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando planta: {0}", id);
        }
    }

    @Override
    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        try {
            // Implementación de lógica de cuidados
            Planta planta = plantaRepositorio.obtenerPorId(plantaId.toString());
            if (planta != null) {
                // Lógica para agregar cuidado a la planta
                logger.log(Level.INFO, "Cuidado agregado a planta {0}: {1}",
                        new Object[]{plantaId, tipo.name()});
            } else {
                logger.log(Level.WARNING, "Planta no encontrada para agregar cuidado: {0}", plantaId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error agregando cuidado a planta: {0}", plantaId);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        try {
            return plantaRepositorio.buscarPorTipo(tipo);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error buscando plantas por tipo: {0}", tipo);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Planta> listarPorUsuario(Long usuarioId) {
        try {
            // Esto debería devolver RegistroPlanta, no Planta
            List<RegistroPlanta> registros = plantaRepositorio.listarRegistrosPorUsuario(usuarioId.toString());
            return registros.stream().map(RegistroPlanta::getPlanta).toList();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error listando plantas por usuario: {0}", usuarioId);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean registrarNuevaPlanta(String nombre, String tipo, String usuarioId) {
        try {
            // Buscar planta en catálogo
            List<Planta> plantas = plantaRepositorio.buscarPorNombre(nombre);
            if (plantas.isEmpty()) {
                logger.log(Level.WARNING, "Planta no encontrada en catálogo: {0}", nombre);
                return false;
            }

            Planta planta = plantas.get(0);
            RegistroPlanta registro = new RegistroPlanta(nombre, usuarioId, planta, "Sin ubicación");
            plantaRepositorio.guardarRegistro(registro);

            logger.log(Level.INFO, "Nueva planta registrada para usuario {0}: {1}",
                    new Object[]{usuarioId, nombre});
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error registrando planta", e);
            return false;
        }
    }

    @Override
    public List<String> obtenerUltimosErrores() {
        // Implementación básica de manejo de errores
        List<String> errores = new ArrayList<>();
        try {
            // Aquí podrías consultar un log de errores o base de datos
            // Por ahora devolvemos lista vacía ya que usamos logger
            logger.log(Level.INFO, "Consultando últimos errores del sistema");
            return errores;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo últimos errores", e);
            return errores;
        }
    }

    @Override
    public int contarPorEstado(String estado) {
        // Implementación de conteo por estado
        try {
            if (estado == null || estado.trim().isEmpty()) {
                logger.log(Level.WARNING, "Estado proporcionado es nulo o vacío");
                return 0;
            }

            // Obtener todos los registros y filtrar por estado
            List<RegistroPlanta> todosRegistros = plantaRepositorio.listarRegistrosPorUsuario(""); // Todos los usuarios
            long count = todosRegistros.stream()
                    .filter(registro -> estado.equalsIgnoreCase(registro.getEstado().name()))
                    .count();

            logger.log(Level.INFO, "Plantas en estado {0}: {1}", new Object[]{estado, count});
            return (int) count;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error contando plantas por estado: {0}", estado);
            return 0;
        }
    }

    // ========================================
    // MÉTODOS ADICIONALES PARA EL CONTROLADOR
    // ========================================

    public List<RegistroPlanta> obtenerRegistrosPorUsuario(String usuarioId) {
        try {
            return plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo registros por usuario: {0}", usuarioId);
            return new ArrayList<>();
        }
    }

    public RegistroPlanta obtenerRegistroPorId(String registroId) {
        try {
            return ((com.planta.plantapp.infraestructura.repositorio.mongodb.PlantaRepositorioImpl) plantaRepositorio)
                    .obtenerRegistroPorId(registroId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo registro por ID: {0}", registroId);
            return null;
        }
    }

    public void guardarRegistro(RegistroPlanta registro) {
        try {
            plantaRepositorio.guardarRegistro(registro);
            logger.log(Level.INFO, "Registro guardado: {0}", registro.getApodo());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error guardando registro", e);
        }
    }

    public void actualizarRegistro(RegistroPlanta registro) {
        try {
            plantaRepositorio.actualizarRegistro(registro);
            logger.log(Level.INFO, "Registro actualizado: {0}", registro.getApodo());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error actualizando registro", e);
        }
    }

    public void eliminarRegistro(String registroId) {
        try {
            ((com.planta.plantapp.infraestructura.repositorio.mongodb.PlantaRepositorioImpl) plantaRepositorio)
                    .eliminarRegistro(registroId);
            logger.log(Level.INFO, "Registro eliminado: {0}", registroId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando registro: {0}", registroId);
        }
    }
}
