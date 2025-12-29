package com.planta.plantapp.dominio.modelo.planta.dto;

import java.util.Date;

public class PlantaUsuarioDTO {
    private String id;
    private String nombreComun;
    private String nombreCientifico;
    private String descripcion;
    private String imagenURL;
    private String apodo;
    private String ubicacion;
    private String estado;
    private Date fechaRegistro;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreComun() { return nombreComun; }
    public void setNombreComun(String nombreComun) { this.nombreComun = nombreComun; }

    public String getNombreCientifico() { return nombreCientifico; }
    public void setNombreCientifico(String nombreCientifico) { this.nombreCientifico = nombreCientifico; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenURL() { return imagenURL; }
    public void setImagenURL(String imagenURL) { this.imagenURL = imagenURL; }

    public String getApodo(){ return apodo; }
    public void setApodo(String apodo){this.apodo = apodo; }

    public String getUbicacion(){ return ubicacion; }
    public void setUbicacion(String ubicacion){this.ubicacion = ubicacion; }

    public String getEstado(){ return estado; }
    public void setEstado(String estado){this.estado = estado; }

    public Date getFechaRegistro(){return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro){this.fechaRegistro = fechaRegistro;}
}