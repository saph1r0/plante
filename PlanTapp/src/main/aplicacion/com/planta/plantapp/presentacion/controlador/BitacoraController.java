package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioBitacora;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;


import java.io.*;
import java.util.*;

/**
 * Controlador para manejar las operaciones relacionadas con la bitácora de las plantas.
 */
public class BitacoraController {

    private IServicioBitacora servicioBitacora;

    public BitacoraController() {
        // Constructor por defecto
    }

    /**
     * Obtiene la lista de bitácoras asociadas a una planta específica.
     *
     * @param plantaId ID de la planta
     * @return Lista de bitácoras (actualmente no implementado)
     */
    public List<Bitacora> obtenerPorPlanta(Long plantaId) {
        // TODO: Implementar lógica para obtener bitácoras por planta
        throw new UnsupportedOperationException("Método obtenerPorPlanta aún no implementado");
    }

    /**
     * Obtiene la lista de bitácoras asociadas a un usuario específico.
     *
     * @param usuarioId ID del usuario
     * @return Lista de bitácoras (actualmente no implementado)
     */
    public List<Bitacora> obtenerPorUsuario(Long usuarioId) {
        // TODO: Implementar lógica para obtener bitácoras por usuario
        throw new UnsupportedOperationException("Método obtenerPorUsuario aún no implementado");
    }

    /**
     * Registra una nueva observación para una planta.
     *
     * @param plantaId    ID de la planta
     * @param descripcion Descripción de la observación
     */
    public void registrarObservacion(Long plantaId, String descripcion) {
        // TODO: Implementar lógica para registrar una observación en la bitácora
        throw new UnsupportedOperationException("Método registrarObservacion aún no implementado");
    }

    /**
     * Edita la descripción de una observación existente en la bitácora.
     *
     * @param bitacoraId        ID de la bitácora
     * @param nuevaDescripcion  Nueva descripción a actualizar
     */
    public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
        // TODO: Implementar lógica para editar una observación
        throw new UnsupportedOperationException("Método editarObservacion aún no implementado");
    }

    /**
     * Elimina una observación de la bitácora.
     *
     * @param bitacoraId ID de la bitácora a eliminar
     */
    public void eliminar(Long bitacoraId) {
        // TODO: Implementar lógica para eliminar una observación de la bitácora
        throw new UnsupportedOperationException("Método eliminar aún no implementado");
    }
}
