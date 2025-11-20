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
        if (usuario == null || usuario.getCorreo() == null) return;

        Optional<Usuario> existente = repositorioUsuario.buscarPorCorreo(usuario.getCorreo());
        if (existente.isPresent()) return;

        repositorioUsuario.guardar(usuario);
    }

    @Override
    public Usuario autenticarUsuario(String correo, String contrasena) {
        Optional<Usuario> usuarioOpt = repositorioUsuario.buscarPorCorreo(correo);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getContrasena().equals(contrasena)) {
            return usuarioOpt.get();
        }

        return null;
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        if (id == null) return null;

        try {
            return repositorioUsuario.obtenerPorId(id);
        } catch (Exception e) {
            return null;
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
    public boolean eliminarUsuario(Long id) {
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
            return List.of();
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
        // lógica real se implementaría luego
    }

    @Override
    public boolean cambiarContrasena(Long id, String nuevaContrasena) {
        if (id == null || nuevaContrasena == null) return false;

        try {
            Usuario usuario = repositorioUsuario.obtenerPorId(id);
            if (usuario == null) return false;

            usuario.setContrasena(nuevaContrasena);
            repositorioUsuario.guardar(usuario);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
