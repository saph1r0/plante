package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.modelo.IBitacoraRepositorio;

import java.util.Date;
import java.util.List;

public class BitacoraRepositorioImpl implements IBitacoraRepositorio {

    public BitacoraRepositorioImpl() {
    }

    @Override
    public void guardar(Bitacora bitacora) {
        // TODO: guardar en MongoDB
    }

    @Override
    public Bitacora obtenerPorId(String id) {
        // TODO: buscar por ID
        return null;
    }

    @Override
    public List<Bitacora> listarTodas() {
        // TODO
        return null;
    }

    @Override
    public void eliminar(String id) {
        // TODO
    }

    @Override
    public void actualizar(Bitacora bitacora) {
        // TODO
    }

    @Override
    public List<Bitacora> listarPorPlanta(String plantaId) {
        // TODO
        return null;
    }

    @Override
    public List<Bitacora> listarPorUsuario(String usuarioId) {
        // TODO
        return null;
    }

    @Override
    public List<Bitacora> listarPorFecha(Date fechaInicio, Date fechaFin) {
        // TODO
        return null;
    }

    @Override
    public List<Bitacora> listarPorTipoActividad(String tipoActividad) {
        // TODO
        return null;
    }

    @Override
    public List<Bitacora> buscarPorDescripcion(String descripcion) {
        // TODO
        return null;
    }

    @Override
    public Long contarRegistrosPorPlanta(String plantaId) {
        // TODO
        return null;
    }

    @Override
    public Bitacora obtenerUltimoRegistro(String plantaId) {
        // TODO
        return null;
    }
}
