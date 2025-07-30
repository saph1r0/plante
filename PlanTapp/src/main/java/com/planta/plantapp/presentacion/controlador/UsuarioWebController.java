package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioLoginDTO;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioRegistroDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/web")
public class UsuarioWebController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioWebController.class);

    // Constantes para atributos de modelo y sesión
    private static final String ATTR_LOGIN_DTO = "loginDTO";
    private static final String ATTR_REGISTRO_DTO = "registroDTO";
    private static final String ATTR_USUARIO_NOMBRE = "usuarioNombre";
    private static final String ATTR_USUARIO_CORREO = "usuarioCorreo";
    private static final String LOGIN_VIEW = "login/login";

    private final IServicioUsuario usuarioServicio;
    private final IServicioAutenticacion autenticacionServicio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioWebController(IServicioUsuario usuarioServicio,
                                IServicioAutenticacion autenticacionServicio,
                                PasswordEncoder passwordEncoder) {
        this.usuarioServicio = usuarioServicio;
        this.autenticacionServicio = autenticacionServicio;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        logger.debug("Cargando formulario de login");
        model.addAttribute(ATTR_LOGIN_DTO, new UsuarioLoginDTO());
        model.addAttribute(ATTR_REGISTRO_DTO, new UsuarioRegistroDTO());
        return LOGIN_VIEW;
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute(ATTR_LOGIN_DTO) UsuarioLoginDTO dto, Model model, HttpSession session) {
        logger.debug("Procesando login para: {}", dto.getCorreo());
        try {
            Usuario u = autenticacionServicio.autenticar(dto.getCorreo(), dto.getContrasena());
            if (u != null) {
                session.setAttribute(ATTR_USUARIO_NOMBRE, u.getNombre());
                session.setAttribute(ATTR_USUARIO_CORREO, u.getCorreo());
                logger.info("Login exitoso para usuario {}", u.getCorreo());
                return "redirect:/web/index";
            }
        } catch (Exception e) {
            logger.warn("Fallo de login para {}: {}", dto.getCorreo(), e.getMessage());
            model.addAttribute("error", "Credenciales inválidas");
            model.addAttribute(ATTR_LOGIN_DTO, new UsuarioLoginDTO());
            model.addAttribute(ATTR_REGISTRO_DTO, new UsuarioRegistroDTO());
        }
        return LOGIN_VIEW;
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute(ATTR_REGISTRO_DTO) UsuarioRegistroDTO dto, Model model) {
        try {
            logger.debug("Registrando nuevo usuario: {}", dto.getCorreo());
            String contrasenaEncriptada = passwordEncoder.encode(dto.getContrasena());
            Usuario nuevo = new Usuario(dto.getNombre(), dto.getCorreo(), contrasenaEncriptada);
            usuarioServicio.registrarUsuario(nuevo);
            logger.info("Usuario {} registrado correctamente", dto.getCorreo());
            return "redirect:/web/login";
        } catch (Exception e) {
            logger.error("Error al registrar usuario: {}", e.getMessage());
            model.addAttribute("error", "Error al registrar usuario");
            model.addAttribute(ATTR_LOGIN_DTO, new UsuarioLoginDTO());
            model.addAttribute(ATTR_REGISTRO_DTO, new UsuarioRegistroDTO());
            return LOGIN_VIEW;
        }
    }

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        String nombre = (String) session.getAttribute(ATTR_USUARIO_NOMBRE);
        String correo = (String) session.getAttribute(ATTR_USUARIO_CORREO);

        if (nombre == null || correo == null) {
            logger.warn("Intento de acceso a /index sin sesión válida");
            return "redirect:/web/login";
        }

        model.addAttribute(ATTR_USUARIO_NOMBRE, nombre);
        model.addAttribute(ATTR_USUARIO_CORREO, correo);
        return "login/index";  // index.html
    }
}
