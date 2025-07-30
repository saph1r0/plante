package presentacion.controlador;

import servicios.IServicioPlanta;
import dominio.modelo.planta.Planta;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class PlantaController {

    /**
     * Default constructor
     */
    public PlantaController() {
    }

    /**
     * 
     */
    public IServicioPlanta servicioPlanta;

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
    public void guardar(Planta planta) {
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
    public void agregarCuidado(Long plantaId, String tipo, Date fecha) {
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

}