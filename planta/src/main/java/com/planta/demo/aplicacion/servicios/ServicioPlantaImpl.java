package servicios;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ServicioPlantaImpl {

    /**
     * Default constructor
     */
    public ServicioPlantaImpl() {
    }

    /**
     * 
     */
    private void repositorioPlanta;

    /**
     * 
     */
    private void servicioDominio;

    /**
     * @return
     */
    public List<Planta> obtenerTodas() {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Planta obtenerPorId(Long id) {
        // TODO implement here
        return null;
    }

    /**
     * @param planta 
     * @return
     */
    public void guardar(void planta) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public void eliminar(Long id) {
        // TODO implement here
        return null;
    }

    /**
     * @param plantaId 
     * @param tipo 
     * @param fecha 
     * @return
     */
    public void agregarCuidado(Long plantaId, void tipo, Date fecha) {
        // TODO implement here
        return null;
    }

    /**
     * @param plantaId 
     * @param realizado 
     * @return
     */
    public void marcarEventoRealizado(Long plantaId, bool realizado) {
        // TODO implement here
        return null;
    }

    /**
     * @param tipo 
     * @return
     */
    public List<Planta> buscarPorTipo(String tipo) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuarioId 
     * @return
     */
    public List<Planta> listarPorUsuario(Long usuarioId) {
        // TODO implement here
        return null;
    }

    /**
     * @param estado 
     * @return
     */
    public int contarPorEstado(String estado) {
        // TODO implement here
        return 0;
    }

}