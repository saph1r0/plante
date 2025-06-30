package com.planta.demo.dominio.usuario;

import com.planta.demo.dominio.usuario.modelo.Usuario;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IUsuarioRepositorio {

    /**
     * @param id 
     * @return
     */
    public Usuario obtenerPorId(String id);

    /**
     * @return
     */
    public List<Usuario> listarTodos();

    /**
     * @param usuario 
     * @return
     */
    public void guardar(Usuario usuario);

    /**
     * @param id 
     * @return
     */
    public void eliminar(String id);

    /**
     * @param email 
     * @return
     */
    public Optional<Usuario> buscarPorCorreo(String email);

    /**
     * @param id 
     * @return
     */
    public Boolean existeUsuario(String id);

}