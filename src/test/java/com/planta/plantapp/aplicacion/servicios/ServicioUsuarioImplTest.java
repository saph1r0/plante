package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.usuario.IUsuarioRepositorio;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioUsuarioImplTest {

    @Mock
    private IUsuarioRepositorio repositorioMock;

    private ServicioUsuarioImpl servicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicio = new ServicioUsuarioImpl(repositorioMock);
    }

    @Test
    void registrarUsuario_deberiaGuardarCuandoNoExiste() {
        Usuario usuario = new Usuario(1L, "Ana", "ana@gmail.com", "12345678");
        when(repositorioMock.buscarPorCorreo("ana@gmail.com")).thenReturn(Optional.empty());

        servicio.registrarUsuario(usuario);

        verify(repositorioMock, times(1)).guardar(usuario);
    }

    @Test
    void registrarUsuario_noDebeGuardarSiUsuarioEsNull() {
        servicio.registrarUsuario(null);
        verify(repositorioMock, never()).guardar(any());
    }

    @Test
    void autenticarUsuario_retornaUsuarioSiCredencialesValidas() {
        Usuario usuario = new Usuario(1L, "Luis", "luis@gmail.com", "clave1234");
        when(repositorioMock.buscarPorCorreo("luis@gmail.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = servicio.autenticarUsuario("luis@gmail.com", "clave1234");

        assertNotNull(resultado);
        assertEquals("Luis", resultado.getNombre());
    }

    @Test
    void autenticarUsuario_retornaNullSiContrasenaIncorrecta() {
        Usuario usuario = new Usuario(1L, "Luis", "luis@gmail.com", "clave1234");
        when(repositorioMock.buscarPorCorreo("luis@gmail.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = servicio.autenticarUsuario("luis@gmail.com", "incorrecta");

        assertNull(resultado);
    }

    @Test
    void actualizarPerfil_deberiaGuardarYRetornarTrue() {
        Usuario usuario = new Usuario(1L, "Sara", "sara@gmail.com", "pass1234");
        boolean resultado = servicio.actualizarPerfil(usuario);

        assertTrue(resultado);
        verify(repositorioMock, times(1)).guardar(usuario);
    }

    @Test
    void eliminarUsuario_retornaTrueCuandoExiste() {
        when(repositorioMock.existeUsuario("1")).thenReturn(true);

        boolean resultado = servicio.eliminarUsuario("1");

        assertTrue(resultado);
        verify(repositorioMock, times(1)).eliminar("1");
    }

    @Test
    void eliminarUsuario_retornaFalseCuandoNoExiste() {
        when(repositorioMock.existeUsuario("99")).thenReturn(false);

        boolean resultado = servicio.eliminarUsuario("99");

        assertFalse(resultado);
        verify(repositorioMock, never()).eliminar("99");
    }

    @Test
    void listarUsuarios_retornaListaDeUsuarios() {
        List<Usuario> lista = List.of(
                new Usuario(1L, "A", "a@gmail.com", "12345678"),
                new Usuario(2L, "B", "b@gmail.com", "87654321")
        );
        when(repositorioMock.listarTodos()).thenReturn(lista);

        List<Usuario> resultado = servicio.listarUsuarios();

        assertEquals(2, resultado.size());
    }

    @Test
    void existeCorreo_retornaTrueSiExiste() {
        when(repositorioMock.buscarPorCorreo("mail@test.com"))
                .thenReturn(Optional.of(new Usuario(1L, "Test", "mail@test.com", "12345678")));

        boolean existe = servicio.existeCorreo("mail@test.com");

        assertTrue(existe);
    }

    @AfterEach
    void tearDown() {
        // Limpieza general si fuera necesario
        Mockito.reset(repositorioMock);
    }

}
