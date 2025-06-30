package com.planta.demo.dominio.modelo.cuidado;

import java.util.Date;

public class TareaCuidado {

    private int id;
    private TipoCuidado tipo;
    private Date fechaProgramada;
    private Date fechaRealizada;
    private boolean realizado;
    private String nota;

    public TareaCuidado() {
        this.realizado = false;
    }

    public TareaCuidado(int id, TipoCuidado tipo, Date fechaProgramada, String nota) {
        this.id = id;
        this.tipo = tipo;
        this.fechaProgramada = fechaProgramada;
        this.nota = nota;
        this.realizado = false;
    }

    public void marcarComoRealizada() {
        this.realizado = true;
        this.fechaRealizada = new Date(); // fecha actual
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoCuidado getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuidado tipo) {
        this.tipo = tipo;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
