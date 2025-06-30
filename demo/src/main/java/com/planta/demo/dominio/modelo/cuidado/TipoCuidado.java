package com.planta.demo.dominio.modelo.cuidado;

/**
 * Enumeración que representa los tipos de cuidado que se pueden aplicar a una planta
 */
public enum TipoCuidado {
    RIEGO("Riego", "Suministro de agua a la planta", 3, "ml"),
    FERTILIZACION("Fertilización", "Aplicación de nutrientes", 14, "g"),
    PODA("Poda", "Corte de ramas o hojas", 30, "unidad"),
    TRASPLANTE("Trasplante", "Cambio de maceta o ubicación", 365, "unidad"),
    FUMIGACION("Fumigación", "Aplicación de pesticidas o fungicidas", 21, "ml"),
    LIMPIEZA("Limpieza", "Limpieza de hojas y eliminación de partes muertas", 7, "unidad"),
    ROTACION("Rotación", "Cambio de orientación para exposición solar", 3, "unidad"),
    AIREACION("Aireación", "Aflojamiento del sustrato", 14, "unidad");

    private final String nombre;
    private final String descripcion;
    private final int frecuenciaRecomendada; // días
    private final String unidadMedidaDefault;

    TipoCuidado(String nombre, String descripcion, int frecuenciaRecomendada, String unidadMedidaDefault) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.frecuenciaRecomendada = frecuenciaRecomendada;
        this.unidadMedidaDefault = unidadMedidaDefault;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getFrecuenciaRecomendada() {
        return frecuenciaRecomendada;
    }

    public String getUnidadMedidaDefault() {
        return unidadMedidaDefault;
    }

    public boolean esRiego() {
        return this == RIEGO;
    }

    public boolean esFertilizacion() {
        return this == FERTILIZACION;
    }

    public boolean requiereCantidad() {
        return this == RIEGO || this == FERTILIZACION || this == FUMIGACION;
    }

    public boolean esMantenimientoBasico() {
        return this == RIEGO || this == LIMPIEZA || this == ROTACION;
    }

    public boolean esMantenimientoAvanzado() {
        return this == PODA || this == TRASPLANTE || this == FUMIGACION || this == AIREACION;
    }

    @Override
    public String toString() {
        return nombre;
    }
}