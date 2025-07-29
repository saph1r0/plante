package com.planta.demo.dominio.modelo.fabrica;

import com.planta.demo.dominio.modelo.recordatorio.Recordatorio;
import com.planta.demo.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.cuidado.TipoCuidado;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Fábrica de objetos de dominio Recordatorio.
 */
public class RecordatorioFabrica {

    public RecordatorioFabrica() {
    }

    /**
     * Crea una instancia de Recordatorio a partir de parámetros primitivos.
     *
     * @param fechaEnvio Fecha de envío como java.util.Date (se transforma a LocalDateTime)
     * @param estado     Estado como String (debe coincidir con EstadoRecordatorio)
     * @param mensaje    Mensaje del recordatorio
     * @return Instancia de Recordatorio o null si los datos son inválidos
     */
    public Recordatorio crearRecordatorio(Date fechaEnvio, String estado, String mensaje) {
        if (fechaEnvio == null || estado == null || mensaje == null || mensaje.isBlank()) {
            return null;
        }

        EstadoRecordatorio estadoEnum;
        try {
            estadoEnum = EstadoRecordatorio.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        LocalDateTime fecha = fechaEnvio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        
        TipoCuidado tipoCuidadoDummy = TipoCuidado.RIEGO; 
        Planta plantaDummy = null; 

        Recordatorio recordatorio = new Recordatorio(mensaje, fecha, tipoCuidadoDummy, plantaDummy);
        recordatorio.setEstado(estadoEnum);
        return recordatorio;
    }
}
