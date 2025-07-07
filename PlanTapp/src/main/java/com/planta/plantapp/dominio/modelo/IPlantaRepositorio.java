package com.planta.plantapp.dominio.modelo;

import com.planta.plantapp.dominio.modelo.planta.Planta;

import java.util.List;

/**
 * Repositorio del dominio para gestionar plantas.
 *
 * Esta interfaz define las operaciones del modelo del problema, 
 * sin depender de detalles de infraestructura o persistencia.
 */
public interface IPlantaRepositorio {

    /**
     * Obtiene una planta por su identificador único.
     * @param id Identificador de la planta
     * @return Planta correspondiente o null si no existe
     */
    Planta obtenerPorId(String id);

    /**
     * Lista todas las plantas asociadas a un usuario.
     * @param usuarioId Identificador del usuario
     * @return Lista de plantas del usuario
     */
    List<Planta> listarPorUsuario(String usuarioId);

    /**
     * Guarda una planta nueva o actualiza una existente.
     * @param planta Planta a guardar
     */
    void guardar(Planta planta);

    /**
     * Elimina una planta por su ID.
     * @param id Identificador de la planta a eliminar
     */
    void eliminar(String id);

    List<Planta> buscarPorNombre(String nombre);

    /**
     * Busca plantas por nombre asociado a un usuario.
     * @param nombre Nombre (parcial o completo) de la planta
     * @param usuarioId Identificador del usuario
     * @return Lista de coincidencias
     */
    List<Planta> buscarPorNombre(String nombre, String usuarioId);

    /**
     * Actualiza el estado general de una planta.
     * @param plantaId Identificador de la planta
     * @param estadoPlanta Estado nuevo a aplicar (ej. "enferma", "saludable")
     */
    void actualizarEstado(String plantaId, String estadoPlanta);

    /**
     * Busca plantas por tipo (etiqueta, especie, etc.).
     * @param tipo Tipo a filtrar (puede ser una categoría, etiqueta u otro criterio)
     * @return Lista de plantas que coinciden
     */
    List<Planta> buscarPorTipo(String tipo);

    /**
     * Cuenta cuántas plantas tiene un usuario.
     * @param usuarioId ID del usuario
     * @return Número total de plantas registradas por ese usuario
     */
    Long contarPorUsuario(String usuarioId);
}
