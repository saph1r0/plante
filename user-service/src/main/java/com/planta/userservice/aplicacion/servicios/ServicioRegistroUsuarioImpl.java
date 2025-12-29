package com.planta.userservice.aplicacion.servicios;


import com.planta.userservice.aplicacion.interfaces.IServicioRegistroUsuario;
import com.planta.userservice.dominio.modelo.IUsuarioRepositorio;
import com.planta.userservice.dominio.modelo.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioRegistroUsuarioImpl implements IServicioRegistroUsuario {

    private final IUsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public ServicioRegistroUsuarioImpl(IUsuarioRepositorio usuarioRepositorio,
                                       PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrar(String nombre, String correo, String contrasena) {

        if (usuarioRepositorio.buscarPorCorreo(correo).isPresent()) {
            throw new IllegalStateException("El correo ya está registrado");
        }

        String hash = passwordEncoder.encode(contrasena);
        Usuario usuario = new Usuario(nombre, correo, hash);

        return usuarioRepositorio.guardar(usuario); // 👈 CLAVE
    }
}
