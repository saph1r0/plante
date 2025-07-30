package com.planta.plantapp.dominio.modelo.fabrica;

import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fábrica para crear instancias de Cuidado.
 * Implementa el patrón Factory para centralizar la creación de objetos Cuidado.
 */
public class CuidadoFabrica {

    private static final Logger logger = Logger.getLogger(CuidadoFabrica.class.getName());

    /**
     * Constructor vacío para la fábrica de cuidados.
     */
    public CuidadoFabrica() {
        // Constructor vacío intencionalmente - clase sin estado
    }

    /**
     * Crea una instancia de Cuidado con los parámetros proporcionados.
     */
    public Cuidado crearCuidado(TipoCuidado tipoCuidado, Planta planta, Double cantidad, String notas) {
        if (tipoCuidado == null) {
            logger.log(Level.WARNING, "Tipo de cuidado no puede ser nulo");
            return null;
        }

        if (planta == null) {
            logger.log(Level.WARNING, "Planta no puede ser nula");
            return null;
        }

        if (cantidad == null || cantidad <= 0) {
            logger.log(Level.WARNING, "Cantidad debe ser mayor a cero");
            return null;
        }

        try {
            // Ajustar al constructor real de Cuidado: (TipoCuidado, String, Integer)
            String plantaInfo = planta.getNombreComun();
            Integer cantidadInt = cantidad.intValue();
            
            Cuidado cuidado = new Cuidado(tipoCuidado, plantaInfo, cantidadInt);
            logger.log(Level.INFO, "Cuidado creado exitosamente para planta: {0}", planta.getNombreComun());
            return cuidado;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creando cuidado para planta: {0}", planta.getNombreComun());
            return null;
        }
    }
}
