package presentacion.controlador;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class UsuarioController {

    /**
     * Default constructor
     */
    public UsuarioController() {
    }

    /**
     * 
     */
    public IUsuarioServicio usuarioServicio;

    /**
     * @param nombre 
     * @param email 
     * @param contraseña 
     * @return
     */
    public null registrarUsuario(String nombre, String email, String contraseña) {
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
    public null obtenerUsuarioPorId(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param usuarioId 
     * @return
     */
    public boolean actualizarPerfil(Long usuarioId) {
        // TODO implement here
        return false;
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

    /**
     * @param id 
     * @return
     */
    public boolean eliminarUsuario(int id) {
        // TODO implement here
        return false;
    }

}