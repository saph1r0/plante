package com.planta.demo.aplicacion.servicios;

import dominio.modelo.Bitacora;
import dominio.modelo.IBitacoraRepositorio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ServicioBitacoraImpl {

    private static final Logger logger = Logger.getLogger(ServicioBitacoraImpl.class.getName());
    private final IBitacoraRepositorio repositorioBitacora;

   //cookbook style
    public ServicioBitacoraImpl(IBitacoraRepositorio repositorioBitacora) {
        if (repositorioBitacora == null) {
            throw new IllegalArgumentException("Repositorio no puede ser nulo");
        }
        this.repositorioBitacora = repositorioBitacora;
    }
    private void servicioDominio;

    /**
     * 
     */
    public ServicioBitacoraImpl(IBitacoraRepositorio repositorioBitacora) {
        this.repositorioBitacora = repositorioBitacora;
    }


    /**
     * @param plantaId 
     * @param descripcion 
     * @return
     */
    public void registrarObservacion(Long plantaId, String descripcion) {
        Bitacora bitacora = new Bitacora(plantaId, descripcion);
        repositorioBitacora.guardar(bitacora);
    }

    /**
     * @param plantaId 
     * @return
     */
    public List<Bitacora> obtenerPorPlanta(Long plantaId) {
        List<Bitacora> resultado = new ArrayList<>();
        for (Bitacora b : repositorioBitacora.obtenerTodas()) {
            if (b.getPlantaId().equals(plantaId)) {
                resultado.add(b);
            }
        }
        return resultado;
    }

    /**
     * @param desde 
     * @param hasta 
     * @return
     */
    public List<Bitacora> obtenerPorFechaRango(Date desde, Date hasta) {
        List<Bitacora> resultado = new ArrayList<>();
        for (Bitacora b : repositorioBitacora.obtenerTodas()) {
            if (!b.getFecha().before(desde) && !b.getFecha().after(hasta)) {
                resultado.add(b);
            }
        }
        return resultado;
    }

    /**
     * @param bitacoraId 
     * @param nuevaDescripcion 
     * @return
     */
    public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
        if (bitacoraId == null || nuevaDescripcion == null) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }
        // TODO implementar actualización en repositorio
    }

    /**
     * @param bitacoraId 
     * @return
     */

    public void eliminar(Long bitacoraId) {
        Iterator<Bitacora> iterator = bitacoras.iterator();
        while (iterator.hasNext()) {
            Bitacora b = iterator.next();
            if (b.getId().equals(bitacoraId)) {
                iterator.remove();
                logger.info("Bitacora con ID " + bitacoraId + " eliminada.");
                return;
            }
        }
        logger.warning("No se encontró una bitacora con ID " + bitacoraId);
    }
    /**
     * @param plantaId 
     * @return
     */
    public byte exportarHistorial() {
        try (FileWriter writer = new FileWriter("historial.txt")) {
            // Aquí iría tu lógica real para exportar los datos
            writer.write("=== Historial de bitácoras ===\n");
            writer.write("Registro 1: inicio del sistema\n");
            writer.write("Registro 2: parada del sistema\n");
            writer.write("Registro 3: error crítico\n");
            logger.info("Historial exportado exitosamente a historial.txt");
            return 0; // éxito
        } catch (IOException e) {
            logger.severe("Error al exportar historial: " + e.getMessage());
            return 1; // error
        }
    }

    public static void main(String[] args) {
        BitacoraService servicio = new BitacoraService();
        byte resultado = servicio.exportarHistorial();
        if (resultado == 0) {
            logger.info("Exportación completada sin errores.");
        } else {
            logger.warning("Exportación fallida.");
        }
    }
}


/**
     * @param tipo 
     * @return
     */
    public List<Bitacora> buscarPorTipo(String tipo) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuarioId 
     * @return
     */
    public List<Bitacora> listarPendientesPorUsuario(Long usuarioId) {
        // TODO implement here
        return null;
    }

}