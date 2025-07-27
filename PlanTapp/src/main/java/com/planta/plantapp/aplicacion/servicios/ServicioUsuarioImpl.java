package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.usuario.IUsuarioRepositorio;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para gestionar operaciones de usuario.
 */
public class ServicioUsuarioImpl {

    private final IUsuarioRepositorio repositorioUsuario;

    public ServicioUsuarioImpl(IUsuarioRepositorio repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public void registrarUsuario(String nombre, String email, String contraseña) {
        Optional<Usuario> existente = repositorioUsuario.buscarPorCorreo(email);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario nuevo = new Usuario(nombre, email, contraseña);
        repositorioUsuario.guardar(nuevo);
    }

    public boolean autenticarUsuario(String email, String contraseña) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.buscarPorCorreo(email);
        return usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(contraseña);
    }

    public Usuario obtenerUsuarioPorId(String id) {
        return repositorioUsuario.obtenerPorId(id);
    }

    public boolean actualizarPerfil(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;
        repositorioUsuario.guardar(usuario); // usar guardar también para actualizar
        return true;
    }

    public boolean eliminarUsuario(String id) {
        if (!repositorioUsuario.existeUsuario(id)) return false;
        repositorioUsuario.eliminar(id);
        return true;
    }

    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.listarTodos();
    }

    public boolean existeCorreo(String email) {
        return repositorioUsuario.buscarPorCorreo(email).isPresent();
    }

    public void recuperarContraseña(String email) {
        if (!existeCorreo(email)) {
            throw new IllegalArgumentException("Correo no registrado");
        }
        // lógica de envío de correo u otro mecanismo va aquí
    }

    public boolean cambiarContraseña(String id, String nuevaContraseña) {
        Usuario usuario = repositorioUsuario.obtenerPorId(id);
        if (usuario == null) return false;
        usuario.setPassword(nuevaContraseña);
        repositorioUsuario.guardar(usuario);
        return true;
    }
}