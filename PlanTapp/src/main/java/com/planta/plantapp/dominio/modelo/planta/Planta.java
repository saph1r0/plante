package com.planta.plantapp.dominio.modelo.planta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;

/**
 * Entidad del dominio que representa una planta.
 */
@Document(collection = "plantas")
public class Planta {

    @Id
    private String id;

    private String estado;
    private String nombreComun;
    private String nombreCientifico;
    private String descripcion;
    private String imagenURL;
    private final List<Etiqueta> etiquetas;
    private final List<Cuidado> cuidados;

    public Planta(String nombreComun, String nombreCientifico, String descripcion, String imagenURL) {
        if (nombreComun == null || nombreComun.isBlank())
            throw new IllegalArgumentException("El nombre común no puede ser nulo o vacío.");
        if (nombreCientifico == null || nombreCientifico.isBlank())
            throw new IllegalArgumentException("El nombre científico no puede ser nulo o vacío.");

        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
        this.etiquetas = new ArrayList<>();
        this.cuidados = new ArrayList<>();
    }

    public Planta(String id) {
        this.id = id;
        this.etiquetas = new ArrayList<>();
        this.cuidados = new ArrayList<>();
    }

    // Solo para uso controlado por persistencia o fábricas
    protected Planta() {
        this.etiquetas = new ArrayList<>();
        this.cuidados = new ArrayList<>();
    }

    // Métodos de comportamiento de dominio
    public void cambiarDescripcion(String nuevaDescripcion) {
        this.descripcion = nuevaDescripcion;
    }

    public void cambiarImagen(String nuevaImagenURL) {
        this.imagenURL = nuevaImagenURL;
    }

    public void agregarEtiqueta(Etiqueta etiqueta) {
        if (etiqueta != null && !this.etiquetas.contains(etiqueta)) {
            this.etiquetas.add(etiqueta);
        }
    }

    public void quitarEtiqueta(Etiqueta etiqueta) {
        this.etiquetas.remove(etiqueta);
    }

    public void agregarCuidado(Cuidado cuidado) {
        if (cuidado != null) {
            this.cuidados.add(cuidado);
        }
    }

    public void limpiarCuidados() {
        this.cuidados.clear();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public String getEstado() {
        return estado;
    }

    public List<Etiqueta> getEtiquetas() {
        return new ArrayList<>(etiquetas);
    }

    public List<Cuidado> getCuidados() {
        return new ArrayList<>(cuidados);
    }

    // equals/hashCode por identidad
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planta)) return false;
        Planta planta = (Planta) o;
        return Objects.equals(id, planta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
