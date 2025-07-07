package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;

import java.util.List;

public class UsuarioController {

    private IServicioUsuario usuarioServicio;

    public UsuarioController(IServicioUsuario servicioUsuario) {
        this.usuarioServicio = servicioUsuario;
    }

    public boolean registrarUsuario(String nombre, String email, String contraseña) {
        throw new UnsupportedOperationException("Método registrarUsuario() no implementado aún.");
    }

    public Usuario autenticarUsuario(String email, String contraseña) {
        throw new UnsupportedOperationException("Método autenticarUsuario() no implementado aún.");
    }

    public Usuario obtenerUsuarioPorId(int id) {
        throw new UnsupportedOperationException("Método obtenerUsuarioPorId() no implementado aún.");
    }

    public boolean actualizarPerfil(Long usuarioId) {
        throw new UnsupportedOperationException("Método actualizarPerfil() no implementado aún.");
    }

    public boolean cambiarContraseña(int id, String nuevaContraseña) {
        throw new UnsupportedOperationException("Método cambiarContraseña() no implementado aún.");
    }

    public boolean eliminarUsuario(int id) {
        throw new UnsupportedOperationException("Método eliminarUsuario() no implementado aún.");
    }

    public IServicioUsuario getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(IServicioUsuario servicioUsuario) {
        this.usuarioServicio = servicioUsuario;
    }
}
