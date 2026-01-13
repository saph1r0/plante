package com.planta.userservice.aplicacion.servicios;

import com.planta.userservice.dominio.modelo.IUsuarioRepositorio;
import com.planta.userservice.dominio.modelo.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.BadCredentialsException; // ✅ CAMBIO
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioAutenticacionImplTest {

    @Mock
    private IUsuarioRepositorio usuarioRepositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ServicioAutenticacionImpl servicioAutenticacion;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void autenticar_deberiaRetornarUsuarioCuandoCredencialesSonValidas() {
        Usuario usuario = new Usuario(1L, "Sofia", "sofia@gmail.com", "hash1234");
        when(usuarioRepositorio.buscarPorCorreo("sofia@gmail.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("12345678", "hash1234"))
                .thenReturn(true);

        Usuario resultado = servicioAutenticacion.autenticar("sofia@gmail.com", "12345678");

        assertNotNull(resultado);
        assertEquals("Sofia", resultado.getNombre());
        verify(usuarioRepositorio, times(1))
                .buscarPorCorreo("sofia@gmail.com");
    }

    @Test
    void autenticar_deberiaLanzarExcepcionCuandoPasswordEsIncorrecto() {
        Usuario usuario = new Usuario(1L, "Sofia", "sofia@gmail.com", "hash1234");
        when(usuarioRepositorio.buscarPorCorreo("sofia@gmail.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrong", "hash1234"))
                .thenReturn(false);

        assertThrows(BadCredentialsException.class, () ->
                servicioAutenticacion.autenticar("sofia@gmail.com", "wrong")
        );
    }

    @Test
    void autenticar_deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        when(usuarioRepositorio.buscarPorCorreo("noexiste@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () ->
                servicioAutenticacion.autenticar("noexiste@gmail.com", "1234")
        );
    }
}
