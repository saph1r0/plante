package com.planta.plantapp.dominio.modelo.bitacora;

import com.planta.plantapp.dominio.modelo.planta.Planta;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad de dominio que representa una entrada de bitácora de cuidado de una planta.
 */
public class Bitacora {

    private String id;
    private Date fecha = new Date();  // inicialización directa
    private String descripcion;
    private String foto;
    private Planta planta;

    public Bitacora(String descripcion, String foto, Planta planta) {
        this.descripcion = descripcion;
        this.foto = foto;
        this.planta = planta;
    }

    public Bitacora() {
        // Constructor vacío
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    @Override
    public String toString() {
        return "Bitacora{" +
                "fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", planta=" + (planta != null ? planta.getNombreComun() : "N/A") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bitacora other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
