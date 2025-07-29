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
            return; // Validación de flujo: datos inválidos, no se lanza excepción
        }

        Optional<Usuario> existente = repositorioUsuario.buscarPorCorreo(usuario.getCorreo());
        if (existente.isPresent()) {
            return; // Usuario ya existe, no se lanza excepción
        }

        try {
            repositorioUsuario.guardar(usuario);
        } catch (Exception e) {
            // Error técnico real
            e.printStackTrace();
        }
    }
    @Override
    public Usuario autenticarUsuario(String correo, String contrasena) {
        Optional<Usuario> usuarioOpt = obtenerUsuarioPorCorreo(correo);

        // Evitamos usar .get() directamente
        if (usuarioOpt.isPresent() && contrasenaCorrecta(usuarioOpt.get(), contrasena)) {
            return usuarioOpt.get();
        }

        return null;
    }

    private Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return repositorioUsuario.buscarPorCorreo(correo);
    }

    private boolean contrasenaCorrecta(Usuario usuario, String contrasena) {
        return usuario.getContrasena().equals(contrasena);
    }



    @Override
    public Usuario obtenerUsuarioPorId(String id) {
        if (id == null) {
            return null;
        }

        try {
            return repositorioUsuario.obtenerPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        try {
            return repositorioUsuario.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public boolean existeCorreo(String email) {
        if (email == null) return false;

        try {
            return repositorioUsuario.buscarPorCorreo(email).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void recuperarContrasena(String email) {
        if (email == null || !existeCorreo(email)) {
            //  enviar correo
        }
    }

    @Override
    public boolean cambiarContrasena(String id, String nuevaContrasena) {
        if (id == null || nuevaContrasena == null) return false;

        try {
            Usuario usuario = repositorioUsuario.obtenerPorId(id);
            if (usuario == null) return false;

            usuario.setContrasena(nuevaContrasena);
            repositorioUsuario.guardar(usuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}