package com.planta.plantapp.dominio.modelo.servicios;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Servicio de dominio para reglas de negocio relacionadas con los recordatorios.
 * No depende de infraestructura ni de frameworks.
 */
public class ServicioRecordatorioDominio {

    private final IRecordatorioRepositorio repositorio;

    public ServicioRecordatorioDominio(IRecordatorioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Devuelve los recordatorios que están activos y cuya fecha ya venció.
     */
    public List<Recordatorio> consultarRecordatoriosPendientes(String usuarioId) {
        return repositorio.listarPendientesPorUsuario(usuarioId);
    }

    /**
     * Crea y guarda un nuevo recordatorio asociado a una planta.
     */
    public Recordatorio crearRecordatorio(Planta planta, TipoCuidado tipoCuidado, String mensaje, LocalDateTime fechaEnvio) {
        Objects.requireNonNull(planta, "La planta no puede ser null");
        Objects.requireNonNull(tipoCuidado, "El tipo de cuidado no puede ser null");
        Objects.requireNonNull(fechaEnvio, "La fecha de envío no puede ser null");

        Recordatorio recordatorio = new Recordatorio(
                mensaje != null ? mensaje : "Recordatorio generado automáticamente",
                fechaEnvio,
                tipoCuidado,
                planta
        );

        repositorio.guardar(recordatorio);
        return recordatorio;
    }

    /**
     * Marca un recordatorio como completado.
     */
    public void completarRecordatorio(String recordatorioId) {
        repositorio.marcarComoCompletado(recordatorioId);
    }
}
