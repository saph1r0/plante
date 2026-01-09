package com.planta.plantapp.presentacion.controlador;
import org.springframework.context.annotation.Profile;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioLoginDTO;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioRegistroDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;
@Profile("no-usar")
@Controller
@RequestMapping("/web")
public class UsuarioWebController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioWebController.class);

    // Atributos de sesión
    private static final String ATTR_LOGIN_DTO = "loginDTO";
    private static final String ATTR_REGISTRO_DTO = "registroDTO";
    private static final String ATTR_USUARIO_ID = "usuarioId";
    private static final String ATTR_USUARIO_NOMBRE = "usuarioNombre";
    private static final String ATTR_USUARIO_CORREO = "usuarioCorreo";
    private static final String LOGIN_VIEW = "login/login";

    private static final String KEY_ERROR = "error";

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
        model.addAttribute(ATTR_LOGIN_DTO, new UsuarioLoginDTO());
        model.addAttribute(ATTR_REGISTRO_DTO, new UsuarioRegistroDTO());
        return LOGIN_VIEW;
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute(ATTR_LOGIN_DTO) UsuarioLoginDTO dto,
                                Model model, HttpSession session) {

        try {
            Usuario u = autenticacionServicio.autenticar(dto.getCorreo(), dto.getContrasena());

            if (u != null) {
                session.setAttribute(ATTR_USUARIO_ID, u.getId().toString());
                session.setAttribute(ATTR_USUARIO_NOMBRE, u.getNombre());
                session.setAttribute(ATTR_USUARIO_CORREO, u.getCorreo());

                return "redirect:/web/index";
            }

        } catch (Exception e) {
            logger.warn("Error login {}: {}", dto.getCorreo(), e.getMessage());
        }

        model.addAttribute(KEY_ERROR, "Credenciales inválidas");
        return LOGIN_VIEW;
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute(ATTR_REGISTRO_DTO) UsuarioRegistroDTO dto, Model model) {

        try {
            String contrasenaEncriptada = passwordEncoder.encode(dto.getContrasena());
            Usuario nuevo = new Usuario(dto.getNombre(), dto.getCorreo(), contrasenaEncriptada);

            usuarioServicio.registrarUsuario(nuevo);
            return "redirect:/web/login";

        } catch (Exception e) {
            model.addAttribute(KEY_ERROR, "Error al registrar usuario");
            return LOGIN_VIEW;
        }
    }

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {

        String nombre = (String) session.getAttribute(ATTR_USUARIO_NOMBRE);
        String correo = (String) session.getAttribute(ATTR_USUARIO_CORREO);

        if (nombre == null || correo == null) {
            return "redirect:/web/login";
        }

        model.addAttribute(ATTR_USUARIO_NOMBRE, nombre);
        model.addAttribute(ATTR_USUARIO_CORREO, correo);

        return "login/index";
    }

    @GetMapping("/usuario-actual")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerUsuarioActual(HttpSession session) {

        try {
            String usuarioId = (String) session.getAttribute(ATTR_USUARIO_ID);
            String nombre = (String) session.getAttribute(ATTR_USUARIO_NOMBRE);
            String correo = (String) session.getAttribute(ATTR_USUARIO_CORREO);

            if (usuarioId != null && nombre != null && correo != null) {

                obtenerUsuarioSeguro(usuarioId);

                Map<String, Object> data = construirRespuestaUsuarioSesion(usuarioId, nombre, correo);
                return ResponseEntity.ok(data);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(KEY_ERROR, "No hay usuario autenticado"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(KEY_ERROR, "Error interno del servidor"));
        }
    }

    // ----------------------------
    // MÉTODOS PRIVADOS
    // ----------------------------

    private Usuario obtenerUsuarioSeguro(String idStr) {
        try {
            Long id = Long.parseLong(idStr);
            return usuarioServicio.obtenerUsuarioPorId(id);
        } catch (Exception e) {
            logger.warn("Usuario no encontrado: {}", idStr);
            return null;
        }
    }

    private Map<String, Object> construirRespuestaUsuarioSesion(String usuarioId,
                                                                String nombre,
                                                                String correo) {
        Map<String, Object> usuarioData = new HashMap<>();
        usuarioData.put("id", usuarioId);
        usuarioData.put("nombre", nombre);
        usuarioData.put("correo", correo);
        usuarioData.put("rol", "USER");
        return usuarioData;
    }
}
