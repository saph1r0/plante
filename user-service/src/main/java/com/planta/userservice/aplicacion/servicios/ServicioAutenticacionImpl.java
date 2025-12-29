package com.planta.userservice.aplicacion.servicios;

import com.planta.userservice.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.userservice.dominio.modelo.IUsuarioRepositorio;
import com.planta.userservice.dominio.modelo.Usuario;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacionImpl implements IServicioAutenticacion {

    private final IUsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public ServicioAutenticacionImpl(IUsuarioRepositorio usuarioRepositorio,
                                     PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario autenticar(String correo, String contrasena) {

        Usuario usuario = usuarioRepositorio.buscarPorCorreo(correo)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return usuario;
    }
}
