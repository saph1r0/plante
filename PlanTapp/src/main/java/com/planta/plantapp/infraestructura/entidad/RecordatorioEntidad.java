package com.planta.plantapp.infraestructura.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RecordatorioEntidad {
    @Id
    private Long id;
    private String mensajeDeRecordatorio;
    public RecordatorioEntidad() {}

    public RecordatorioEntidad(Long id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public String getMensajeDeRecordatorio() {
        return mensajeDeRecordatorio;
    }



}
