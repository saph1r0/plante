package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
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
    public IServicioUsuario usuarioServicio;

    /**
     * @param nombre 
     * @param email 
     * @param contraseña 
     * @return
     */
    public boolean registrarUsuario(String nombre, String email, String contraseña) {
        // TODO implement here
        return false;
    }

    /**
     * @param email 
     * @param contraseña 
     * @return
     */
    public Usuario autenticarUsuario(String email, String contraseña) {
        // TODO implement here
        return null;
    }

    /**
     * @param id 
     * @return
     */
    public Usuario obtenerUsuarioPorId(int id) {
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