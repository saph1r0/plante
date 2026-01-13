package com.planta.userservice.aplicacion.servicios;

import com.planta.userservice.dominio.modelo.IUsuarioRepositorio;
import com.planta.userservice.dominio.modelo.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioRegistroUsuarioImplTest {

    @Mock
    private IUsuarioRepositorio usuarioRepositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ServicioRegistroUsuarioImpl servicioRegistroUsuario;

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
    void registrar_deberiaRegistrarUsuarioCuandoCorreoNoExiste() {
        when(usuarioRepositorio.buscarPorCorreo("nuevo@gmail.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("12345678"))
                .thenReturn("hash1231");

        Usuario usuarioGuardado = new Usuario(
                1L,
                "Nuevo",
                "nuevo@gmail.com",
                "hash1231"
        );

        when(usuarioRepositorio.guardar(any(Usuario.class)))
                .thenReturn(usuarioGuardado);

        Usuario resultado = servicioRegistroUsuario.registrar(
                "Nuevo",
                "nuevo@gmail.com",
                "123456"
        );

        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("nuevo@gmail.com", resultado.getCorreo());

        verify(usuarioRepositorio).buscarPorCorreo("nuevo@gmail.com");
        verify(passwordEncoder).encode("123456");
        verify(usuarioRepositorio).guardar(any(Usuario.class));
    }

    @Test
    void registrar_deberiaLanzarExcepcionSiCorreoYaExiste() {
        Usuario existente = new Usuario(
                1L,
                "Sofia",
                "sofia@gmail.com",
                "hash1112112"
        );

        when(usuarioRepositorio.buscarPorCorreo("sofia@gmail.com"))
                .thenReturn(Optional.of(existente));

        assertThrows(IllegalStateException.class, () ->
                servicioRegistroUsuario.registrar(
                        "Sofia",
                        "sofia@gmail.com",
                        "123456"
                )
        );

        verify(usuarioRepositorio).buscarPorCorreo("sofia@gmail.com");
        verify(usuarioRepositorio, never()).guardar(any());
        verify(passwordEncoder, never()).encode(any());
    }
}

