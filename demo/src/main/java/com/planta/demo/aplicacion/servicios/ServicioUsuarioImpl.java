package servicios;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ServicioUsuarioImpl {

    /**
     * Default constructor
     */
    public ServicioUsuarioImpl() {
    }

    /**
     * 
     */
    private void repositorioUsuario;

    /**
     * @param nombre 
     * @param email 
     * @param contraseña 
     * @return
     */
    public void registrarUsuario(String nombre, String email, String contraseña) {
        // TODO implement here
        return null;
    }

    /**
     * @param email 
     * @param contraseña 
     * @return
     */
    public boolean autenticarUsuario(String email, String contraseña) {
        // TODO implement here
        return false;
    }

    /**
     * @param id 
     * @return
     */
    public void obtenerUsuarioPorId(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuario 
     * @return
     */
    public boolean actualizarPerfil(void usuario) {
        // TODO implement here
        return false;
    }

    /**
     * @param id 
     * @return
     */
    public boolean eliminarUsuario(int id) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public List<Usuario> listarUsuarios() {
        // TODO implement here
        return null;
    }

    /**
     * @param email 
     * @return
     */
    public boolean existeCorreo(String email) {
        // TODO implement here
        return false;
    }

    /**
     * @param email 
     * @return
     */
    public void recuperarContraseña(String email) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @param nuevaContraseña 
     * @return
     */
    public boolean cambiarContraseña(int id, String nuevaContraseña) {
        // TODO implement here
        return false;
    }

}