package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.modelo.IBitacoraRepositorio;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BitacoraRepositorioImpl implements IBitacoraRepositorio {

    public BitacoraRepositorioImpl() {
        // Constructor por defecto
    }

    @Override
    public void guardar(Bitacora bitacora) {
        // Método no implementado
    }

    @Override
    public Bitacora obtenerPorId(String id) {
        // Método no implementado
        return null;
    }

    @Override
    public List<Bitacora> listarTodas() {
        // Método no implementado
        return Collections.emptyList();
    }

    @Override
    public void eliminar(String id) {
        // Método no implementado
    }

    @Override
    public void actualizar(Bitacora bitacora) {
        // Método no implementado
    }

    @Override
    public List<Bitacora> listarPorPlanta(String plantaId) {
        // Método no implementado
        return Collections.emptyList();
    }

    @Override
    public List<Bitacora> listarPorUsuario(String usuarioId) {
        // Método no implementado
        return Collections.emptyList();
    }

    @Override
    public List<Bitacora> listarPorFecha(Date fechaInicio, Date fechaFin) {
        // Método no implementado
        return Collections.emptyList();
    }

    @Override
    public List<Bitacora> listarPorTipoActividad(String tipoActividad) {
        // Método no implementado
        return Collections.emptyList();
    }

    @Override
    public List<Bitacora> buscarPorDescripcion(String descripcion) {
        // Método no implementado
        return Collections.emptyList();
    }

    @Override
    public Long contarRegistrosPorPlanta(String plantaId) {
        // Método no implementado
        return 0L;
    }

    @Override
    public Bitacora obtenerUltimoRegistro(String plantaId) {
        // Método no implementado
        return null;
    }
}
