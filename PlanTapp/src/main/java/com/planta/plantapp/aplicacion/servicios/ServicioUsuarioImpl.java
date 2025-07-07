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
        Optional<Usuario> existente = repositorioUsuario.buscarPorCorreo(usuario.getEmail());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        repositorioUsuario.guardar(usuario);
    }


    @Override
    public Usuario autenticarUsuario(String email, String contrasena) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.buscarPorCorreo(email);
        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(contrasena)) {
            return usuarioOpt.get();
        }
        return null; // o lanzar excepción si quieres
    }


    @Override
    public Usuario obtenerUsuarioPorId(String id) {
        return repositorioUsuario.obtenerPorId(id);
    }

    @Override
    public boolean actualizarPerfil(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;
        repositorioUsuario.guardar(usuario);
        return true;
    }

    @Override
    public boolean eliminarUsuario(String id) {
        if (!repositorioUsuario.existeUsuario(id)) return false;
        repositorioUsuario.eliminar(id);
        return true;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.listarTodos();
    }

    @Override
    public boolean existeCorreo(String email) {
        return repositorioUsuario.buscarPorCorreo(email).isPresent();
    }

    @Override
    public void recuperarContrasena(String email) {
        if (!existeCorreo(email)) {
            throw new IllegalArgumentException("Correo no registrado");
        }
        // Aquí puedes poner la lógica real de recuperación
    }

    @Override
    public boolean cambiarContrasena(String id, String nuevaContrasena) {
        Usuario usuario = repositorioUsuario.obtenerPorId(id);
        if (usuario == null) return false;
        usuario.setPassword(nuevaContrasena);
        repositorioUsuario.guardar(usuario);
        return true;
    }
}
