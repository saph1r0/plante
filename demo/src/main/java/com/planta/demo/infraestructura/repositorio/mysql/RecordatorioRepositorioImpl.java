package com.planta.demo.infraestructura.repositorio.mysql;

import com.planta.demo.dominio.modelo.recordatorio.Recordatorio;
import com.planta.demo.dominio.modelo.IRecordatorioRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Implementación del repositorio de Recordatorios usando MySQL.
 * Esta clase forma parte de la capa de infraestructura.
 */
@Repository
public class RecordatorioRepositorioImpl implements IRecordatorioRepositorio {

    public RecordatorioRepositorioImpl() {
        // Constructor por defecto (puede inyectarse JdbcTemplate o EntityManager aquí)
    }

    @Override
    public Recordatorio obtenerPorId(String id) {
        // TODO: Implementar consulta SQL para obtener recordatorio por ID
        return null;
    }

    @Override
    public List<Recordatorio> listarPorPlanta(String plantaId) {
        // TODO: Implementar consulta para listar recordatorios por planta
        return null;
    }

    @Override
    public void guardar(Recordatorio recordatorio) {
        // TODO: Implementar inserción o actualización del recordatorio
    }

    @Override
    public void eliminar(String id) {
        // TODO: Implementar eliminación por ID
    }

    @Override
    public List<Recordatorio> obtenerProximosPorUsuario(String usuarioId, Date fecha) {
        // TODO: Consulta con condición de fecha >= fecha y estado = ACTIVO
        return null;
    }

    @Override
    public void marcarComoCompletado(String id) {
        // TODO: Actualizar estado del recordatorio a COMPLETADO
    }

    @Override
    public List<Recordatorio> listarPendientesPorUsuario(String usuarioId) {
        // TODO: Listar recordatorios con estado pendiente para el usuario
        return null;
    }

    @Override
    public List<Recordatorio> listarPorTipo(String tipo, String usuarioId) {
        // TODO: Filtrar por tipo de cuidado y usuario
        return null;
    }
}
