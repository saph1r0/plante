package dominio.modelo;

import dominio.modelo.recordatorio.Recordatorio;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IRecordatorioRepositorio {

    /**
     * @param id 
     * @return
     */
    public Recordatorio obtenerPorId(String id);

    /**
     * @param plantaId 
     * @return
     */
    public List<Recordatorio> listarPorPlanta(String plantaId);

    /**
     * @param recordatorio 
     * @return
     */
    public void guardar(Recordatorio recordatorio);

    /**
     * @param id 
     * @return
     */
    public void eliminar(String id);

    /**
     * @param usuarioId 
     * @param fecha 
     * @return
     */
    public List<Recordatorio> obtenerProximosPorUsuario(String usuarioId, Date fecha);

    /**
     * @param id 
     * @return
     */
    public void marcarComoCompletado(String id);

    /**
     * @param usuarioId 
     * @return
     */
    public List<Recordatorio> listarPendientesPorUsuario(String usuarioId);

    /**
     * @param tipo 
     * @param usuarioId 
     * @return
     */
    public List<Recordatorio> listarPorTipo(String tipo, String usuarioId);

}