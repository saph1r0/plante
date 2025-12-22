package com.planta.plantapp.dominio.modelo.eventos;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;

import java.time.LocalDateTime;

/**
 * Evento de dominio: Se ha agregado una nueva entrada a la bitácora.
 */
public class BitacoraAgregadaEvento {

    private final Bitacora bitacora;
    private final LocalDateTime fecha;

    public BitacoraAgregadaEvento(Bitacora bitacora) {
        this.bitacora = bitacora;
        this.fecha = LocalDateTime.now();
    }

    public Bitacora getBitacora() {
        return bitacora;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return String.format("BitacoraAgregada[descripcion=%s, fecha=%s]",
                bitacora.getDescripcion(), fecha);
    }
}

