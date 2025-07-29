package com.planta.demo.presentacion.controlador;

import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.recordatorio.Recordatorio;
import com.planta.demo.aplicacion.interfaces.IServicioRecordatorio;

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