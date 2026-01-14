package com.planta.userplantsservice.dominio.modelo.planta.dto;

import java.util.Date;

public class RegistroPlantaResponseDTO {

    private String id;
    private String usuarioId;
    private String plantaId;
    private String apodo;
    private String ubicacion;
    private String estado;
    private Date fechaRegistro;
    private String fotoPersonal;
    private String notas;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getPlantaId() { return plantaId; }
    public void setPlantaId(String plantaId) { this.plantaId = plantaId; }

    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getFotoPersonal() { return fotoPersonal; }
    public void setFotoPersonal(String fotoPersonal) { this.fotoPersonal = fotoPersonal; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
