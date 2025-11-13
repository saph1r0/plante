package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con la bitácora de las plantas.
 */
public class BitacoraController {

    public BitacoraController() {
        // Constructor por defecto
    }

    public List<Bitacora> obtenerPorPlanta(Long plantaId) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    public List<Bitacora> obtenerPorUsuario(Long usuarioId) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    public void registrarObservacion(Long plantaId, String descripcion) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
        throw new UnsupportedOperationException("Método no implementado");
    }

    public void eliminar(Long bitacoraId) {
        throw new UnsupportedOperationException("Método no implementado");
    }
}
