package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioRecordatorioDominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación que gestiona la relación entre plantas y cuidados.
 * Aplica DDD con Bounded Context entre Plantas, Cuidados y Recordatorios.
 */
@Service
public class ServicioPlantaCuidadoImpl {

    private static final Logger logger = LoggerFactory.getLogger(ServicioPlantaCuidadoImpl.class);

    private final IPlantaRepositorio plantaRepositorio;
    private final ServicioRecordatorioDominio servicioRecordatorio;

    @Autowired
    public ServicioPlantaCuidadoImpl(IPlantaRepositorio plantaRepositorio,
                                     ServicioRecordatorioDominio servicioRecordatorio) {
        this.plantaRepositorio = plantaRepositorio;
        this.servicioRecordatorio = servicioRecordatorio;
    }

    /**
     * Agrega un cuidado a una planta y genera un recordatorio automático.
     */
    public void agregarCuidadoAPlanta(String plantaId, TipoCuidado tipo, String descripcion,
                                     Integer frecuenciaDias) {

        logger.info("Agregando cuidado {} a planta {}", tipo, plantaId);

        Planta planta = plantaRepositorio.obtenerPorId(plantaId);
        if (planta == null) {
            throw new IllegalArgumentException("Planta no encontrada: " + plantaId);
        }

        // Crear el cuidado
        Cuidado cuidado = new Cuidado(tipo, descripcion, frecuenciaDias);

        // Agregar el cuidado a la planta
        planta.agregarCuidado(cuidado);

        // Guardar la planta actualizada
        plantaRepositorio.guardar(planta);

        // Crear recordatorio automático para el próximo cuidado
        if (frecuenciaDias != null && frecuenciaDias > 0) {
            LocalDateTime fechaProximoCuidado = LocalDateTime.now().plusDays(frecuenciaDias);
            String mensaje = String.format("Recordatorio: %s para %s",
                                          descripcion, planta.getNombreComun());

            servicioRecordatorio.crearRecordatorio(planta, tipo, mensaje, fechaProximoCuidado);
        }

        logger.info("Cuidado agregado exitosamente y recordatorio creado");
    }

    /**
     * Obtiene todos los cuidados de una planta.
     */
    public List<Cuidado> obtenerCuidadosPlanta(String plantaId) {
        Planta planta = plantaRepositorio.obtenerPorId(plantaId);
        if (planta == null) {
            throw new IllegalArgumentException("Planta no encontrada: " + plantaId);
        }
        return planta.getCuidados();
    }

    /**
     * Actualiza los cuidados de una planta.
     */
    public void actualizarCuidadosPlanta(String plantaId, List<Cuidado> nuevosCuidados) {
        logger.info("Actualizando cuidados de planta {}", plantaId);

        Planta planta = plantaRepositorio.obtenerPorId(plantaId);
        if (planta == null) {
            throw new IllegalArgumentException("Planta no encontrada: " + plantaId);
        }

        // Limpiar cuidados existentes
        planta.limpiarCuidados();

        // Agregar nuevos cuidados
        for (Cuidado cuidado : nuevosCuidados) {
            planta.agregarCuidado(cuidado);

            // Crear recordatorios para cada cuidado con frecuencia
            if (cuidado.getFrecuenciaDias() != null && cuidado.getFrecuenciaDias() > 0) {
                LocalDateTime fechaProximoCuidado = LocalDateTime.now()
                    .plusDays(cuidado.getFrecuenciaDias());
                String mensaje = String.format("Recordatorio: %s para %s",
                                              cuidado.getDescripcion(), planta.getNombreComun());

                servicioRecordatorio.crearRecordatorio(planta, cuidado.getTipo(),
                                                      mensaje, fechaProximoCuidado);
            }
        }

        plantaRepositorio.guardar(planta);
        logger.info("Cuidados actualizados exitosamente");
    }

    /**
     * Elimina todos los cuidados de una planta.
     */
    public void eliminarCuidadosPlanta(String plantaId) {
        logger.info("Eliminando cuidados de planta {}", plantaId);

        Planta planta = plantaRepositorio.obtenerPorId(plantaId);
        if (planta == null) {
            throw new IllegalArgumentException("Planta no encontrada: " + plantaId);
        }

        planta.limpiarCuidados();
        plantaRepositorio.guardar(planta);

        logger.info("Cuidados eliminados exitosamente");
    }

    /**
     * Verifica si una planta tiene cuidados pendientes.
     */
    public boolean tieneCuidadosPendientes(String plantaId) {
        List<Cuidado> cuidados = obtenerCuidadosPlanta(plantaId);
        return cuidados.stream().anyMatch(Cuidado::esPendiente);
    }
}

