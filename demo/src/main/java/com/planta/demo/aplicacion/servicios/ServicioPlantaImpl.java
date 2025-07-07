package com.planta.demo.aplicacion.servicios;

import com.planta.demo.dominio.modelo.IPlantaRepositorio;
import com.planta.demo.dominio.modelo.cuidado.TipoCuidado;
import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.servicios.ServicioPlantaDominio;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para gestionar plantas.
 * Orquesta la lógica entre el dominio y la infraestructura.
 */
public class ServicioPlantaImpl {

    private final IPlantaRepositorio repositorioPlanta;
    private final ServicioPlantaDominio servicioDominio;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param repositorioPlanta Repositorio para operaciones de persistencia
     * @param servicioDominio Servicio de dominio para lógica de negocio
     */
    public ServicioPlantaImpl(IPlantaRepositorio repositorioPlanta, ServicioPlantaDominio servicioDominio) {
        this.repositorioPlanta = repositorioPlanta;
        this.servicioDominio = servicioDominio;
    }

    /**
     * Obtiene todas las plantas del sistema.
     *
     * @return Lista de todas las plantas
     */
    public List<Planta> obtenerTodas() {
        return repositorioPlanta.listarPorUsuario("global");
    }

    /**
     * Obtiene una planta por su identificador.
     *
     * @param id Identificador de la planta
     * @return Planta encontrada o null si no existe
     */
    public Planta obtenerPorId(Long id) {
        return repositorioPlanta.obtenerPorId(id.toString());
    }

    /**
     * Guarda una planta en el repositorio.
     *
     * @param planta Planta a guardar
     */
    public void guardar(Planta planta) {
        repositorioPlanta.guardar(planta);
    }

    /**
     * Elimina una planta del repositorio.
     *
     * @param id Identificador de la planta a eliminar
     */
    public void eliminar(Long id) {
        repositorioPlanta.eliminar(id.toString());
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
        Planta planta = repositorioPlanta.obtenerPorId(plantaId.toString());
        if (planta != null) {
            servicioDominio.agregarCuidado(planta, tipo, frecuenciaDias, notas);
            repositorioPlanta.guardar(planta);
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
        return repositorioPlanta.buscarPorTipo(tipo);
    }

    /**
     * Lista las plantas asociadas a un usuario específico.
     *
     * @param usuarioId Identificador del usuario
     * @return Lista de plantas del usuario
     */
    public List<Planta> listarPorUsuario(Long usuarioId) {
        return repositorioPlanta.listarPorUsuario(usuarioId.toString());
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
}
