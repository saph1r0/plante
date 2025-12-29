package com.planta.userservice.aplicacion.interfaces;

import com.planta.userservice.dominio.modelo.Usuario;
import java.util.List;

public interface IServicioUsuario {
    void registrarUsuario(Usuario usuario);
    Usuario autenticarUsuario(String email, String contrasena);
    Usuario obtenerUsuarioPorId(Long id);
    boolean actualizarPerfil(Usuario usuario);
    boolean eliminarUsuario(Long id);
    List<Usuario> listarUsuarios();
    boolean existeCorreo(String email);
    void recuperarContrasena(String email);
    boolean cambiarContrasena(Long id, String nuevaContrasena);
}
