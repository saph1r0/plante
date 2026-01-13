package com.planta.userplantsservice.infraestructura.documento;

import com.planta.userplantsservice.dominio.modelo.planta.EstadoPlanta;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Documento MongoDB que representa una planta registrada por un usuario.
 */
@Document(collection = "registros_plantas")
public class RegistroPlantaDocumento {

    @Id
    private String id;

    private String usuarioId;
    private String plantaId;
    private String apodo;
    private String ubicacion;
    private EstadoPlanta estado;
    private Date fechaRegistro;

    // 🆕 URL o ruta de la foto personalizada
    private String fotoPersonal;
    private String notas;

    public RegistroPlantaDocumento() {}

    public RegistroPlantaDocumento(String usuarioId, String plantaId, String apodo, String ubicacion, EstadoPlanta estado) {
        this.usuarioId = usuarioId;
        this.plantaId = plantaId;
        this.apodo = apodo;
        this.ubicacion = ubicacion;
        this.estado = estado != null ? estado : EstadoPlanta.SALUDABLE;
        this.fechaRegistro = new Date();
    }

    // Getters y Setters
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

    public EstadoPlanta getEstado() { return estado; }
    public void setEstado(EstadoPlanta estado) { this.estado = estado; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // 🆕 Getter y Setter para fotoPersonal
    public String getFotoPersonal() { return fotoPersonal; }
    public void setFotoPersonal(String fotoPersonal) { this.fotoPersonal = fotoPersonal; }
    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
