package com.planta.plantapp.dominio.modelo.cuidado;

import java.util.EnumSet;

/**
 * Enumeración que representa los tipos de cuidado que se pueden aplicar a una planta.
 */

public enum TipoCuidado {

    RIEGO("Riego", "Suministro de agua a la planta", 3, UnidadMedida.MILILITRO),
    FERTILIZACION("Fertilización", "Aplicación de nutrientes", 14, UnidadMedida.GRAMO),
    PODA("Poda", "Corte de ramas o hojas", 30, UnidadMedida.UNIDAD),
    TRASPLANTE("Trasplante", "Cambio de maceta o ubicación", 365, UnidadMedida.UNIDAD),
    FUMIGACION("Fumigación", "Aplicación de pesticidas o fungicidas", 21, UnidadMedida.MILILITRO),
    LIMPIEZA("Limpieza", "Limpieza de hojas y eliminación de partes muertas", 7, UnidadMedida.UNIDAD),
    ROTACION("Rotación", "Cambio de orientación para exposición solar", 3, UnidadMedida.UNIDAD),
    AIREACION("Aireación", "Aflojamiento del sustrato", 14, UnidadMedida.UNIDAD);

    private final String nombre;
    private final String descripcion;
    private final int frecuenciaRecomendada;
    private final String unidadMedidaDefault;

    // Grupos para métodos auxiliares
    private static final EnumSet<TipoCuidado> CUIDADOS_CON_CANTIDAD = EnumSet.of(RIEGO, FERTILIZACION, FUMIGACION);
    private static final EnumSet<TipoCuidado> MANTENIMIENTO_BASICO = EnumSet.of(RIEGO, LIMPIEZA, ROTACION);
    private static final EnumSet<TipoCuidado> MANTENIMIENTO_AVANZADO = EnumSet.of(PODA, TRASPLANTE, FUMIGACION, AIREACION);

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

    public boolean requiereCantidad() {
        return CUIDADOS_CON_CANTIDAD.contains(this);
    }

    public boolean esMantenimientoBasico() {
        return MANTENIMIENTO_BASICO.contains(this);
    }

    public boolean esMantenimientoAvanzado() {
        return MANTENIMIENTO_AVANZADO.contains(this);
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Sub-enum para las unidades de medida, evitando literales duplicados.
     */
    public static class UnidadMedida {
        public static final String MILILITRO = "ml";
        public static final String GRAMO = "g";
        public static final String UNIDAD = "unidad";
    }
}
