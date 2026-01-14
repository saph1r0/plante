package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;

import java.util.Date;
import java.util.List;

/**
 * Implementación del servicio de recordatorios de aplicación.
 */
public class ServicioRecordatorioImpl {

    public ServicioRecordatorioImpl() {
        // Constructor por defecto
    }


    /**
     * Consulta todos los recordatorios existentes.
     *
     * @return Lista de recordatorios.
     */
    public List<Recordatorio> consultarTodos() {
        throw new UnsupportedOperationException("Método consultarTodos() no implementado.");
    }

    /**
     * Consulta los recordatorios pendientes.
     *
     * Este método no está implementado en esta versión.
     */

    /**
     * Consulta los recordatorios de una planta específica.
     *
     * @param plantaId ID de la planta.
     * @return Lista de recordatorios asociados a la planta.
     */
    public List<Recordatorio> consultarPorPlanta(Long plantaId) {
        throw new UnsupportedOperationException("Método consultarPorPlanta() no implementado.");
    }

    /**
     * Consulta los recordatorios asociados a un usuario.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de recordatorios del usuario.
     */
    public List<Recordatorio> consultarPorUsuario(Long usuarioId) {
        throw new UnsupportedOperationException("Método consultarPorUsuario() no implementado.");
    }

    /**
     * Crea un nuevo recordatorio para una planta.
     *
     * @param planta Planta asociada.
     * @param estado Estado del recordatorio.
     * @param mensaje Mensaje del recordatorio.
     */
    public void crearRecordatorio(Planta planta, EstadoRecordatorio estado, String mensaje) {
        throw new UnsupportedOperationException("Método crearRecordatorio() no implementado.");
    }

    /**
     * Edita el mensaje de un recordatorio existente.
     *
     * @param id ID del recordatorio.
     * @param nuevoMensaje Nuevo mensaje a actualizar.
     */
    public void editarRecordatorio(Long id, String nuevoMensaje) {
        throw new UnsupportedOperationException("Método editarRecordatorio() no implementado.");
    }

    /**
     * Marca un recordatorio como realizado.
     *
     * @param id ID del recordatorio.
     */
    public void marcarComoRealizado(Long id) {
        throw new UnsupportedOperationException("Método marcarComoRealizado() no implementado.");
    }

    /**
     * Elimina un recordatorio por su ID.
     *
     * @param id ID del recordatorio.
     */
    public void eliminarRecordatorio(Long id) {
        throw new UnsupportedOperationException("Método eliminarRecordatorio() no implementado.");
    }

    /**
     * Consulta recordatorios por fecha.
     *
     * @param fecha Fecha a consultar.
     * @return Lista de recordatorios.
     */
    public List<Recordatorio> consultarPorFecha(Date fecha) {
        throw new UnsupportedOperationException("Método consultarPorFecha() no implementado.");
    }

    /**
     * Consulta recordatorios por estado.
     *
     * @param estado Estado del recordatorio.
     * @return Lista de recordatorios.
     */
    public List<Recordatorio> consultarPorEstado(EstadoRecordatorio estado) {
        throw new UnsupportedOperationException("Método consultarPorEstado() no implementado.");
    }
}
