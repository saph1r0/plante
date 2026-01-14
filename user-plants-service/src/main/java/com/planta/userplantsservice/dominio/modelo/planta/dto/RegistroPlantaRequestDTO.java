package com.planta.userplantsservice.dominio.modelo.planta.dto;

public class RegistroPlantaRequestDTO {
    private String plantaId;
    private String apodo;
    private String ubicacion;
    private String estado;
    private String fotoPersonal;
    private String notas;

    // Getters y Setters
    public String getPlantaId() { return plantaId; }
    public void setPlantaId(String plantaId) { this.plantaId = plantaId; }
    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFotoPersonal() { return fotoPersonal; }
    public void setFotoPersonal(String fotoPersonal) { this.fotoPersonal = fotoPersonal; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}