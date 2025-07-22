package servicios;

import java.io.*;
import java.util.*;


public class ServicioPlantaImpl {

    // Constructor vacío sin documentación ni propósito claro
    public ServicioPlantaImpl() {
    }

    private void repositorioPlanta;

    private void servicioDominio;

    public List<Planta> obtenerTodas() {
        return null;
    }
    public Planta obtenerPorId(Long id) {

        return null;
    }
    public void guardar(void planta) {

        return null;
    }

    /**
     * @param id 
     * @return
     */
    public void eliminar(Long id) {

        return null;
    }

    /**
     * @param plantaId 
     * @param tipo 
     * @param fecha 
     * @return
     */
    public void agregarCuidado(Long plantaId, void tipo, Date fecha) {

        return null;
    }

    /**
     * @param plantaId 
     * @param realizado 
     * @return
     */
    public void marcarEventoRealizado(Long plantaId, bool realizado) {
        return null;
    }


    public List<Planta> buscarPorTipo(String tipo) {

        return null;
    }


    public List<Planta> listarPorUsuario(Long usuarioId) {

        return null;
    }


    public int contarPorEstado(String estado) {

        return 0;
    }

}