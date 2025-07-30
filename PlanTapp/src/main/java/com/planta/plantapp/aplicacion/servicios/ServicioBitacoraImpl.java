package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.modelo.IBitacoraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de aplicación para bitácoras.
 */
@Service
public class ServicioBitacoraImpl {

    private final IBitacoraRepositorio repositorioBitacora;

    @Autowired
    public ServicioBitacoraImpl(IBitacoraRepositorio repositorioBitacora) {
        this.repositorioBitacora = repositorioBitacora;
    }

    /**
     * Registra una nueva observación para una planta.
     */
    public void registrarObservacion(String plantaId, String descripcion) {
        Bitacora nueva = new Bitacora(descripcion, null, plantaId, "observacion");
        repositorioBitacora.guardar(nueva);
    }

    /**
     * Obtiene todas las bitácoras asociadas a una planta.
     */
    public List<Bitacora> obtenerPorPlanta(String plantaId) {
        return repositorioBitacora.listarPorPlanta(plantaId);
    }

    /**
     * Obtiene bitácoras dentro de un rango de fechas.
     */
    public List<Bitacora> obtenerPorFechaRango(LocalDateTime desde, LocalDateTime hasta) {
        return repositorioBitacora.listarPorRangoFechas(desde, hasta);
    }

    /**
     * Edita la descripción de una bitácora si existe.
     */
    public void editarObservacion(String bitacoraId, String nuevaDescripcion) {
        Bitacora bitacora = repositorioBitacora.obtenerPorId(bitacoraId);
        if (bitacora != null) {
            bitacora.setDescripcion(nuevaDescripcion);
            repositorioBitacora.actualizar(bitacora);
        }
    }

    /**
     * Elimina una bitácora si existe.
     */
    public void eliminar(String bitacoraId) {
        repositorioBitacora.eliminar(bitacoraId);
    }

    /**
     * Exporta el historial en bytes (ejemplo simple, podría generar PDF o CSV).
     */
    public byte[] exportarHistorial(String plantaId) {
        List<Bitacora> historial = repositorioBitacora.listarPorPlanta(plantaId);
        return historial.toString().getBytes(); // Mejor reemplazar con lógica de exportación real
    }

    /**
     * Busca bitácoras por tipo de actividad (riego, poda, etc.).
     */
    public List<Bitacora> buscarPorTipo(String tipo) {
        return repositorioBitacora.listarPorTipoCuidado(tipo);
    }

    /**
     * Lista todas las bitácoras del sistema.
     */
    public List<Bitacora> listarTodas() {
        return repositorioBitacora.listarTodas();
    }
}
