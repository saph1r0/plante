package com.planta.userservice.presentacion.controlador;

import com.planta.userservice.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.userservice.aplicacion.interfaces.IServicioRegistroUsuario;
import com.planta.userservice.dominio.modelo.Usuario;
import com.planta.userservice.dominio.modelo.dto.UsuarioLoginDTO;
import com.planta.userservice.dominio.modelo.dto.UsuarioRegistroDTO;
import com.planta.userservice.infraestructura.seguridad.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private IServicioRegistroUsuario registroServicio;
    private IServicioAutenticacion autenticacionServicio;
    private JwtService jwtService;
    private AuthController controller;

    @BeforeEach
    void setUp() {
        registroServicio = mock(IServicioRegistroUsuario.class);
        autenticacionServicio = mock(IServicioAutenticacion.class);
        jwtService = mock(JwtService.class);
        controller = new AuthController(registroServicio, autenticacionServicio, jwtService);
    }

    @Test
    void registerRetornaUsuario() {
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO("Test", "test@mail.com", "12345678"); // ✅ 8 chars
        Usuario usuario = new Usuario(1L, "Test", "test@mail.com", "12345678");
        when(registroServicio.registrar(dto.getNombre(), dto.getCorreo(), dto.getContrasena())).thenReturn(usuario);

        ResponseEntity<?> response = controller.register(dto);
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertEquals(usuario.getId(), body.get("id"));
        assertEquals(usuario.getCorreo(), body.get("correo"));
    }

    @Test
    void loginRetornaToken() {
        UsuarioLoginDTO dto = new UsuarioLoginDTO("test@mail.com", "12345678"); // ✅ 8 chars
        Usuario usuario = new Usuario(1L, "Test", "test@mail.com", "12345678");
        when(autenticacionServicio.autenticar(dto.getCorreo(), dto.getContrasena())).thenReturn(usuario);
        when(jwtService.generarToken(usuario.getId(), usuario.getCorreo(), "USER")).thenReturn("token123");

        ResponseEntity<?> response = controller.login(dto);
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertEquals("token123", body.get("token"));
    }

}
