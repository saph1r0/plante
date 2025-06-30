package dominio.modelo;

import dominio.modelo.planta.Planta;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IPlantaRepositorio {

    /**
     * @param id 
     * @return
     */
    public Planta obtenerPorId(String id);

    /**
     * @param usuarioId 
     * @return
     */
    public List<Planta> listarPorUsuario(String usuarioId);

    /**
     * @param planta 
     * @return
     */
    public void guardar(Planta planta);

    /**
     * @param id 
     * @return
     */
    public void eliminar(String id);

    /**
     * @param nombre 
     * @param usuarioId 
     * @return
     */
    public List<Planta> buscarPorNombre(String nombre, String usuarioId);

    /**
     * @param plantaId 
     * @param estadoPlanta 
     * @return
     */
    public void actualizarEstado(String plantaId, String estadoPlanta);

    /**
     * @param tipo 
     * @return
     */
    public List<Planta> buscarPorTipo(String tipo);

    /**
     * @param usuarioId 
     * @return
     */
    public Long contarPorUsuario(String usuarioId);

}