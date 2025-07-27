package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IBitacoraRepositorio;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.servicios.ServicioBitacoraDominio;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación que orquesta el uso del dominio para gestionar bitácoras.
 * Forma parte del dominio de la solución.
 */
public class ServicioBitacoraImpl {

    private final ServicioBitacoraDominio servicioDominio;
    private final IBitacoraRepositorio repositorioBitacora;

    public ServicioBitacoraImpl(ServicioBitacoraDominio servicioDominio, IBitacoraRepositorio repositorioBitacora) {
        this.servicioDominio = servicioDominio;
        this.repositorioBitacora = repositorioBitacora;
    }

    /**
     * Registra una nueva observación para una planta.
     */
    public void registrarObservacion(String plantaId, String descripcion) {
        Bitacora nueva = new Bitacora(descripcion, null, new Planta(plantaId));
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
    public List<Bitacora> obtenerPorFechaRango(Date desde, Date hasta) {
        return repositorioBitacora.listarPorFecha(desde, hasta);
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
        return repositorioBitacora.listarPorTipoActividad(tipo);
    }

    /**
     * Lista todas las bitácoras pendientes de atención por usuario.
     */
    public List<Bitacora> listarPendientesPorUsuario(String usuarioId) {
        return repositorioBitacora.listarPorUsuario(usuarioId)
                .stream()
                .filter(bitacora -> bitacora.getDescripcion() != null && bitacora.getDescripcion().contains("pendiente"))
                .toList(); // Filtro de ejemplo, depende de cómo se marca lo "pendiente"
    }
}