package com.planta.demo.aplicacion.servicios;

import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.IPlantaRepositorio;
import com.planta.demo.aplicacion.interfaces.IServicioPlanta;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * ESTILO COOKBOOK: Implementación con procedimientos secuenciales
 * que modifican estado compartido para el registro de plantas.
 */
/**
 *  * Servicio de aplicación para gestionar plantas.
 *  * Orquesta la lógica entre el dominio y la infraestructura.
 *  */
@Service
public class ServicioPlantaImpl implements IServicioPlanta {

    private final IPlantaRepositorio plantaRepositorio;

    /**
     * Constructor que inyecta las dependencias necesarias.
     */
    // COOKBOOK: Estado compartido modificado por procedimientos
    private List<String> erroresValidacion = new ArrayList<>();
    private Planta plantaEnProceso;
    private boolean procesoCompletado = false;
    private Map<String, Object> datosTemporales = new HashMap<>();

    public ServicioPlantaImpl(IPlantaRepositorio plantaRepositorio) {
        this.plantaRepositorio = plantaRepositorio;
    }

    /**
     * Obtiene todas las plantas del sistema.
     *
     * @return Lista de todas las plantas
     */
    public List<Planta> obtenerTodas() {
        return plantaRepositorio.listarPorUsuario("global");
    }

    /**
     * Obtiene una planta por su identificador.
     *
     * @param id Identificador de la planta
     * @return Planta encontrada o null si no existe
     */
    public Planta obtenerPorId(Long id) {
        return plantaRepositorio.obtenerPorId(id.toString());
    }

    /**
     * Guarda una planta en el repositorio.
     *
     * @param planta Planta a guardar
     */
    public void guardar(Planta planta) {
        plantaRepositorio.guardar(planta);
    }

    /**
     * Elimina una planta del repositorio.
     *
     * @param id Identificador de la planta a eliminar
     */
    public void eliminar(Long id) {
        plantaRepositorio.eliminar(id.toString());
    }

    /**
     * Agrega un cuidado a una planta específica.
     *
     * @param plantaId Identificador de la planta
     * @param tipo Tipo de cuidado a agregar
     * @param frecuenciaDias Frecuencia en días del cuidado
     * @param notas Notas adicionales del cuidado
     */
    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        Planta planta = plantaRepositorio.obtenerPorId(plantaId.toString());
        if (planta != null) {
            servicioDominio.agregarCuidado(planta, tipo, frecuenciaDias, notas);
            plantaRepositorio.guardar(planta);
        } else {
            throw new IllegalArgumentException("Planta no encontrada con ID: " + plantaId);
        }
    }

    /**
     * Busca plantas por tipo o especie.
     *
     * @param tipo Tipo de planta a buscar
     * @return Lista de plantas del tipo especificado
     */
    public List<Planta> buscarPorTipo(String tipo) {
        return plantaRepositorio.buscarPorTipo(tipo);
    }

    /**
     * Lista las plantas asociadas a un usuario específico.
     *
     * @param usuarioId Identificador del usuario
     * @return Lista de plantas del usuario
     */
    public List<Planta> listarPorUsuario(Long usuarioId) {
        return plantaRepositorio.listarPorUsuario(usuarioId.toString());
    }

    /**
     * ESTILO COOKBOOK: Registro de planta con pasos secuenciales
     */
    public boolean registrarNuevaPlanta(String nombre, String tipo, String usuarioId) {
        limpiarEstadoProceso();
        validarDatosEntrada(nombre, tipo, usuarioId);
        prepararDatosPlanta(nombre, tipo);
        verificarReglas(usuarioId);

        if (erroresValidacion.isEmpty()) {
            ejecutarGuardado(usuarioId);
        }

        return procesoCompletado;
    }

    public List<String> obtenerUltimosErrores() {
        return new ArrayList<>(erroresValidacion);
    }

    /**
     * Cuenta las plantas que tienen un estado específico.
     *
     * @param estado Estado a contar
     * @return Número de plantas con el estado especificado
     */
    public int contarPorEstado(String estado) {
        List<Planta> todas = obtenerTodas();
        return (int) todas.stream()
                .filter(p -> estado.equalsIgnoreCase(p.getEstado()))
                .count();
    }

    /**
     * COOKBOOK: Procedimiento para limpiar estado compartido.
     */
    private void limpiarEstadoProceso() {
        erroresValidacion.clear();
        plantaEnProceso = null;
        procesoCompletado = false;
        datosTemporales.clear();
    }

    /**
     * COOKBOOK: Procedimiento para validar datos de entrada.
     */
    private void validarDatosEntrada(String nombre, String tipo, String usuarioId) {
        if (nombre == null || nombre.trim().isEmpty()) {
            erroresValidacion.add("Nombre de planta es obligatorio");
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            erroresValidacion.add("Tipo de planta es obligatorio");
        }

        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            erroresValidacion.add("Usuario ID es obligatorio");
        }

        // Validar longitud del nombre
        if (nombre != null && nombre.length() > 100) {
            erroresValidacion.add("Nombre de planta muy largo (máximo 100 caracteres)");
        }
    }

    /**
     * COOKBOOK: Procedimiento para preparar y normalizar datos.
     */
    private void prepararDatosPlanta(String nombre, String tipo) {
        if (!erroresValidacion.isEmpty()) {
            return; // No procesar si hay errores previos
        }

        plantaEnProceso = new Planta();
        plantaEnProceso.setNombre(nombre.trim().toLowerCase());
        plantaEnProceso.setTipo(tipo.trim().toUpperCase());
        plantaEnProceso.setFechaRegistro(new Date());
        plantaEnProceso.setEstado("ACTIVA");

        // Guardar datos originales en estado temporal
        datosTemporales.put("nombre_original", nombre);
        datosTemporales.put("tipo_original", tipo);
        datosTemporales.put("timestamp_proceso", System.currentTimeMillis());
    }

    /**
     * COOKBOOK: Procedimiento para verificar reglas de negocio.
     */
    private void verificarReglas(String usuarioId) {
        if (!erroresValidacion.isEmpty() || plantaEnProceso == null) {
            return;
        }

        try {
            // Verificar duplicados por nombre de usuario
            List<Planta> existentes = plantaRepositorio.buscarPorNombre(
                    plantaEnProceso.getNombre(), usuarioId);

            if (existentes != null && !existentes.isEmpty()) {
                erroresValidacion.add("Ya existe una planta con ese nombre para este usuario");
            }

            // Verificar límite de plantas por usuario (regla de negocio)
            List<Planta> plantasUsuario = plantaRepositorio.listarPorUsuario(usuarioId);
            if (plantasUsuario != null && plantasUsuario.size() >= 50) {
                erroresValidacion.add("Usuario ha alcanzado el límite máximo de 50 plantas");
            }

        } catch (Exception e) {
            erroresValidacion.add("Error al verificar reglas de negocio: " + e.getMessage());
        }
    }

    /**
     * COOKBOOK: Procedimiento para ejecutar el guardado final.
     */
    private void ejecutarGuardado(String usuarioId) {
        if (plantaEnProceso == null || !erroresValidacion.isEmpty()) {
            return;
        }

        try {
            // Asignar datos finales
            plantaEnProceso.setUsuarioId(usuarioId);
            plantaEnProceso.setId(UUID.randomUUID().toString());
            plantaEnProceso.setUltimoRiego(new Date());

            // Guardar en repositorio
            plantaRepositorio.guardar(plantaEnProceso);

            // Marcar proceso como completado
            procesoCompletado = true;

            // Log del proceso exitoso
            System.out.println("Planta registrada exitosamente: " + plantaEnProceso.getNombre() +
                    " para usuario: " + usuarioId);

        } catch (Exception e) {
            erroresValidacion.add("Error al guardar planta: " + e.getMessage());
            procesoCompletado = false;
        }
    }
}
}
