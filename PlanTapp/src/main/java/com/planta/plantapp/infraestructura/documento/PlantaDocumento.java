package com.planta.plantapp.infraestructura.documento;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "plantas")
public class PlantaDocumento {

    @Id
    private String id;

    private String nombreComun;
    private String nombreCientifico;
    private String descripcion;
    private String imagenURL;
    private List<CuidadoDocumento> cuidados;

    public PlantaDocumento() {}

    public PlantaDocumento(String nombreComun, String nombreCientifico, String descripcion, String imagenURL, List<CuidadoDocumento> cuidados) {
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
        this.cuidados = cuidados;
    }

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

    public List<CuidadoDocumento> getCuidados() {
        return cuidados;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public void setCuidados(List<CuidadoDocumento> cuidados) {
        this.cuidados = cuidados;
    }
}