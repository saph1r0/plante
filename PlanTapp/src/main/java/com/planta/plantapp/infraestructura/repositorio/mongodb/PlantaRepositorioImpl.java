package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.RegistroPlanta;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Implementación del repositorio de plantas usando MongoDB
 * Aplicando prácticas de Clean Code y convenciones Java

 */
@Repository
public class PlantaRepositorioImpl implements IPlantaRepositorio {

    // Logger en lugar de System.err
    private static final Logger logger = Logger.getLogger(PlantaRepositorioImpl.class.getName());

    @Autowired
    private PlantaMongoRepository plantaMongoRepo;

    @Autowired
    private RegistroPlantaMongoRepository registroMongoRepo;

    // ========================================
    // EXCEPCIONES PERSONALIZADAS
    // ========================================

    /**
     * Excepción específica para errores de repositorio de plantas
     */
    public static class PlantaRepositorioException extends RuntimeException {
        public PlantaRepositorioException(String message) {
            super(message);
        }

        public PlantaRepositorioException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Excepción específica para plantas no encontradas
     */
    public static class PlantaNoEncontradaException extends PlantaRepositorioException {
        public PlantaNoEncontradaException(String plantaId) {
            super("Planta no encontrada con ID: " + plantaId);
        }
    }

    /**
     * Excepción específica para registros no encontrados
     */
    public static class RegistroNoEncontradoException extends PlantaRepositorioException {
        public RegistroNoEncontradoException(String registroId) {
            super("Registro no encontrado con ID: " + registroId);
        }
    }

    // ========================================
    // IMPLEMENTACIÓN DE MÉTODOS BÁSICOS
    // ========================================

    @Override
    public List<Planta> listarTodas() {
        try {
            return plantaMongoRepo.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error listando todas las plantas");
            throw new PlantaRepositorioException("Error al listar plantas del catálogo");
        }
    }

    @Override
    public Planta obtenerPorId(String id) {
        try {
            return plantaMongoRepo.findById(id)
                    .orElseThrow(() -> new PlantaNoEncontradaException(id));
        } catch (PlantaNoEncontradaException e) {
            throw e; // Re-lanzar excepciones específicas
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error obteniendo planta por ID: {0}", id);
            throw new PlantaRepositorioException("Error al obtener planta con ID: " + id);
        }
    }

    @Override
    public void guardar(Planta planta) {
        try {
            if (planta == null) {
                throw new PlantaRepositorioException("No se puede guardar una planta nula");
            }
            plantaMongoRepo.save(planta);
            logger.log(Level.INFO, "Planta guardada exitosamente: {0}", planta.getNombreComun());
        } catch (PlantaRepositorioException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error guardando planta: {0}", planta.getNombreComun());
            throw new PlantaRepositorioException("Error al guardar planta: " + planta.getNombreComun());
        }
    }

    @Override
    public void eliminar(String id) {
        try {
            if (!plantaMongoRepo.existsById(id)) {
                throw new PlantaNoEncontradaException(id);
            }
            plantaMongoRepo.deleteById(id);
            logger.log(Level.INFO, "Planta eliminada: {0}", id);
        } catch (PlantaNoEncontradaException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando planta: {0}", id);
            throw new PlantaRepositorioException("Error al eliminar planta con ID: " + id);
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return plantaMongoRepo.findByNombreComunContainingIgnoreCase(nombre.trim());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error buscando plantas por nombre: {0}", nombre);
            throw new PlantaRepositorioException("Error al buscar plantas por nombre: " + nombre);
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        try {
            if (tipo == null || tipo.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return plantaMongoRepo.findByTipoIgnoreCase(tipo.trim());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error buscando plantas por tipo: {0}", tipo);
            throw new PlantaRepositorioException("Error al buscar plantas por tipo: " + tipo);
        }
    }

    // ========================================
    // MÉTODOS DE REGISTRO DE PLANTAS
    // ========================================

    @Override
    public void guardarRegistro(RegistroPlanta registro) {
        try {
            if (registro == null) {
                throw new PlantaRepositorioException("No se puede guardar un registro nulo");
            }
            registroMongoRepo.save(registro);
            logger.log(Level.INFO, "Registro guardado: {0}", registro.getApodo());
        } catch (PlantaRepositorioException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error guardando registro: {0}", registro.getApodo());
            throw new PlantaRepositorioException("Error al guardar registro: " + registro.getApodo());
        }
    }

    @Override
    public void actualizarRegistro(RegistroPlanta registro) {
        try {
            if (registro == null) {
                throw new PlantaRepositorioException("No se puede actualizar un registro nulo");
            }
            if (!registroMongoRepo.existsById(registro.getId())) {
                throw new RegistroNoEncontradoException(registro.getId());
            }
            registroMongoRepo.save(registro);
            logger.log(Level.INFO, "Registro actualizado: {0}", registro.getApodo());
        } catch (PlantaRepositorioException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error actualizando registro: {0}", registro.getId());
            throw new PlantaRepositorioException("Error al actualizar registro: " + registro.getId());
        }
    }

    @Override
    public List<RegistroPlanta> listarRegistrosPorUsuario(String usuarioId) {
        try {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return registroMongoRepo.findByUsuarioId(usuarioId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error listando registros por usuario: {0}", usuarioId);
            throw new PlantaRepositorioException("Error al listar registros del usuario: " + usuarioId);
        }
    }

    @Override
    public Long contarRegistrosPorUsuario(String usuarioId) {
        try {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                return 0L;
            }
            return registroMongoRepo.countByUsuarioId(usuarioId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error contando registros por usuario: {0}", usuarioId);
            throw new PlantaRepositorioException("Error al contar registros del usuario: " + usuarioId);
        }
    }

    @Override
    public RegistroPlanta obtenerUltimoRegistro(String usuarioId) {
        try {
            return registroMongoRepo.findFirstByUsuarioIdOrderByFechaRegistroDesc(usuarioId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo último registro: {0}", usuarioId);
            throw new PlantaRepositorioException("Error al obtener último registro del usuario: " + usuarioId);
        }
    }

    // ========================================
    // MÉTODOS ADICIONALES
    // ========================================

    /**
     * Obtiene un registro por su ID
     */
    public RegistroPlanta obtenerRegistroPorId(String registroId) {
        try {
            return registroMongoRepo.findById(registroId)
                    .orElseThrow(() -> new RegistroNoEncontradoException(registroId));
        } catch (RegistroNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error obteniendo registro por ID: {0}", registroId);
            throw new PlantaRepositorioException("Error al obtener registro con ID: " + registroId);
        }
    }

    /**
     * Elimina un registro por su ID
     */
    public void eliminarRegistro(String registroId) {
        try {
            if (!registroMongoRepo.existsById(registroId)) {
                throw new RegistroNoEncontradoException(registroId);
            }
            registroMongoRepo.deleteById(registroId);
            logger.log(Level.INFO, "Registro eliminado: {0}", registroId);
        } catch (RegistroNoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando registro: {0}", registroId);
            throw new PlantaRepositorioException("Error al eliminar registro con ID: " + registroId);
        }
    }

    // ========================================
    // MÉTODOS DE BITÁCORAS (implementación básica)
    // ========================================

    @Override
    public List<Bitacora> listarBitacorasPorPlanta(String plantaId) {
        // Implementación básica - retorna lista vacía por ahora
        logger.log(Level.INFO, "Bitácoras no implementadas aún para planta: {0}", plantaId);
        return new ArrayList<>();
    }

    @Override
    public List<Bitacora> listarBitacorasPorUsuario(String usuarioId) {
        // Implementación básica - retorna lista vacía por ahora
        logger.log(Level.INFO, "Bitácoras no implementadas aún para usuario: {0}", usuarioId);
        return new ArrayList<>();
    }

    @Override
    public List<Bitacora> listarBitacorasPorFecha(Date fechaInicio, Date fechaFin) {
        // Implementación básica - retorna lista vacía por ahora
        logger.info("Bitácoras por fecha no implementadas aún");
        return new ArrayList<>();
    }

    // ========================================
    // MÉTODOS DE ESTADÍSTICAS
    // ========================================

    @Override
    public Long contarPlantasPorTipo(String tipo) {
        try {
            if (tipo == null || tipo.trim().isEmpty()) {
                return 0L;
            }
            return plantaMongoRepo.countByTipoIgnoreCase(tipo);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error contando por tipo: {0}", tipo);
            throw new PlantaRepositorioException("Error al contar plantas por tipo: " + tipo);
        }
    }

    @Override
    public List<Planta> obtenerPlantasPopulares(int limite) {
        try {
            // Implementación básica - obtiene las primeras plantas del catálogo
            List<Planta> todas = plantaMongoRepo.findAll();
            return todas.size() > limite ? todas.subList(0, limite) : todas;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo plantas populares");
            throw new PlantaRepositorioException("Error al obtener plantas populares");
        }
    }

    /**
     * Obtiene registros que necesitan atención urgente
     */
    public List<RegistroPlanta> obtenerRegistrosQueNecesitanAtencion(String usuarioId) {
        try {
            return registroMongoRepo.findRegistrosQueNecesitanAtencion(usuarioId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo registros que necesitan atención: {0}", usuarioId);
            throw new PlantaRepositorioException("Error al obtener registros que necesitan atención");
        }
    }

    /**
     * Método de diagnóstico para verificar conectividad
     */
    public boolean verificarConexion() {
        try {
            long totalPlantas = plantaMongoRepo.count();
            long totalRegistros = registroMongoRepo.count();
            logger.log(Level.INFO, "Diagnóstico MongoDB - Plantas: {0}, Registros: {1}",
                    new Object[]{totalPlantas, totalRegistros});
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error verificando conexión MongoDB");
            throw new PlantaRepositorioException("Error de conectividad con MongoDB");
        }
    }

    /**
     * Limpia datos de prueba (solo para desarrollo)
     */
    public void limpiarDatosPrueba() {
        try {
            logger.info("Limpiando datos de prueba...");
            registroMongoRepo.deleteAll();
            logger.info("Registros de prueba eliminados");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error limpiando datos");
            throw new PlantaRepositorioException("Error al limpiar datos de prueba");
        }
    }
    @Override
	public List<Planta> buscarPorDescripcion(String descripcion) {
	    try {
		if (descripcion == null || descripcion.trim().isEmpty()) {
		    return new ArrayList<>();
		}
		return plantaMongoRepo.findByDescripcionContaining(descripcion.trim());
	    } catch (Exception e) {
		logger.log(Level.WARNING, "Error buscando plantas por descripción: {0}", descripcion);
		throw new PlantaRepositorioException("Error al buscar plantas por descripción: " + descripcion);
	    }
	}

	@Override
	public void eliminarRegistro(int registroId) {
	    try {
		String registroIdStr = String.valueOf(registroId);
		if (!registroMongoRepo.existsById(registroIdStr)) {
		    throw new RegistroNoEncontradoException(registroIdStr);
		}
		registroMongoRepo.deleteById(registroIdStr);
		logger.log(Level.INFO, "Registro eliminado: {0}", registroId);
	    } catch (RegistroNoEncontradoException e) {
		throw e;
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error eliminando registro: {0}", registroId);
		throw new PlantaRepositorioException("Error al eliminar registro con ID: " + registroId);
	    }
	}

	@Override
	public List<RegistroPlanta> listarRegistrosPorEstado(String estado, String usuarioId) {
	    try {
		if (estado == null || usuarioId == null) {
		    return new ArrayList<>();
		}
		EstadoPlanta estadoEnum = EstadoPlanta.valueOf(estado.toUpperCase());
		return registroMongoRepo.findByEstadoAndUsuarioId(estadoEnum, usuarioId);
	    } catch (Exception e) {
		logger.log(Level.WARNING, "Error listando registros por estado: {0}", estado);
		return new ArrayList<>();
	    }
	}
}
