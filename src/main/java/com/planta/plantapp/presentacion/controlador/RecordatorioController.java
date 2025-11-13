package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;

import java.util.List;

/**
 * Controlador para la gestión de recordatorios.
 */
public class RecordatorioController {

    public RecordatorioController() {
        // Constructor vacío permitido
    }

    /**
     * Consulta todos los recordatorios existentes.
     *
     * @return Lista de recordatorios
     */
    public List<Recordatorio> consultarTodos() {
        throw new UnsupportedOperationException("Método consultarTodos() no implementado aún.");
    }

    /**
     * Consulta los recordatorios pendientes.
     *
     * @return Lista de recordatorios pendientes
     */
    public List<Recordatorio> consultarPendientes() {
        throw new UnsupportedOperationException("Método consultarPendientes() no implementado aún.");
    }

    /**
     * Consulta los recordatorios por usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de recordatorios asociados al usuario
     */
    public List<Recordatorio> consultarPorUsuario(Long usuarioId) {
        throw new UnsupportedOperationException("Método consultarPorUsuario() no implementado aún.");
    }

    /**
     * Crea un nuevo recordatorio para una planta.
     *
     * @param planta Planta relacionada
     * @param estado Estado del recordatorio
     * @param mensaje Mensaje del recordatorio
     */
    public void crearRecordatorio(Planta planta, String estado, String mensaje) {
        throw new UnsupportedOperationException("Método crearRecordatorio() no implementado aún.");
    }

    /**
     * Marca un recordatorio como realizado.
     *
     * @param id ID del recordatorio
     */
    public void marcarComoRealizado(Long id) {
        throw new UnsupportedOperationException("Método marcarComoRealizado() no implementado aún.");
    }

    /**
     * Elimina un recordatorio.
     *
     * @param id ID del recordatorio
     */
    public void eliminarRecordatorio(Long id) {
        throw new UnsupportedOperationException("Método eliminarRecordatorio() no implementado aún.");
    }
}
