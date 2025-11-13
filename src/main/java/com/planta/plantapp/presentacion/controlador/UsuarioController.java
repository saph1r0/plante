package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioDTO;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IServicioUsuario usuarioServicio;
    private final IServicioAutenticacion autenticacionServicio;

    public UsuarioController(IServicioUsuario usuarioServicio,
                             IServicioAutenticacion autenticacionServicio) {
        this.usuarioServicio = usuarioServicio;
        this.autenticacionServicio = autenticacionServicio;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestParam String nombre,
                                                   @RequestParam String correo,
                                                   @RequestParam String contrasena) {
        Usuario nuevoUsuario = new Usuario(nombre, correo, contrasena);
        usuarioServicio.registrarUsuario(nuevoUsuario);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String correo,
                                        @RequestParam String contrasena) {
        try {
            Usuario usuarioAutenticado = autenticacionServicio.autenticar(correo, contrasena);
            if (usuarioAutenticado != null) {
                UsuarioDTO dto = new UsuarioDTO(usuarioAutenticado.getId(),
                                                usuarioAutenticado.getCorreo());
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    public IServicioUsuario getUsuarioServicio() {
        return usuarioServicio;
    }
}
