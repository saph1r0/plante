package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con las plantas.
 */
@RestController
@RequestMapping("/plantas")
public class PlantaController {

    private IServicioPlanta servicioPlanta;

    public PlantaController() {
        // Constructor por defecto (válido y permitido por SonarLint)
    }

    public PlantaController(IServicioPlanta servicioPlanta) {
        this.servicioPlanta = servicioPlanta;
    }

    // Endpoint para buscar por nombre común
    @GetMapping("/buscar")
    public List<Planta> buscarPorNombre(@RequestParam String nombre) {
        return servicioPlanta.buscarPorNombre(nombre);
    }
    /**
     * Obtiene la lista de todas las plantas.
     *
     * @return Lista de plantas (no implementado aún)
     */
    @GetMapping
    public List<Planta> obtenerTodas() {
        return servicioPlanta.obtenerTodas();
    }

    /**
     * Obtiene una planta por su ID.
     *
     * @param id ID de la planta
     * @return Planta encontrada o null
     */
    @GetMapping("/{id}")
    public Planta obtenerPorId(@PathVariable Long id) {
        return servicioPlanta.obtenerPorId(id);
    }

    /**
     * Guarda una planta en el sistema.
     *
     * @param planta Planta a guardar
     */
    @PostMapping
    public void guardar(@RequestBody Planta planta) {
        servicioPlanta.guardar(planta);
    }

    /**
     * Elimina una planta por su ID.
     *
     * @param id ID de la planta a eliminar
     */
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicioPlanta.eliminar(id);
    }

    /**
     * Agrega un cuidado a una planta específica.
     *
     * @param plantaId ID de la planta
     * @param tipo Tipo de cuidado
     * @param fecha Fecha del cuidado
     */
    public void agregarCuidado(Long plantaId, String tipo, Date fecha) {
        throw new UnsupportedOperationException("Método agregarCuidado() no implementado aún.");
    }

    /**
     * Busca plantas por tipo.
     *
     * @param tipo Tipo de planta
     * @return Lista de plantas que coinciden
     */
    public List<Planta> buscarPorTipo(String tipo) {
        throw new UnsupportedOperationException("Método buscarPorTipo() no implementado aún.");
    }

    /**
     * Lista las plantas asociadas a un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de plantas del usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public List<Planta> listarPorUsuario(@PathVariable Long usuarioId) {
        return servicioPlanta.listarPorUsuario(usuarioId);
    }
}
