package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioRecordatorioDominio;

import java.util.Date;
import java.util.List;

/**
 * Servicio de aplicación para recordatorios.
 * Esta versión solo define la estructura sin implementación real.
 */
public class ServicioRecordatorioImpl {

    private ServicioRecordatorioDominio servicioDominio;
    private IRecordatorioRepositorio repositorioRecordatorio;

    public ServicioRecordatorioImpl() {
        // Constructor por defecto
    }

    /**
     * No implementado.
     */
    public List<Recordatorio> consultarTodos() {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public List<Recordatorio> consultarPorPlanta(Long plantaId) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public List<Recordatorio> consultarPorUsuario(Long usuarioId) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public void crearRecordatorio(Planta planta, EstadoRecordatorio estado, String mensaje) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public void editarRecordatorio(Long id, String nuevoMensaje) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public void marcarComoRealizado(Long id) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public void eliminarRecordatorio(Long id) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public List<Recordatorio> consultarPorFecha(Date fecha) {
        throw new UnsupportedOperationException("Método no implementado.");
    }

    /**
     * No implementado.
     */
    public List<Recordatorio> consultarPorEstado(EstadoRecordatorio estado) {
        throw new UnsupportedOperationException("Método no implementado.");
    }
}
