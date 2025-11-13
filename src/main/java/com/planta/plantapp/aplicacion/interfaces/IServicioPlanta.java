package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de aplicación para plantas.
 * Define las operaciones principales que pueden realizarse con las plantas.
 */
public interface IServicioPlanta {

    // Métodos principales de la interfaz
    List<Planta> obtenerTodas();
    Optional<Planta> obtenerPorId(String id);
    Planta guardar(Planta planta);
    void eliminar(String id);
    List<Planta> buscarPorTipo(String tipo);
    List<Planta> buscarPorUsuario(Long usuarioId);
    List<Planta> buscarPorNombre(String nombre, String usuarioId);
}