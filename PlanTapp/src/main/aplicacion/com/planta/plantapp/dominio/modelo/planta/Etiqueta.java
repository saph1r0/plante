package com.planta.plantapp.dominio.modelo.planta;

import java.util.Objects;

/**
 * Clase de dominio que representa una etiqueta asociada a una planta.
 */
public class Etiqueta {

    private int id;
    private String nombre;
    private String color;

    public Etiqueta() {
    }

    public Etiqueta(int id, String nombre, String color) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
    }

    public Etiqueta(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Métodos auxiliares

    @Override
    public String toString() {
        return "Etiqueta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Etiqueta)) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return id == etiqueta.id &&
               Objects.equals(nombre, etiqueta.nombre) &&
               Objects.equals(color, etiqueta.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, color);
    }
}
