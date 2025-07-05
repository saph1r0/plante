package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.aplicacion.interfaces.IServicioRecordatorio;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class RecordatorioController {

    /**
     * Default constructor
     */
    public RecordatorioController() {
    }

    /**
     * 
     */
    private IServicioRecordatorio servicioRecordatorio;

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
    public List<Recordatorio> consultarPendientes() {
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
    public void crearRecordatorio(Planta planta, String estado, String mensaje) {
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

}