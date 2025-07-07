package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IServicioUsuario usuarioServicio;
    public UsuarioController(IServicioUsuario usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String nombre,
                                   @RequestParam String correo,
                                   @RequestParam String contrasena) {
        Usuario nuevoUsuario = new Usuario(nombre, correo, contrasena);  // sin ID
        usuarioServicio.registrarUsuario(nuevoUsuario);
        return "Usuario registrado correctamente";
    }

    @PostMapping("/login")
    public String autenticarUsuario(@RequestParam String email,
                                    @RequestParam String contrasena) {
        Usuario usuario = usuarioServicio.autenticarUsuario(email, contrasena);
        if (usuario != null) {
            return "Inicio de sesión exitoso";
        } else {
            return "Credenciales incorrectas";
        }
    }
    public Usuario obtenerUsuarioPorId(int id) {
        throw new UnsupportedOperationException("Método obtenerUsuarioPorId() no implementado aún.");
    }

    public boolean actualizarPerfil(Long usuarioId) {
        throw new UnsupportedOperationException("Método actualizarPerfil() no implementado aún.");
    }

    public boolean cambiarContrasena(int id, String nuevaContrasena) {
        throw new UnsupportedOperationException("Método cambiarContrasena() no implementado aún.");
    }

    public boolean eliminarUsuario(int id) {
        throw new UnsupportedOperationException("Método eliminarUsuario() no implementado aún.");
    }

    public IServicioUsuario getUsuarioServicio() {
        return usuarioServicio;
    }

}
