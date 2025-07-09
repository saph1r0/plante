package com.planta.demo.dominio.modelo;

import com.planta.demo.dominio.modelo.bitacora.Bitacora;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IBitacoraRepositorio {

    /**
     * @param bitacora 
     * @return
     */
    public void guardar(Bitacora bitacora);

    /**
     * @param id 
     * @return
     */
    public Bitacora obtenerPorId(String id);

    /**
     * @return
     */
    public List<Bitacora> listarTodas();

    /**
     * @param id 
     * @return
     */
    public void eliminar(String id);

    /**
     * @param bitacora 
     * @return
     */
    public void actualizar(Bitacora bitacora);

    /**
     * @param plantaId 
     * @return
     */
    public List<Bitacora> listarPorPlanta(String plantaId);

    /**
     * @param usuarioId 
     * @return
     */
    public List<Bitacora> listarPorUsuario(String usuarioId);

    /**
     * @param fechaInicio 
     * @param fechaFin 
     * @return
     */
    public List<Bitacora> listarPorFecha(Date fechaInicio, Date fechaFin);

    /**
     * @param tipoActividad 
     * @return
     */
    public List<Bitacora> listarPorTipoActividad(String tipoActividad);

    /**
     * @param descripcion 
     * @return
     */
    public List<Bitacora> buscarPorDescripcion(String descripcion);

    /**
     * @param plantaId 
     * @return
     */
    public Long contarRegistrosPorPlanta(String plantaId);

    /**
     * @param plantaId 
     * @return
     */
    public Bitacora obtenerUltimoRegistro(String plantaId);

}