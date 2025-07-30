package com.planta.plantapp.dominio.modelo.planta.dto;

public class EtiquetaDTO {
    private int id;
    private String nombre;
    private String color;

    public EtiquetaDTO(){}

    public EtiquetaDTO(int id, String nombre, String color){
        this.id = id;
        this.nombre = nombre;
        this.color = color;
    }
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}