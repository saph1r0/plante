package com.planta.plantapp.infraestructura.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UsuarioEntidad {
    @Id
    private Long id;

    private String nombre;

    public UsuarioEntidad() {}

    public UsuarioEntidad(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
