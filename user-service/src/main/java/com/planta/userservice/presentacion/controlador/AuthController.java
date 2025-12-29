package com.planta.userservice.presentacion.controlador;

import com.planta.userservice.aplicacion.interfaces.IServicioRegistroUsuario;
import com.planta.userservice.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.userservice.dominio.modelo.Usuario;
import com.planta.userservice.dominio.modelo.dto.UsuarioLoginDTO;
import com.planta.userservice.dominio.modelo.dto.UsuarioRegistroDTO;
import com.planta.userservice.infraestructura.seguridad.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IServicioRegistroUsuario registroServicio;
    private final IServicioAutenticacion autenticacionServicio;
    private final JwtService jwtService;

    public AuthController(IServicioRegistroUsuario registroServicio,
                          IServicioAutenticacion autenticacionServicio,
                          JwtService jwtService) {
        this.registroServicio = registroServicio;
        this.autenticacionServicio = autenticacionServicio;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioRegistroDTO dto) {

        Usuario usuario = registroServicio.registrar(
                dto.getNombre(),
                dto.getCorreo(),
                dto.getContrasena()
        );

        return ResponseEntity.ok(Map.of(
                "id", usuario.getId(),
                "correo", usuario.getCorreo()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO dto) {

        Usuario usuario = autenticacionServicio.autenticar(
                dto.getCorreo(),
                dto.getContrasena()
        );

        String token = jwtService.generarToken(
                usuario.getId(),
                usuario.getCorreo(),
                "USER"
        );

        return ResponseEntity.ok(Map.of(
                "token", token
        ));
    }
}
