package com.planta.plantapp.dominio.modelo.fabrica;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Fábrica de aplicación para crear instancias de Recordatorio.
 * Pertenece al dominio de la solución.
 * Aplicando prácticas de Clean Code y convenciones Java
 *
 * @author PlantCare Team
 * @version 2.0.0 - Clean Code Applied
 */
public class RecordatorioFabrica {

    // Logger para manejo de errores
    private static final Logger logger = Logger.getLogger(RecordatorioFabrica.class.getName());

    /**
     * Constructor por defecto.
     *
     */
    public RecordatorioFabrica() {
        // Constructor vacío intencionalmente - clase sin estado
    }

    /**
     * Crea una instancia de Recordatorio con los parámetros proporcionados.
     * Implementa validación robusta y manejo de errores según Clean Code.
     *
     * @param mensaje      Mensaje del recordatorio
     * @param fechaEnvio   Fecha programada para el recordatorio
     * @param tipoCuidado  Tipo de cuidado asociado
     * @param planta       Planta asociada al recordatorio
     * @return             Instancia de Recordatorio o null si los parámetros no son válidos
     */
    public Recordatorio crearRecordatorio(String mensaje, LocalDateTime fechaEnvio,
                                          TipoCuidado tipoCuidado, Planta planta) {
        // Validación de parámetros obligatorios
        if (mensaje == null || mensaje.isBlank()) {
            logger.log(Level.WARNING, "Mensaje del recordatorio no puede ser nulo o vacío");
            return null;
        }

        if (fechaEnvio == null) {
            logger.log(Level.WARNING, "Fecha de envío no puede ser nula");
            return null;
        }

        if (tipoCuidado == null) {
            logger.log(Level.WARNING, "Tipo de cuidado no puede ser nulo");
            return null;
        }

        if (planta == null) {
            logger.log(Level.WARNING, "Planta no puede ser nula");
            return null;
        }

        try {
            Recordatorio recordatorio = new Recordatorio(mensaje, fechaEnvio, tipoCuidado, planta);
            logger.log(Level.INFO, "Recordatorio creado exitosamente para planta: {0}",
                    planta.getNombreComun());
            return recordatorio;
        } catch (Exception e) {
            // Usar unnamed pattern para excepción no utilizada
            logger.log(Level.SEVERE, "Error creando recordatorio para planta: {0}",
                    planta.getNombreComun());
            return null;
        }
    }

    /**
     * Crea un recordatorio programado para el futuro basado en la frecuencia del cuidado.
     * Método de conveniencia que calcula automáticamente la fecha de envío.
     *
     * @param tipoCuidado  Tipo de cuidado (usa su frecuencia recomendada)
     * @param planta       Planta asociada
     * @return             Recordatorio programado o null si hay error
     */
    public Recordatorio crearRecordatorioAutomatico(TipoCuidado tipoCuidado, Planta planta) {
        if (tipoCuidado == null || planta == null) {
            logger.log(Level.WARNING, "TipoCuidado y Planta son obligatorios");
            return null;
        }

        try {
            // Calcular fecha de envío basada en la frecuencia del cuidado
            LocalDateTime fechaEnvio = LocalDateTime.now()
                    .plusDays(tipoCuidado.getFrecuenciaRecomendada());

            // Generar mensaje automático
            String mensaje = String.format("Recordatorio: Es momento de %s para %s",
                    tipoCuidado.getNombre().toLowerCase(),
                    planta.getNombreComun());

            return crearRecordatorio(mensaje, fechaEnvio, tipoCuidado, planta);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creando recordatorio automático");
            return null;
        }
    }

    /**
     * Crea un recordatorio inmediato (para ahora mismo).
     * Útil para notificaciones urgentes o recordatorios de emergencia.
     *
     * @param mensaje      Mensaje del recordatorio
     * @param tipoCuidado  Tipo de cuidado
     * @param planta       Planta asociada
     * @return             Recordatorio inmediato o null si hay error
     */
    public Recordatorio crearRecordatorioInmediato(String mensaje, TipoCuidado tipoCuidado,
                                                   Planta planta) {
        return crearRecordatorio(mensaje, LocalDateTime.now(), tipoCuidado, planta);
    }

    /**
     * Crea un recordatorio para una fecha específica.
     * Valida que la fecha no sea en el pasado.
     *
     * @param mensaje      Mensaje del recordatorio
     * @param fechaEnvio   Fecha específica (debe ser futura)
     * @param tipoCuidado  Tipo de cuidado
     * @param planta       Planta asociada
     * @return             Recordatorio programado o null si la fecha es inválida
     */
    public Recordatorio crearRecordatorioParaFecha(String mensaje, LocalDateTime fechaEnvio,
                                                   TipoCuidado tipoCuidado, Planta planta) {
        if (fechaEnvio != null && fechaEnvio.isBefore(LocalDateTime.now())) {
            logger.log(Level.WARNING, "No se puede crear recordatorio para fecha pasada: {0}",
                    fechaEnvio);
            return null;
        }

        return crearRecordatorio(mensaje, fechaEnvio, tipoCuidado, planta);
    }

    /**
     * Valida si los parámetros para crear un recordatorio son válidos.
     * Método de utilidad para validación externa.
     *
     * @param mensaje      Mensaje a validar
     * @param fechaEnvio   Fecha a validar
     * @param tipoCuidado  Tipo de cuidado a validar
     * @param planta       Planta a validar
     * @return             true si todos los parámetros son válidos
     */
    public boolean sonParametrosValidos(String mensaje, LocalDateTime fechaEnvio,
                                        TipoCuidado tipoCuidado, Planta planta) {
        return mensaje != null && !mensaje.isBlank() &&
                fechaEnvio != null &&
                tipoCuidado != null &&
                planta != null;
    }
}
