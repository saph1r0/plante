/*package servicios;*/
package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.util.List;
import java.util.Optional;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IServicioPlanta {
    List<Planta> buscarPorNombre(String nombre);

    Planta guardar(Planta planta);
    Optional<Planta> buscarPorId(String id);
    void eliminar(String id);
}