package com.planta.plantapp.infraestructura.documento;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bitacoras")
public class BitacoraDocumento {
    @Id
    private String id;
    private String descripcion;

    public BitacoraDocumento() {}

    public BitacoraDocumento(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
