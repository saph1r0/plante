package com.planta.demo.presentacion.controlador;

import com.planta.demo.aplicacion.interfaces.IServicioPlanta;
import com.planta.demo.dominio.modelo.planta.Planta;


import java.io.*;
import java.util.*;

/**
 * 
 */
public class PlantaController {
    private static final int LIMITE_RESULTADOS = 100;
    private static final String ESTADO_ELIMINADA = "ELIMINADA";
    private static final long MILISEGUNDOS_POR_DIA = 86400000L;

    private final IServicioPlanta servicioPlanta;
    private final IPlantaRepositorio plantaRepositorio;
    /**
     * Default constructor
     */
    public PlantaController(IServicioPlanta servicioPlanta, IPlantaRepositorio plantaRepositorio) {
        this.servicioPlanta = servicioPlanta;
        this.plantaRepositorio = plantaRepositorio;
    }

    /**
     * 
     */
    public IServicioPlanta servicioPlanta;

    /**
     * ESTILO PIPELINE: Procesamiento fluido de plantas
     */
    public List<PlantaResumenDTO> obtenerTodas() {
        // Pipeline: obtener -> filtrar -> transformar -> ordenar
        return plantaRepositorio.listarTodos()
                .stream()
                .filter(planta -> planta != null && planta.getEstado() != null)
                .filter(planta -> !"ELIMINADA".equals(planta.getEstado()))
                .map(this::transformarAResumen)
                .sorted((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()))
                .limit(100)
                .collect(Collectors.toList());
    }

    private PlantaResumenDTO transformarAResumen(Planta planta) {
        PlantaResumenDTO dto = new PlantaResumenDTO();
        dto.setId(planta.getId());
        dto.setNombre(planta.getNombre());
        dto.setTipo(planta.getTipo());
        dto.setEstado(planta.getEstado());
        return dto;
    }

    /**
     * @param id
     * @return
     */
    /**
     * ESTILO THINGS: Acceso controlado a datos
     */
    public Planta obtenerPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }

        try {
            return servicioPlanta.buscarPorIdSeguro(id.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener planta", e);
        }
    }

    /**
     * @param planta 
     * @return
     */
    /**
     * ESTILO COOKBOOK: Usar el servicio con procedimientos secuenciales
     */
    public void guardar(Planta planta) {
        if (planta == null) {
            throw new IllegalArgumentException("Planta no puede ser nula");
        }

        // Usar el servicio que implementa Cookbook Style
        boolean resultado = servicioPlanta.registrarNuevaPlanta(
                planta.getNombre(),
                planta.getTipo(),
                planta.getUsuarioId()
        );

        if (!resultado) {
            List<String> errores = servicioPlanta.obtenerUltimosErrores();
            throw new RuntimeException("Errores al guardar: " + String.join(", ", errores));
        }
    }

    /**
     * @param id 
     * @return
     */
    public void eliminar(Long id) {
        // TODO implement here
    }

    /**
     * @param plantaId 
     * @param tipo 
     * @param fecha 
     * @return
     */
    public void agregarCuidado(Long plantaId, String tipo, Date fecha) {
        // TODO implement here
    }

    /**
     * @param tipo 
     * @return
     */
    public List<Planta> buscarPorTipo(String tipo) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuarioId 
     * @return
     */
    /**
     * ESTILO PIPELINE: Procesamiento con transformaciones encadenadas
     */
    public List<Planta> listarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            return new ArrayList<>();
        }

        // Pipeline: obtener -> validar -> enriquecer -> filtrar
        return plantaRepositorio.listarPorUsuario(usuarioId.toString())
                .stream()
                .filter(Objects::nonNull)
                .filter(planta -> planta.getNombre() != null)
                .peek(this::calcularDiasDesdeRegistro)
                .filter(planta -> !"ELIMINADA".equals(planta.getEstado()))
                .collect(Collectors.toList());
    }

    private void calcularDiasDesdeRegistro(Planta planta) {
        if (planta.getFechaRegistro() != null) {
            long dias = (System.currentTimeMillis() - planta.getFechaRegistro().getTime()) / (1000 * 60 * 60 * 24);
            planta.setDiasDesdeRegistro(dias);
        }
    }

}