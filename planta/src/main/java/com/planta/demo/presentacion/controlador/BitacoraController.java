package presentacion.controlador;

import servicios.IServicioBitacora;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class BitacoraController {

    /**
     * Default constructor
     */
    public BitacoraController() {
        this.bitacoraService = new BitacoraService();
    }

    /**
     * 
     */
    public IServicioBitacora servicioBitacora;

    /**
     * @param plantaId 
     * @return
     */
    public List<Bitacora> obtenerPorPlanta(Long plantaId) {
        // Ejemplo: obtienes todos y filtras por plantaId
        List<Bitacora> todas = bitacoraRepository.findAll();
        List<Bitacora> filtradas = new ArrayList<>();

        for (Bitacora b : todas) {
            if (b.getPlantaId().equals(plantaId)) {
                filtradas.add(b);
            }
        }

        return filtradas;
    }

    /**
     * @param usuarioId 
     * @return
     */
    public List<Bitacora> obtenerPorUsuario(Long usuarioId) {
        // TODO implement here
        return null;
    }

    /**
     * @param plantaId 
     * @param descripcion 
     * @return
     */
    public void registrarObservacion(Long plantaId, String descripcion) {
        // TODO implement here
        return null;
    }

    /**
     * @param bitacoraId 
     * @param nuevaDescripcion 
     * @return
     */
    public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
        // TODO implement here
        return null;
    }

    /**
     * @param bitacoraId 
     * @return
     */
    public void eliminar(Long bitacoraId) {
        // TODO implement here
        return null;
    }

}