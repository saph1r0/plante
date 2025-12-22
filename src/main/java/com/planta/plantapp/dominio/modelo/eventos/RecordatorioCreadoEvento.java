package com.planta.plantapp.dominio.modelo.eventos;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;

import java.time.LocalDateTime;

/**
 * Evento de dominio: Se ha creado un nuevo recordatorio.
 */
public class RecordatorioCreadoEvento {

    private final Recordatorio recordatorio;
    private final LocalDateTime fecha;

    public RecordatorioCreadoEvento(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
        this.fecha = LocalDateTime.now();
    }

    public Recordatorio getRecordatorio() {
        return recordatorio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return String.format("RecordatorioCreado[mensaje=%s, fecha=%s]",
                recordatorio.getMensaje(), fecha);
    }
}

