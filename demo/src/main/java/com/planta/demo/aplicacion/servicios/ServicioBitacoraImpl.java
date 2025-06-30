 //servicios;
 package com.planta.demo.aplicacion.servicios;

import com.planta.demo.dominio.modelo.IBitacoraRepositorio;
import com.planta.demo.dominio.modelo.bitacora.Bitacora;
import com.planta.demo.dominio.modelo.servicios.ServicioBitacoraDominio;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ServicioBitacoraImpl {

    /**
     * Default constructor
     */
    public ServicioBitacoraImpl() {
    }

    /**
     * 
     */
    private ServicioBitacoraDominio servicioDominio;

    /**
     * 
     */
    private IBitacoraRepositorio repositorioBitacora;

    /**
     * @param plantaId 
     * @param descripcion 
     * @return
     */
    public void registrarObservacion(Long plantaId, String descripcion) {
        // TODO implement here
    }

    /**
     * @param plantaId 
     * @return
     */
    public List<Bitacora> obtenerPorPlanta(Long plantaId) {
        // TODO implement here
        return null;
    }

    /**
     * @param desde 
     * @param hasta 
     * @return
     */
    public List<Bitacora> obtenerPorFechaRango(Date desde, Date hasta) {
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
    }

    /**
     * @param bitacoraId 
     * @return
     */
    public void eliminar(Long bitacoraId) {
        // TODO implement here
    }

    /**
     * @param plantaId 
     * @return
     */
    public byte exportarHistorial(Long plantaId) {
        // TODO implement here
        return 0;
    }

    /**
     * @param tipo 
     * @return
     */
    public List<Bitacora> buscarPorTipo(String tipo) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuarioId 
     * @return
     */
    public List<Bitacora> listarPendientesPorUsuario(Long usuarioId) {
        // TODO implement here
        return null;
    }

}