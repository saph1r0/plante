package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import java.util.List;

public interface IServicioUsuario {
    void registrarUsuario(Usuario usuario);
    Usuario autenticarUsuario(String email, String contrasena);
    Usuario obtenerUsuarioPorId(String id);
    boolean actualizarPerfil(Usuario usuario);
    boolean eliminarUsuario(String id);
    List<Usuario> listarUsuarios();
    boolean existeCorreo(String email);
    void recuperarContrasena(String email);
    boolean cambiarContrasena(String id, String nuevaContrasena);
}
