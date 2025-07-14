package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.dominio.usuario.IUsuarioRepositorio;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuarioImpl implements IServicioUsuario {

    private final IUsuarioRepositorio repositorioUsuario;

    public ServicioUsuarioImpl(IUsuarioRepositorio repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getCorreo() == null) {
            throw new IllegalArgumentException("Datos del usuario inválidos");
        }

        Optional<Usuario> existente = repositorioUsuario.buscarPorCorreo(usuario.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        try {
            repositorioUsuario.guardar(usuario);
        } catch (Exception e) {
            throw new IllegalStateException("Error al registrar el usuario", e);
        }
    }

    @Override
    public Usuario autenticarUsuario(String email, String contrasena) {
        if (email == null || contrasena == null) {
            throw new IllegalArgumentException("Correo y contraseña son requeridos");
        }

        try {
            Optional<Usuario> usuarioOpt = repositorioUsuario.buscarPorCorreo(email);
            if (usuarioOpt.isPresent() && usuarioOpt.get().getContrasena().equals(contrasena)) {
                return usuarioOpt.get();
            }
            return null;
        } catch (Exception e) {
            throw new IllegalStateException("Error al autenticar usuario", e);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }

        try {
            Usuario usuario = repositorioUsuario.obtenerPorId(id);
            if (usuario == null) {
                throw new IllegalArgumentException("No se encontró el usuario con ID: " + id);
            }
            return usuario;
        } catch (Exception e) {
            throw new IllegalStateException("Error al obtener usuario por ID: " + id, e);
        }
    }

    @Override
    public boolean actualizarPerfil(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;
        try {
            repositorioUsuario.guardar(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String id) {
        if (id == null) return false;

        try {
            if (!repositorioUsuario.existeUsuario(id)) return false;
            repositorioUsuario.eliminar(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        try {
            return repositorioUsuario.listarTodos();
        } catch (Exception e) {
            throw new IllegalStateException("Error al listar usuarios", e);
        }
    }

    @Override
    public boolean existeCorreo(String email) {
        if (email == null) return false;
        try {
            return repositorioUsuario.buscarPorCorreo(email).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void recuperarContrasena(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Correo requerido");
        }

        try {
            if (!existeCorreo(email)) {
                throw new IllegalArgumentException("Correo no registrado");
            }
            // Lógica de recuperación
        } catch (Exception e) {
            throw new IllegalStateException("Error al procesar recuperación de contraseña", e);
        }
    }

    @Override
    public boolean cambiarContrasena(String id, String nuevaContrasena) {
        if (id == null || nuevaContrasena == null) return false;

        try {
            Usuario usuario = repositorioUsuario.obtenerPorId(id);
            if (usuario == null) return false;

            usuario.setPassword(nuevaContrasena);
            repositorioUsuario.guardar(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}