package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.plantapp.aplicacion.interfaces.IServicioUsuario;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioLoginDTO;
import com.planta.plantapp.dominio.usuario.modelo.dto.UsuarioRegistroDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioWebControllerTest {

    @Mock
    private IServicioUsuario usuarioServicio;

    @Mock
    private IServicioAutenticacion autenticacionServicio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UsuarioWebController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarLogin_deberiaAgregarDTOs() {
        String view = controller.mostrarLogin(model);
        verify(model).addAttribute(eq("loginDTO"), any(UsuarioLoginDTO.class));
        verify(model).addAttribute(eq("registroDTO"), any(UsuarioRegistroDTO.class));
        assertEquals("login/login", view);
    }

    @Test
    void procesarLogin_exitoso_redirigeAIndex() throws Exception {
        UsuarioLoginDTO dto = new UsuarioLoginDTO();
        dto.setCorreo("test@correo.com");
        dto.setContrasena("1234");

        // Usamos el constructor que recibe ID
        Usuario usuario = new Usuario(1L, "Test", "test@correo.com", "dabc1234");

        when(autenticacionServicio.autenticar(dto.getCorreo(), dto.getContrasena())).thenReturn(usuario);

        String result = controller.procesarLogin(dto, model, session);

        assertEquals("redirect:/web/index", result);
        verify(session).setAttribute("usuarioId", usuario.getId().toString());
        verify(session).setAttribute("usuarioNombre", usuario.getNombre());
        verify(session).setAttribute("usuarioCorreo", usuario.getCorreo());
    }


    @Test
    void procesarLogin_falla_muestraError() throws Exception {
        UsuarioLoginDTO dto = new UsuarioLoginDTO();
        dto.setCorreo("fallo@correo.com");
        dto.setContrasena("wrong");

        when(autenticacionServicio.autenticar(dto.getCorreo(), dto.getContrasena()))
                .thenThrow(new RuntimeException("Error"));

        String view = controller.procesarLogin(dto, model, session);
        verify(model).addAttribute("error", "Credenciales inválidas");
        assertEquals("login/login", view);
    }

    @Test
    void procesarRegistro_exitoso_redirigeALogin() {
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Test");
        dto.setCorreo("correo@dominio.com");
        dto.setContrasena("1234");

        when(passwordEncoder.encode(dto.getContrasena())).thenReturn("encoded1234");

        String view = controller.procesarRegistro(dto, model);

        verify(usuarioServicio).registrarUsuario(any());
        assertEquals("redirect:/web/login", view);
    }

    @Test
    void procesarRegistro_falla_muestraError() {
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Test");
        dto.setCorreo("correo@dominio.com");
        dto.setContrasena("1234");

        when(passwordEncoder.encode(dto.getContrasena())).thenReturn("encoded1234");
        doThrow(new RuntimeException("Error")).when(usuarioServicio).registrarUsuario(any());

        String view = controller.procesarRegistro(dto, model);

        verify(model).addAttribute("error", "Error al registrar usuario");
        assertEquals("login/login", view);
    }

    @Test
    void index_sinSesion_redirigeLogin() {
        when(session.getAttribute("usuarioNombre")).thenReturn(null);
        when(session.getAttribute("usuarioCorreo")).thenReturn(null);

        String view = controller.index(session, model);
        assertEquals("redirect:/web/login", view);
    }

    @Test
    void index_conSesion_muestraIndex() {
        when(session.getAttribute("usuarioNombre")).thenReturn("Test");
        when(session.getAttribute("usuarioCorreo")).thenReturn("test@correo.com");

        String view = controller.index(session, model);
        verify(model).addAttribute("usuarioNombre", "Test");
        verify(model).addAttribute("usuarioCorreo", "test@correo.com");
        assertEquals("login/index", view);
    }

    @Test
    void obtenerUsuarioActual_sesionInvalida_devuelveUnauthorized() {
        when(session.getAttribute("usuarioId")).thenReturn(null);

        var response = controller.obtenerUsuarioActual(session);
        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test

    void obtenerUsuarioActual_sesionValida_devuelveUsuario() {
        when(session.getAttribute("usuarioId")).thenReturn("1");
        when(session.getAttribute("usuarioNombre")).thenReturn("Test");
        when(session.getAttribute("usuarioCorreo")).thenReturn("test@correo.com");

        // Constructor con ID
        Usuario usuario = new Usuario(1L, "Test", "test@correo.com", "dabc1234");
        when(usuarioServicio.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        var response = controller.obtenerUsuarioActual(session);
        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertEquals("1", body.get("id"));
        assertEquals("Test", body.get("nombre"));
        assertEquals("test@correo.com", body.get("correo"));
        assertEquals("USER", body.get("rol"));
    }
}
