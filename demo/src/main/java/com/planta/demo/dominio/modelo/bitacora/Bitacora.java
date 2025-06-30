package com.planta.demo.dominio.modelo.bitacora;

import com.planta.demo.dominio.modelo.planta.Planta;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Document(collection = "bitacoras")
public class Bitacora {

    @Id
    private String id;

    private Date fecha;

    private String descripcion;

    private String fotoOpcional;

    @DBRef
    private Planta planta;

    // Constructores

    public Bitacora() {
        this.fecha = new Date();
    }

    public Bitacora(String descripcion, String fotoOpcional, Planta planta) {
        this.fecha = new Date();
        this.descripcion = descripcion;
        this.fotoOpcional = fotoOpcional;
        this.planta = planta;
    }

    // Getters y Setters

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
}
