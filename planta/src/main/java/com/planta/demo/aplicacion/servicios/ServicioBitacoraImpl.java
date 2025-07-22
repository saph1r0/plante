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
       this.repositorioBitacora = Objects.requireNonNull(
               repositorioBitacora, "Repositorio no puede ser nulo"
       );
   }
    }
    private void servicioDominio;


    public ServicioBitacoraImpl(IBitacoraRepositorio repositorioBitacora) {
        this.repositorioBitacora = repositorioBitacora;
    }

    //Error/Exception Handling



public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
    Objects.requireNonNull(bitacoraId, "ID de bitácora requerido");
    Objects.requireNonNull(nuevaDescripcion, "Descripción no puede ser nula");

    Bitacora bitacora = Objects.requireNonNull(
            repositorioBitacora.buscarPorId(bitacoraId),
            "Bitácora no encontrada con ID " + bitacoraId
    );

    bitacora.setDescripcion(nuevaDescripcion);
    repositorioBitacora.actualizar(bitacora);
    logger.info("Descripción actualizada para bitácora ID " + bitacoraId);
}

            // Pipeline
            public List<Bitacora> obtenerPorFechaRango(Date desde, Date hasta) {
                return repositorioBitacora.obtenerTodas()
                        .stream()
                        .filter(b -> !b.getFecha().before(desde) && !b.getFecha().after(hasta))
                        .collect(Collectors.toList());
            }alArgumentException("Parámetros inválidos");
        }

    }

public void eliminar(Long bitacoraId) {
    List<Bitacora> bitacoras = repositorioBitacora.obtenerTodas();
    bitacoras.removeIf(b -> b.getId().equals(bitacoraId));
    repositorioBitacora.eliminar(bitacoraId);
    logger.info("Bitácora con ID " + bitacoraId + " eliminada.");
}

public byte exportarHistorial() {
    try (FileWriter writer = new FileWriter("historial.txt")) {
        writer.write("=== Historial de bitácoras ===\n");
        writer.write("Registro 1: inicio del sistema\n");
        writer.write("Registro 2: parada del sistema\n");
        writer.write("Registro 3: error crítico\n");
        logger.info("Historial exportado exitosamente");
        return 0;
    } catch (IOException e) {
        logger.severe("Error al exportar historial: " + e.getMessage());
        return 1;
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

    public List<Bitacora> buscarPorTipo(String tipo) {
        // TODO implement here
        return null;
    }

    public List<Bitacora> listarPendientesPorUsuario(Long usuarioId) {
        // TODO implement here
        return null;
    }

}