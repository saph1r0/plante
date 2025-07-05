package com.planta.plantapp.dominio.modelo.bitacora;

import com.planta.plantapp.dominio.modelo.planta.Planta;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad de dominio que representa una entrada de bitácora de cuidado de una planta.
 */
public class Bitacora {

    private String id;
    private Date fecha;
    private String descripcion;
    private String fotoOpcional;
    private Planta planta;

    public Bitacora(String descripcion, String fotoOpcional, Planta planta) {
        this.fecha = new Date();
        this.descripcion = descripcion;
        this.fotoOpcional = fotoOpcional;
        this.planta = planta;
    }

    // Constructor vacío útil para instanciación sin parámetros (por ejemplo, tests)
    public Bitacora() {
        this.fecha = new Date();
    }

    // Getters y setters

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

    public String getFotoOpcional() {
        return fotoOpcional;
    }

    public void setFotoOpcional(String fotoOpcional) {
        this.fotoOpcional = fotoOpcional;
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
        if (!(o instanceof Bitacora)) return false;
        Bitacora bitacora = (Bitacora) o;
        return Objects.equals(id, bitacora.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
