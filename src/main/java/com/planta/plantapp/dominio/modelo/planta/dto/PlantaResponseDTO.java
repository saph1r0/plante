package com.planta.plantapp.dominio.modelo.planta.dto;

import java.util.List;

/**
 * DTO para enviar datos de plantas al cliente
 */
public class PlantaResponseDTO {
    private String id;
    private String nombreComun;
    private String nombreCientifico;
    private String tipo;
    private String descripcion;
    private String imagenURL;
    private Long usuarioId;
    private List<EtiquetaDTO> etiquetas;
    private List<CuidadoDTO> cuidados;

    // Constructores
    public PlantaResponseDTO() {}

    public PlantaResponseDTO(String id, String nombreComun, String nombreCientifico, String tipo, String descripcion, String imagenURL, Long usuarioId) {
        this.id = id;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreComun() { return nombreComun; }
    public void setNombreComun(String nombreComun) { this.nombreComun = nombreComun; }

    public String getNombreCientifico() { return nombreCientifico; }
    public void setNombreCientifico(String nombreCientifico) { this.nombreCientifico = nombreCientifico; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenURL() { return imagenURL; }
    public void setImagenURL(String imagenURL) { this.imagenURL = imagenURL; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<CuidadoDTO> getCuidados() { return cuidados; }
    public void setCuidados(List<CuidadoDTO> cuidados) { this.cuidados = cuidados; }

    public List<EtiquetaDTO> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<EtiquetaDTO> etiquetas) { this.etiquetas = etiquetas; }
}