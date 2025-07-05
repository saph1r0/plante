package com.planta.plantapp.aplicacion.servicios;

import java.io.*;
import java.util.*;

import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioRecordatorioDominio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;


/**
 * 
 */
public class ServicioRecordatorioImpl {

    /**
     * Default constructor
     */
    public ServicioRecordatorioImpl() {
    }

    /**
     * 
     */
    private ServicioRecordatorioDominio servicioDominio;

    /**
     * 
     */
    private IRecordatorioRepositorio repositorioRecordatorio;

    /**
     * @return
     */
    public List<Recordatorio> consultarTodos() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    /*public List<RecordatorioPendientes> consultarPendientes() {
        // TODO implement here
        return null;
    }*/

    /**
     * @param plantaId 
     * @return
     */
    public List<Recordatorio> consultarPorPlanta(Long plantaId) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuarioId 
     * @return
     */
    public List<Recordatorio> consultarPorUsuario(Long usuarioId) {
        // TODO implement here
        return null;
    }

    /**
     * @param planta 
     * @param estado 
     * @param mensaje 
     * @return
     */
    public void crearRecordatorio(Planta planta, EstadoRecordatorio estado, String mensaje) {
    // TODO implement here
    }

    /**
     * @param id 
     * @param nuevoMensaje 
     * @return
     */
    public void editarRecordatorio(Long id, String nuevoMensaje) {
        // TODO implement here
    }

    /**
     * @param id 
     * @return
     */
    public void marcarComoRealizado(Long id) {
        // TODO implement here
    }

    /**
     * @param id 
     * @return
     */
    public void eliminarRecordatorio(Long id) {
        // TODO implement here
    }

    /**
     * @param fecha 
     * @return
     */
    public List<Recordatorio> consultarPorFecha(Date fecha) {
        // TODO implement here
        return null;
    }

    /**
     * @param estado 
     * @return
     */
    public List<Recordatorio> consultarPorEstado(EstadoRecordatorio estado) {
        // TODO implement here
        return null;
    }

}