package com.planta.plantapp.infraestructura.repositorio.mysql;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Implementación del repositorio de Recordatorios usando MySQL.
 * Esta clase forma parte de la capa de infraestructura.
 */
@Repository
public class RecordatorioRepositorioImpl implements IRecordatorioRepositorio {
    private final JdbcTemplate jdbcTemplate;
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
    @Override
    public void marcarComoCompletado(String id) {
        ejecutarActualizacionEstado(id, "COMPLETADO");
    }

    @Override
    public void restaurarRecordatorio(String id) {
        ejecutarActualizacionEstado(id, "PENDIENTE");
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

    @Override
    public List<Recordatorio> obtenerActivosEntreFechas(String usuarioId, Date fechaInicio, Date fechaFin) {
        // Muestra solo los recordatorios relevantes al usuario en ese intervalo.
        String sql = "SELECT * FROM recordatorios WHERE usuario_id = ? AND estado = 'ACTIVO' AND fecha >= ? AND fecha <= ?";

        return ejecutarConsultaPorRangoDeFechas(usuarioId, fechaInicio, fechaFin, sql);
    }

    @Override
    public List<Recordatorio> obtenerProximosPorUsuario(String usuarioId, Date fechaDesde) {
        return buscarRecordatoriosActivosDesdeFecha(usuarioId, fechaDesde);
    }

    private List<Recordatorio> buscarRecordatoriosActivosDesdeFecha(String usuarioId, Date fechaDesde) {
        String sql = """
        SELECT * FROM recordatorios 
        WHERE usuario_id = ? AND estado = 'ACTIVO' AND fecha >= ?        
    """;
        return ejecutarConsulta(usuarioId, fechaDesde, sql);
    }
    }

    @Override
    public Recordatorio obtenerPorId(String id) {
        Recordatorio recordatorio = buscarPorId(id);
        if (recordatorio == null) {
            throw new RecordatorioNoEncontradoException("No se encontró el recordatorio con ID: " + id);
        }
        return recordatorio;
    }

    private Recordatorio buscarPorId(String id) {
        return null;
    }

    @Override
    public List<Recordatorio> listarPorPlanta(String plantaId) {
        String sql = "SELECT * FROM recordatorios WHERE planta_id = ?";

        return jdbcTemplate.query(sql, new Object[]{plantaId}, (rs, rowNum) -> {
            return new Recordatorio(
                    rs.getString("id"),
                    rs.getString("usuario_id"),
                    rs.getString("planta_id"),
                    rs.getString("mensaje"),
                    rs.getDate("fecha"),
                    rs.getString("estado"),
                    rs.getString("tipo")
            );
        });
    }



}
