package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.plantapp.dominio.usuario.IUsuarioRepositorio;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class ServicioAutenticacionImpl implements IServicioAutenticacion {

    private static final Logger logger = LoggerFactory.getLogger(ServicioAutenticacionImpl.class);

    private final IUsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public ServicioAutenticacionImpl(IUsuarioRepositorio usuarioRepositorio,
                                     PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario autenticar(String correo, String password) {
        logger.debug("=== AUTENTICACIÓN DEBUG ===");
        logger.debug("Email: {}", correo);
        logger.debug("Password recibido: {}", password);

        Optional<Usuario> usuarioOpt = usuarioRepositorio.buscarPorCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            logger.debug("Usuario encontrado: {}", usuario.getNombre());
            logger.debug("Hash en BD: {}", usuario.getContrasena());

            boolean passwordValido = passwordEncoder.matches(password, usuario.getContrasena());
            logger.debug("¿Password válido? {}", passwordValido);

            if (passwordValido) {
                logger.info("✅ Autenticación exitosa para {}", correo);
                return usuario;
            } else {
                logger.warn("❌ Password incorrecto para {}", correo);
            }
        } else {
            logger.warn("❌ Usuario no encontrado para {}", correo);
        }

        throw new AuthenticationCredentialsNotFoundException("Credenciales inválidas");
    }
}