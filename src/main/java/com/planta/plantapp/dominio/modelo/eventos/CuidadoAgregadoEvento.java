package com.planta.plantapp.dominio.modelo.eventos;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;

import java.time.LocalDateTime;

/**
 * Evento de dominio: Se ha agregado un cuidado a una planta.
 */
public class CuidadoAgregadoEvento {

    private final Planta planta;
    private final Cuidado cuidado;
    private final LocalDateTime fecha;

    public CuidadoAgregadoEvento(Planta planta, Cuidado cuidado) {
        this.planta = planta;
        this.cuidado = cuidado;
        this.fecha = LocalDateTime.now();
    }

    public Planta getPlanta() {
        return planta;
    }

    public Cuidado getCuidado() {
        return cuidado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return String.format("CuidadoAgregado[planta=%s, cuidado=%s, fecha=%s]",
                planta.getNombreComun(), cuidado.getDescripcion(), fecha);
    }
}

