package servicios;

import java.io.*;
import java.util.*;

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
    private void servicioDominio;

    /**
     * 
     */
    private void repositorioRecordatorio;

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
    public List<RecordatorioPendientes> consultarPendientes() {
        // TODO implement here
        return null;
    }

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
    public void crearRecordatorio(void planta, void estado, String mensaje) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @param nuevoMensaje 
     * @return
     */
    public void editarRecordatorio(Long id, String nuevoMensaje) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public void marcarComoRealizado(Long id) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public void eliminarRecordatorio(Long id) {
        // TODO implement here
        return null;
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
    public List<Recordatorio> consultarPorEstado(void estado) {
        // TODO implement here
        return null;
    }

}