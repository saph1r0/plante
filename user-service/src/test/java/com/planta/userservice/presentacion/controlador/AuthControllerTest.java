package com.planta.userservice.presentacion.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planta.userservice.aplicacion.interfaces.IServicioAutenticacion;
import com.planta.userservice.aplicacion.interfaces.IServicioRegistroUsuario;
import com.planta.userservice.dominio.modelo.Usuario;
import com.planta.userservice.dominio.modelo.dto.UsuarioLoginDTO;
import com.planta.userservice.dominio.modelo.dto.UsuarioRegistroDTO;
import com.planta.userservice.infraestructura.seguridad.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AuthControllerTest.TestSecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IServicioRegistroUsuario registroServicio;

    @MockitoBean
    private IServicioAutenticacion autenticacionServicio;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    @EnableWebSecurity
    public static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll()
                    );
            return http.build();
        }
    }

    @Test
    public void register_deberiaRegistrarUsuario() throws Exception {
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO("Ana", "ana@mail.com", "12345678");
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "hash");

        Mockito.when(registroServicio.registrar(dto.getNombre(), dto.getCorreo(), dto.getContrasena()))
                .thenReturn(usuario);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.correo").value("ana@mail.com"));
    }

    @Test
    public void login_deberiaAutenticarYRetornarToken() throws Exception {
        UsuarioLoginDTO dto = new UsuarioLoginDTO("ana@mail.com", "12345678");
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "hash");

        Mockito.when(autenticacionServicio.autenticar(dto.getCorreo(), dto.getContrasena()))
                .thenReturn(usuario);

        Mockito.when(jwtService.generarToken(usuario.getId(), usuario.getCorreo(), "USER"))
                .thenReturn("token123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }
}