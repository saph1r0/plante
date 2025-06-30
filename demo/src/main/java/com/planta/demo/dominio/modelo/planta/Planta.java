package com.planta.demo.dominio.modelo.planta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * Documento MongoDB que representa una planta.
 */
@Document(collection = "plantas")
public class Planta {

    @Id
    private String id;

    private String nombreComun;
    private String nombreCientifico;
    private String descripcion;
    private String imagenURL;

    // Constructor por defecto
    public Planta() {
    }

    // Constructor completo
    public Planta(String nombreComun, String nombreCientifico, String descripcion, String imagenURL) {
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    // Métodos útiles
    @Override
    public String toString() {
        return "Planta{" +
                "id='" + id + '\'' +
                ", nombreComun='" + nombreComun + '\'' +
                ", nombreCientifico='" + nombreCientifico + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagenURL='" + imagenURL + '\'' +
                '}';
    }

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
