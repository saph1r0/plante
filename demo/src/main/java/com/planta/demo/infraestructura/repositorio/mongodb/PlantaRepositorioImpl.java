package com.planta.demo.infraestructura.repositorio.mongodb;

import com.planta.demo.dominio.modelo.IPlantaRepositorio;
import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.planta.RegistroPlanta;
import com.planta.demo.dominio.modelo.planta.EstadoPlanta;
import com.planta.demo.dominio.modelo.bitacora.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Repository
public class PlantaRepositorioImpl implements IPlantaRepositorio {

    @Autowired
    private PlantaMongoRepository plantaMongoRepo;

    @Autowired
    private RegistroPlantaMongoRepository registroMongoRepo;

    @Override
    public void guardar(Planta planta) {
        try {
            plantaMongoRepo.save(planta);
        } catch (Exception e) {
            throw new RuntimeException("Error guardando planta", e);
        }
    }

    @Override
    public Planta obtenerPorId(String id) {
        try {
            return plantaMongoRepo.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Planta> listarTodas() {
        try {
            return plantaMongoRepo.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre) {
        try {
            return (nombre == null || nombre.trim().isEmpty()) ? listarTodas() : plantaMongoRepo.findByNombreContaining(nombre.trim());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        try {
            return (tipo == null || tipo.trim().isEmpty()) ? listarTodas() : plantaMongoRepo.findByTipoIgnoreCase(tipo);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Planta> buscarPorDescripcion(String descripcion) {
        try {
            return (descripcion == null || descripcion.trim().isEmpty()) ? new ArrayList<>() : plantaMongoRepo.findByDescripcionContaining(descripcion);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void eliminar(String id) {
        try {
            plantaMongoRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error eliminando planta", e);
        }
    }

    @Override
    public void actualizar(Planta planta) {
        try {
            if (planta.getId() != null && plantaMongoRepo.existsById(planta.getId())) {
                plantaMongoRepo.save(planta);
            } else {
                throw new IllegalArgumentException("Planta no existe: " + planta.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando planta", e);
        }
    }

    @Override
    public void guardarRegistro(RegistroPlanta registro) {
        try {
            registroMongoRepo.save(registro);
        } catch (Exception e) {
            throw new RuntimeException("Error guardando registro", e);
        }
    }

    @Override
    public RegistroPlanta obtenerRegistroPorId(int registroId) {
        return obtenerRegistroPorId(String.valueOf(registroId));
    }

    public RegistroPlanta obtenerRegistroPorId(String registroId) {
        try {
            return registroMongoRepo.findById(registroId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RegistroPlanta> listarRegistrosPorUsuario(String usuarioId) {
        try {
            return registroMongoRepo.findByUsuarioIdOrderByFechaRegistroDesc(usuarioId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void actualizarRegistro(RegistroPlanta registro) {
        try {
            if (registro.getId() != null && registroMongoRepo.existsById(registro.getId())) {
                registroMongoRepo.save(registro);
            } else {
                throw new IllegalArgumentException("Registro no existe: " + registro.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando registro", e);
        }
    }

    @Override
    public void eliminarRegistro(int registroId) {
        eliminarRegistro(String.valueOf(registroId));
    }

    public void eliminarRegistro(String registroId) {
        try {
            registroMongoRepo.deleteById(registroId);
        } catch (Exception e) {
            throw new RuntimeException("Error eliminando registro", e);
        }
    }

    @Override
    public List<RegistroPlanta> listarRegistrosPorEstado(String estado, String usuarioId) {
        try {
            EstadoPlanta estadoEnum = EstadoPlanta.valueOf(estado.toUpperCase());
            return registroMongoRepo.findByEstadoAndUsuarioId(estadoEnum, usuarioId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Long contarRegistrosPorUsuario(String usuarioId) {
        try {
            return registroMongoRepo.countByUsuarioId(usuarioId);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public RegistroPlanta obtenerUltimoRegistro(String usuarioId) {
        try {
            return registroMongoRepo.findFirstByUsuarioIdOrderByFechaRegistroDesc(usuarioId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Bitacora> listarBitacorasPorPlanta(String plantaId) {
        return new ArrayList<>();
    }

    @Override
    public List<Bitacora> listarBitacorasPorUsuario(String usuarioId) {
        return new ArrayList<>();
    }

    @Override
    public List<Bitacora> listarBitacorasPorFecha(Date fechaInicio, Date fechaFin) {
        return new ArrayList<>();
    }

    @Override
    public Long contarPlantasPorTipo(String tipo) {
        try {
            return plantaMongoRepo.countByTipoIgnoreCase(tipo);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public List<Planta> obtenerPlantasPopulares(int limite) {
        try {
            List<Planta> todas = plantaMongoRepo.findAll();
            return todas.size() > limite ? todas.subList(0, limite) : todas;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<RegistroPlanta> obtenerRegistrosQueNecesitanAtencion(String usuarioId) {
        try {
            return registroMongoRepo.findRegistrosQueNecesitanAtencion(usuarioId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean verificarConexion() {
        try {
            plantaMongoRepo.count();
            registroMongoRepo.count();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void limpiarDatosPrueba() {
        try {
            registroMongoRepo.deleteAll();
        } catch (Exception e) {
            // Ignorar
        }
    }
}

