package com.planta.plantapp.infraestructura.documento;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plantas")
public class PlantaDocumento {
    @Id
    private String id;
    private String nombre;

    public PlantaDocumento() {}

    public PlantaDocumento(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
