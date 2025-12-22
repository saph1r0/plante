package com.planta.plantapp.infraestructura.repositorio.mysql;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Collections;

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
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public List<Recordatorio> listarPorPlanta(String plantaId) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public void guardar(Recordatorio recordatorio) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public void eliminar(String id) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public List<Recordatorio> obtenerProximosPorUsuario(String usuarioId, Date fecha) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public void marcarComoCompletado(String id) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public List<Recordatorio> listarPendientesPorUsuario(String usuarioId) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public List<Recordatorio> listarPorTipo(String tipo, String usuarioId) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    @Override
    public List<Recordatorio> listarTodos() {
        // TODO: Implementar consulta a base de datos
        return Collections.emptyList();
    }

    @Override
    public List<Recordatorio> listarPorEstado(EstadoRecordatorio estado) {
        // TODO: Implementar consulta a base de datos filtrando por estado
        return Collections.emptyList();
    }
}
