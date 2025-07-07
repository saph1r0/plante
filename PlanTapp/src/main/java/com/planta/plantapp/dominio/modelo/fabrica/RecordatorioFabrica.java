package com.planta.plantapp.dominio.modelo.fabrica;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Fábrica de objetos de dominio Recordatorio.
 */
public class RecordatorioFabrica {

    public RecordatorioFabrica() {
        // Constructor vacío requerido por frameworks o instanciación reflexiva.
    }

    /**
     * Crea un Recordatorio a partir de datos primitivos.
     *
     * @param fechaEnvio Fecha de envío (java.util.Date)
     * @param estado     Estado en texto (debe coincidir con EstadoRecordatorio)
     * @param mensaje    Mensaje del recordatorio
     * @return Recordatorio creado o null si los datos no son válidos
     */
    public Recordatorio crearRecordatorio(Date fechaEnvio, String estado, String mensaje) {
        if (fechaEnvio == null || estado == null || mensaje == null || mensaje.isBlank()) {
            return null;
        }

        EstadoRecordatorio estadoEnum;
        try {
            estadoEnum = EstadoRecordatorio.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

        LocalDateTime fecha = fechaEnvio.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        TipoCuidado tipoCuidado = TipoCuidado.RIEGO; // Mejora: pedirlo como parámetro
        Planta planta = null; // Mejora: pasar la planta o usar un objeto nulo

        Recordatorio recordatorio = new Recordatorio(mensaje, fecha, tipoCuidado, planta);
        recordatorio.setEstado(estadoEnum);
        return recordatorio;
    }
}
