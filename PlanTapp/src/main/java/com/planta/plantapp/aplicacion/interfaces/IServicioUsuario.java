package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import java.util.List;

/**
 * Interfaz de servicio para operaciones de usuario.
 * Define el contrato para la gestión de usuarios en el sistema.
 */
public interface IServicioUsuario {

    boolean registerUser(String name, String email, String password);

    Usuario authenticateUser(String email, String password);

    Usuario getUserById(String id);

    boolean updateProfile(Usuario user);

    boolean deleteUser(String id);

    List<Usuario> listUsers();

    boolean emailExists(String email);

    void recoverPassword(String email);

    boolean changePassword(String id, String newPassword);
}
