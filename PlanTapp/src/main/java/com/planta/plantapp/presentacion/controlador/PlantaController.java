package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.util.Date;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con las plantas.
 */
public class PlantaController {

    private IServicioPlanta servicioPlanta;

    public PlantaController() {
        // Constructor por defecto (válido y permitido por SonarLint)
    }

    /**
     * Obtiene la lista de todas las plantas.
     *
     * @return Lista de plantas (no implementado aún)
     */
    public List<Planta> obtenerTodas() {
        throw new UnsupportedOperationException("Método obtenerTodas() no implementado aún.");
    }

    /**
     * Obtiene una planta por su ID.
     *
     * @param id ID de la planta
     * @return Planta encontrada o null
     */
    public Planta obtenerPorId(Long id) {
        throw new UnsupportedOperationException("Método obtenerPorId() no implementado aún.");
    }

    /**
     * Guarda una planta en el sistema.
     *
     * @param planta Planta a guardar
     */
    public void guardar(Planta planta) {
        throw new UnsupportedOperationException("Método guardar() no implementado aún.");
    }

    /**
     * Elimina una planta por su ID.
     *
     * @param id ID de la planta a eliminar
     */
    public void eliminar(Long id) {
        throw new UnsupportedOperationException("Método eliminar() no implementado aún.");
    }

    /**
     * Agrega un cuidado a una planta específica.
     *
     * @param plantaId ID de la planta
     * @param tipo Tipo de cuidado
     * @param fecha Fecha del cuidado
     */
    public void agregarCuidado(Long plantaId, String tipo, Date fecha) {
        throw new UnsupportedOperationException("Método agregarCuidado() no implementado aún.");
    }

    /**
     * Busca plantas por tipo.
     *
     * @param tipo Tipo de planta
     * @return Lista de plantas que coinciden
     */
    public List<Planta> buscarPorTipo(String tipo) {
        throw new UnsupportedOperationException("Método buscarPorTipo() no implementado aún.");
    }

    /**
     * Lista las plantas asociadas a un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de plantas del usuario
     */
    public List<Planta> listarPorUsuario(Long usuarioId) {
        throw new UnsupportedOperationException("Método listarPorUsuario() no implementado aún.");
    }
}
