package com.planta.plantapp.dominio.modelo.bitacora;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de dominio que representa una entrada de bitácora de cuidado de una planta.
 */
@Document(collection = "bitacoras")
public class Bitacora {

    @Id
    private String id;
    private LocalDateTime fecha;
    private String descripcion;
    private String foto;
    private String plantaId;
    private String tipoCuidado;
    private String observaciones;

    public Bitacora() {
        this.fecha = LocalDateTime.now();
    }

    public Bitacora(String descripcion, String foto, String plantaId, String tipoCuidado) {
        this();
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta no puede ser nulo o vacío");
        }
        this.descripcion = descripcion;
        this.foto = foto;
        this.plantaId = plantaId;
        this.tipoCuidado = tipoCuidado;
    }

    public Bitacora(String descripcion, String foto, String plantaId, String tipoCuidado, String observaciones) {
        this(descripcion, foto, plantaId, tipoCuidado);
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPlantaId() {
        return plantaId;
    }

    public void setPlantaId(String plantaId) {
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta no puede ser nulo o vacío");
        }
        this.plantaId = plantaId;
    }

    public String getTipoCuidado() {
        return tipoCuidado;
    }

    public void setTipoCuidado(String tipoCuidado) {
        this.tipoCuidado = tipoCuidado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bitacora bitacora = (Bitacora) obj;
        return Objects.equals(id, bitacora.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bitacora{" +
                "id='" + id + '\'' +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", plantaId='" + plantaId + '\'' +
                ", tipoCuidado='" + tipoCuidado + '\'' +
                '}';
    }
}
