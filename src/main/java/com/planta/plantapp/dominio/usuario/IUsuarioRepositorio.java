package com.planta.plantapp.dominio.usuario;

import com.planta.plantapp.dominio.usuario.modelo.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de Usuario para acceder a la persistencia.
 * Parte del dominio de la solución.
 */
public interface IUsuarioRepositorio {

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id identificador único del usuario
     * @return Usuario encontrado o null si no existe
     */
    Usuario obtenerPorId(String id);

    /**
     * Lista todos los usuarios registrados.
     * 
     * @return Lista de usuarios
     */
    List<Usuario> listarTodos();

    /**
     * Guarda o actualiza un usuario.
     * 
     * @param usuario objeto usuario a guardar
     */
    void guardar(Usuario usuario);

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id identificador del usuario a eliminar
     */
    void eliminar(String id);

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email correo del usuario
     * @return Optional del usuario si se encuentra
     */
    Optional<Usuario> buscarPorCorreo(String email);

    /**
     * Verifica si un usuario existe por su ID.
     * 
     * @param id identificador del usuario
     * @return true si existe, false si no
     */
    Boolean existeUsuario(String id);
}
