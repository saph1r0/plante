package com.planta.userservice.aplicacion.servicios;

import com.planta.userservice.dominio.modelo.IUsuarioRepositorio;
import com.planta.userservice.dominio.modelo.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioUsuarioImplTest {

    @Mock
    private IUsuarioRepositorio repositorio;

    private ServicioUsuarioImpl servicio;

    private static final String PASSWORD_VALIDA = "password123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicio = new ServicioUsuarioImpl(repositorio);
    }

    // ================= REGISTRAR =================

    @Test
    void registrarUsuario_guardaCuandoNoExiste() {
        Usuario u = new Usuario(1L, "Ana", "ana@mail.com", PASSWORD_VALIDA);
        when(repositorio.buscarPorCorreo("ana@mail.com"))
                .thenReturn(Optional.empty());

        servicio.registrarUsuario(u);

        verify(repositorio).guardar(u);
    }

    @Test
    void registrarUsuario_noHaceNadaSiUsuarioNull() {
        servicio.registrarUsuario(null);

        verify(repositorio, never()).guardar(any());
    }

    @Test
    void registrarUsuario_noGuardaSiCorreoExiste() {
        Usuario u = new Usuario(1L, "Ana", "ana@mail.com", PASSWORD_VALIDA);
        when(repositorio.buscarPorCorreo("ana@mail.com"))
                .thenReturn(Optional.of(u));

        servicio.registrarUsuario(u);

        verify(repositorio, never()).guardar(any());
    }

    // ================= AUTENTICAR =================

    @Test
    void autenticarUsuario_retornaUsuarioSiCredencialesValidas() {
        Usuario u = new Usuario(1L, "Luis", "l@mail.com", PASSWORD_VALIDA);
        when(repositorio.buscarPorCorreo("l@mail.com"))
                .thenReturn(Optional.of(u));

        Usuario res = servicio.autenticarUsuario("l@mail.com", PASSWORD_VALIDA);

        assertNotNull(res);
        assertEquals("Luis", res.getNombre());
    }

    @Test
    void autenticarUsuario_retornaNullSiPasswordIncorrecta() {
        Usuario u = new Usuario(1L, "Luis", "l@mail.com", PASSWORD_VALIDA);
        when(repositorio.buscarPorCorreo("l@mail.com"))
                .thenReturn(Optional.of(u));

        Usuario res = servicio.autenticarUsuario("l@mail.com", "incorrecta123");

        assertNull(res);
    }

    @Test
    void autenticarUsuario_retornaNullSiUsuarioNoExiste() {
        when(repositorio.buscarPorCorreo("x@mail.com"))
                .thenReturn(Optional.empty());

        Usuario res = servicio.autenticarUsuario("x@mail.com", PASSWORD_VALIDA);

        assertNull(res);
    }

    // ================= OBTENER POR ID =================

    @Test
    void obtenerUsuarioPorId_retornaUsuario() {
        Usuario u = new Usuario(1L, "A", "a@mail.com", PASSWORD_VALIDA);
        when(repositorio.obtenerPorId(1L)).thenReturn(u);

        Usuario res = servicio.obtenerUsuarioPorId(1L);

        assertNotNull(res);
    }

    @Test
    void obtenerUsuarioPorId_retornaNullSiIdNull() {
        assertNull(servicio.obtenerUsuarioPorId(null));
    }

    @Test
    void obtenerUsuarioPorId_retornaNullSiExcepcion() {
        when(repositorio.obtenerPorId(1L))
                .thenThrow(new RuntimeException());

        Usuario res = servicio.obtenerUsuarioPorId(1L);

        assertNull(res);
    }

    // ================= ACTUALIZAR =================

    @Test
    void actualizarPerfil_retornaTrue() {
        Usuario u = new Usuario(1L, "A", "a@mail.com", PASSWORD_VALIDA);

        boolean res = servicio.actualizarPerfil(u);

        assertTrue(res);
        verify(repositorio).guardar(u);
    }

    @Test
    void actualizarPerfil_retornaFalseSiUsuarioNull() {
        assertFalse(servicio.actualizarPerfil(null));
    }

    @Test
    void actualizarPerfil_retornaFalseSiIdNull() {
        Usuario u = new Usuario(null, "A", "a@mail.com", PASSWORD_VALIDA);

        assertFalse(servicio.actualizarPerfil(u));
    }

    @Test
    void actualizarPerfil_retornaFalseSiExcepcion() {
        Usuario u = new Usuario(1L, "A", "a@mail.com", PASSWORD_VALIDA);
        doThrow(new RuntimeException()).when(repositorio).guardar(u);

        boolean res = servicio.actualizarPerfil(u);

        assertFalse(res);
    }

    // ================= ELIMINAR =================

    @Test
    void eliminarUsuario_retornaTrue() {
        when(repositorio.existeUsuario(1L)).thenReturn(true);

        boolean res = servicio.eliminarUsuario(1L);

        assertTrue(res);
        verify(repositorio).eliminar(1L);
    }

    @Test
    void eliminarUsuario_retornaFalseSiNoExiste() {
        when(repositorio.existeUsuario(2L)).thenReturn(false);

        boolean res = servicio.eliminarUsuario(2L);

        assertFalse(res);
    }

    @Test
    void eliminarUsuario_retornaFalseSiIdNull() {
        assertFalse(servicio.eliminarUsuario(null));
    }

    @Test
    void eliminarUsuario_retornaFalseSiExcepcion() {
        when(repositorio.existeUsuario(1L))
                .thenThrow(new RuntimeException());

        boolean res = servicio.eliminarUsuario(1L);

        assertFalse(res);
    }

    // ================= LISTAR =================

    @Test
    void listarUsuarios_retornaLista() {
        when(repositorio.listarTodos())
                .thenReturn(List.of(
                        new Usuario(1L, "A", "a@mail.com", PASSWORD_VALIDA),
                        new Usuario(2L, "B", "b@mail.com", PASSWORD_VALIDA)
                ));

        List<Usuario> res = servicio.listarUsuarios();

        assertEquals(2, res.size());
    }

    @Test
    void listarUsuarios_retornaListaVaciaSiExcepcion() {
        when(repositorio.listarTodos())
                .thenThrow(new RuntimeException());

        List<Usuario> res = servicio.listarUsuarios();

        assertTrue(res.isEmpty());
    }

    // ================= EXISTE CORREO =================

    @Test
    void existeCorreo_retornaTrue() {
        when(repositorio.buscarPorCorreo("a@mail.com"))
                .thenReturn(Optional.of(
                        new Usuario(1L, "Ana", "a@mail.com", PASSWORD_VALIDA)
                ));

        assertTrue(servicio.existeCorreo("a@mail.com"));
    }

    @Test
    void existeCorreo_retornaFalseSiNoExiste() {
        when(repositorio.buscarPorCorreo("a@mail.com"))
                .thenReturn(Optional.empty());

        assertFalse(servicio.existeCorreo("a@mail.com"));
    }

    @Test
    void existeCorreo_retornaFalseSiEmailNull() {
        assertFalse(servicio.existeCorreo(null));
    }

    @Test
    void existeCorreo_retornaFalseSiExcepcion() {
        when(repositorio.buscarPorCorreo("a@mail.com"))
                .thenThrow(new RuntimeException());

        assertFalse(servicio.existeCorreo("a@mail.com"));
    }

    // ================= CAMBIAR CONTRASEÑA =================

    @Test
    void cambiarContrasena_retornaTrue() {
        Usuario u = new Usuario(1L, "A", "a@mail.com", PASSWORD_VALIDA);
        when(repositorio.obtenerPorId(1L)).thenReturn(u);

        boolean res = servicio.cambiarContrasena(1L, "nuevaPassword123");

        assertTrue(res);
        assertEquals("nuevaPassword123", u.getContrasena());
        verify(repositorio).guardar(u);
    }

    @Test
    void cambiarContrasena_retornaFalseSiIdNull() {
        assertFalse(servicio.cambiarContrasena(null, "x12345678"));
    }

    @Test
    void cambiarContrasena_retornaFalseSiPasswordNull() {
        assertFalse(servicio.cambiarContrasena(1L, null));
    }

    @Test
    void cambiarContrasena_retornaFalseSiUsuarioNull() {
        when(repositorio.obtenerPorId(1L)).thenReturn(null);

        assertFalse(servicio.cambiarContrasena(1L, "x12345678"));
    }

    @Test
    void cambiarContrasena_retornaFalseSiExcepcion() {
        when(repositorio.obtenerPorId(1L))
                .thenThrow(new RuntimeException());

        assertFalse(servicio.cambiarContrasena(1L, "x12345678"));
    }
}
