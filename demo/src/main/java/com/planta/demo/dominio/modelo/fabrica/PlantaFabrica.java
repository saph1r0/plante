package com.planta.demo.dominio.modelo.fabrica;

import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.planta.Etiqueta;

import java.util.List;

/**
 * Fábrica de dominio para crear instancias válidas de Planta.
 */
public class PlantaFabrica {

    /**
     * Crea una nueva instancia de Planta.
     *
     * @param nombreComun       nombre común de la planta (obligatorio)
     * @param nombreCientifico  nombre científico de la planta (obligatorio)
     * @param descripcion       descripción de la planta (opcional)
     * @param imagenURL         URL de imagen de la planta (opcional)
     * @param nombresEtiquetas  lista de nombres de etiquetas asociadas (puede ser nulo o vacío)
     * @return instancia de Planta válida
     * @throws IllegalArgumentException si los campos obligatorios son inválidos
     */
    public Planta crearPlanta(String nombreComun, String nombreCientifico, String descripcion, String imagenURL, List<String> nombresEtiquetas) {
        Planta planta = new Planta(nombreComun, nombreCientifico, descripcion, imagenURL);

        if (nombresEtiquetas != null) {
            for (String nombreEtiqueta : nombresEtiquetas) {
                if (nombreEtiqueta != null && !nombreEtiqueta.isBlank()) {
                    Etiqueta etiqueta = new Etiqueta();
                    etiqueta.setNombre(nombreEtiqueta); 
                    planta.agregarEtiqueta(etiqueta);
                }
            }
        }

        return planta;
    }
}
