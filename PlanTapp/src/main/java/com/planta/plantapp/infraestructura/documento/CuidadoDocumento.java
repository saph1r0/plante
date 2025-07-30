package com.planta.plantapp.infraestructura.documento;

public class CuidadoDocumento {

    private String tipo;
    private String descripcion;
    private int frecuenciaDias;

    public CuidadoDocumento() {}

    public CuidadoDocumento(String tipo, String descripcion, int frecuenciaDias) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.frecuenciaDias = frecuenciaDias;
    }


    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getFrecuenciaDias() {
        return frecuenciaDias;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFrecuenciaDias(int frecuenciaDias) {
        this.frecuenciaDias = frecuenciaDias;
    }
}
