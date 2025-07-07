package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.servicios.ServicioPlantaDominio;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para gestionar plantas.
 * Orquesta la lógica entre el dominio y la infraestructura.
 */
@Service
public class ServicioPlantaImpl implements IServicioPlanta{

    private final IPlantaRepositorio repositorioPlanta;
    private final ServicioPlantaDominio servicioDominio;

    public ServicioPlantaImpl(IPlantaRepositorio repositorioPlanta, ServicioPlantaDominio servicioDominio) {
        this.repositorioPlanta = repositorioPlanta;
        this.servicioDominio = servicioDominio;
    }

    @Override
    public List<Planta> buscarPorNombre(String nombre) {
        return repositorioPlanta.buscarPorNombre(nombre);
    }

    public List<Planta> obtenerTodas() {
        // Suponiendo que el repositorio tiene listarPorUsuario u otra forma global
        return repositorioPlanta.listarPorUsuario("global"); // cambiar si hay un método más adecuado
    }

    public Planta obtenerPorId(Long id) {
        return repositorioPlanta.obtenerPorId(id.toString());
    }

    public void guardar(Planta planta) {
        repositorioPlanta.guardar(planta);
    }

    public void eliminar(Long id) {
        repositorioPlanta.eliminar(id.toString());
    }

    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        Planta planta = repositorioPlanta.obtenerPorId(plantaId.toString());
        if (planta != null) {
            servicioDominio.agregarCuidado(planta, tipo, frecuenciaDias, notas);
            repositorioPlanta.guardar(planta);
        } else {
            throw new IllegalArgumentException("Planta no encontrada con ID: " + plantaId);
        }
    }
    
    /*public void marcarEventoRealizado(Long plantaId) {
        Planta planta = repositorioPlanta.obtenerPorId(plantaId.toString());
        if (planta != null) {
            servicioDominio.marcarEventoRealizado(planta);
            repositorioPlanta.guardar(planta);
        } else {
            throw new IllegalArgumentException("Planta no encontrada con ID: " + plantaId);
        }
    }*/
    
    public List<Planta> buscarPorTipo(String tipo) {
        return repositorioPlanta.buscarPorTipo(tipo);
    }

    public List<Planta> listarPorUsuario(Long usuarioId) {
        return repositorioPlanta.listarPorUsuario(usuarioId.toString());
    }

    public int contarPorEstado(String estado) {
        List<Planta> todas = obtenerTodas();
        return (int) todas.stream()
                .filter(p -> estado.equalsIgnoreCase(p.getEstado()))
                .count();
    }
}
