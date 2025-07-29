package com.planta.demo.aplicacion.servicios;

import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.planta.RegistroPlanta;
import com.planta.demo.dominio.modelo.planta.EstadoPlanta;
import com.planta.demo.dominio.modelo.IPlantaRepositorio;
import com.planta.demo.aplicacion.interfaces.IServicioPlanta;
import com.planta.demo.dominio.modelo.cuidado.TipoCuidado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPlantaImpl implements IServicioPlanta {

    @Autowired
    private IPlantaRepositorio plantaRepositorio;

    @Override
    public List<Planta> obtenerTodas() {
        return plantaRepositorio.listarTodas();
    }

    @Override
    public Planta obtenerPorId(Long id) {
        return plantaRepositorio.obtenerPorId(id.toString());
    }

    @Override
    public boolean guardar(Planta planta) {
        try {
            plantaRepositorio.guardar(planta);
            return true;
        } catch (Exception e) {
            System.err.println("Error guardando planta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void eliminar(Long id) {
        plantaRepositorio.eliminar(id.toString());
    }

    @Override
    public void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        // TODO: Implementar lógica de cuidados
    }

    @Override
    public List<Planta> buscarPorTipo(String tipo) {
        return plantaRepositorio.buscarPorTipo(tipo);
    }

    @Override
    public List<Planta> listarPorUsuario(Long usuarioId) {
        // Esto debería devolver RegistroPlanta, no Planta
        List<RegistroPlanta> registros = plantaRepositorio.listarRegistrosPorUsuario(usuarioId.toString());
        return registros.stream().map(RegistroPlanta::getPlanta).toList();
    }

    @Override
    public boolean registrarNuevaPlanta(String nombre, String tipo, String usuarioId) {
        try {
            // Buscar planta en catálogo
            List<Planta> plantas = plantaRepositorio.buscarPorNombre(nombre);
            if (plantas.isEmpty()) {
                return false;
            }
            
            Planta planta = plantas.get(0);
            RegistroPlanta registro = new RegistroPlanta(nombre, usuarioId, planta, "Sin ubicación");
            plantaRepositorio.guardarRegistro(registro);
            return true;
        } catch (Exception e) {
            System.err.println("Error registrando planta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> obtenerUltimosErrores() {
        return List.of(); // TODO: Implementar manejo de errores
    }

    @Override
    public int contarPorEstado(String estado) {
        // TODO: Implementar conteo por estado
        return 0;
    }

    // Métodos adicionales para el controlador
    public List<RegistroPlanta> obtenerRegistrosPorUsuario(String usuarioId) {
        return plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
    }

    public RegistroPlanta obtenerRegistroPorId(String registroId) {
        return ((com.planta.demo.infraestructura.repositorio.mongodb.PlantaRepositorioImpl) plantaRepositorio)
                .obtenerRegistroPorId(registroId);
    }

    public void guardarRegistro(RegistroPlanta registro) {
        plantaRepositorio.guardarRegistro(registro);
    }

    public void actualizarRegistro(RegistroPlanta registro) {
        plantaRepositorio.actualizarRegistro(registro);
    }

    public void eliminarRegistro(String registroId) {
        ((com.planta.demo.infraestructura.repositorio.mongodb.PlantaRepositorioImpl) plantaRepositorio)
                .eliminarRegistro(registroId);
    }
}
