package com.planta.userservice.presentacion.controlador;

import com.planta.userservice.aplicacion.interfaces.IServicioUsuario;
import com.planta.userservice.dominio.modelo.Usuario;
import com.planta.userservice.infraestructura.seguridad.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private final IServicioUsuario usuarioServicio;
    private final JwtService jwtService;

    public UsuarioController(IServicioUsuario usuarioServicio,
                             JwtService jwtService) {
        this.usuarioServicio = usuarioServicio;
        this.jwtService = jwtService;
    }

    // DTO para respuesta de usuario
    public static class UsuarioResponseDTO {
        private Long id;
        private String nombre;
        private String correo;

        public UsuarioResponseDTO(Long id, String nombre, String correo) {
            this.id = id;
            this.nombre = nombre;
            this.correo = correo;
        }

        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getCorreo() { return correo; }
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getMe(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extraer token del header
            String token = authHeader.replace("Bearer ", "");

            // Validar token
            if (!jwtService.esTokenValido(token)) {
                return ResponseEntity.status(401).build();
            }

            // Obtener ID de usuario desde token
            Long userId = jwtService.obtenerUserId(token);

            // Consultar usuario en DB
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(userId);

            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            // Devolver DTO tipado
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getCorreo()
            );

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
