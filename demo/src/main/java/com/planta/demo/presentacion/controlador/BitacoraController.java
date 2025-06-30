package com.planta.demo.presentacion.controlador;

import com.planta.demo.aplicacion.interfaces.IServicioBitacora;
import com.planta.demo.dominio.modelo.bitacora.Bitacora;


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
        // TODO implement here
        return null;
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
    }

    /**
     * @param bitacoraId 
     * @param nuevaDescripcion 
     * @return
     */
    public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
        // TODO implement here
    }

    /**
     * @param bitacoraId 
     * @return
     */
    public void eliminar(Long bitacoraId) {
        // TODO implement here
    }

}