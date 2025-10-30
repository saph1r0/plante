package com.planta.plantapp.infraestructura.repositorio.mysql;

import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import com.planta.plantapp.infraestructura.entidad.UsuarioEntidad;
import com.planta.plantapp.infraestructura.repositorio.mysql.jpa.UsuarioJpaRepositorio;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositorioImplTest {

    @Mock
    private UsuarioJpaRepositorio usuarioJpaRepositorio;

    @InjectMocks
    private UsuarioRepositorioImpl usuarioRepositorioImpl;

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
    void obtenerPorId_deberiaRetornarUsuarioSiExiste() {
        UsuarioEntidad entidad = new UsuarioEntidad(1L, "Juan", "juan@gmail.com", "pass123S");
        when(usuarioJpaRepositorio.findById("1")).thenReturn(Optional.of(entidad));

        Usuario usuario = usuarioRepositorioImpl.obtenerPorId("1");

        assertNotNull(usuario);
        assertEquals("Juan", usuario.getNombre());
        verify(usuarioJpaRepositorio, times(1)).findById("1");
    }

    @Test
    void obtenerPorId_deberiaRetornarNullSiNoExiste() {
        when(usuarioJpaRepositorio.findById("99")).thenReturn(Optional.empty());

        Usuario usuario = usuarioRepositorioImpl.obtenerPorId("99");

        assertNull(usuario);
        verify(usuarioJpaRepositorio, times(1)).findById("99");
    }

    @Test
    void listarTodos_deberiaRetornarListaDeUsuarios() {
        List<UsuarioEntidad> entidades = List.of(
                new UsuarioEntidad(1L, "Ana", "ana@gmail.com", "pass234651"),
                new UsuarioEntidad(2L, "Luis", "luis@gmail.com", "pass21234")
        );
        when(usuarioJpaRepositorio.findAll()).thenReturn(entidades);

        List<Usuario> usuarios = usuarioRepositorioImpl.listarTodos();

        assertEquals(2L, usuarios.size());
        assertEquals("Ana", usuarios.get(0).getNombre());
        verify(usuarioJpaRepositorio, times(1)).findAll();
    }

    @Test
    void guardar_deberiaLlamarSaveDelRepositorio() {
        Usuario usuario = new Usuario(1L, "Pedro", "pedro@gmail.com", "clave12234653");

        usuarioRepositorioImpl.guardar(usuario);

        verify(usuarioJpaRepositorio, times(1)).save(any(UsuarioEntidad.class));
    }

    @Test
    void eliminar_deberiaLlamarDeleteByIdDelRepositorio() {
        usuarioRepositorioImpl.eliminar("1");
        verify(usuarioJpaRepositorio, times(1)).deleteById("1");
    }

    @Test
    void buscarPorCorreo_deberiaRetornarUsuarioSiExiste() {
        UsuarioEntidad entidad = new UsuarioEntidad(1L, "Sofia", "sofia@gmail.com", "clave1234567");
        when(usuarioJpaRepositorio.findByCorreo("sofia@gmail.com")).thenReturn(Optional.of(entidad));

        Optional<Usuario> resultado = usuarioRepositorioImpl.buscarPorCorreo("sofia@gmail.com");

        assertTrue(resultado.isPresent());
        assertEquals("Sofia", resultado.get().getNombre());
        verify(usuarioJpaRepositorio, times(1)).findByCorreo("sofia@gmail.com");
    }

    @Test
    void existeUsuario_deberiaRetornarTrueSiExiste() {
        when(usuarioJpaRepositorio.existsById("1")).thenReturn(true);

        assertTrue(usuarioRepositorioImpl.existeUsuario("1"));
        verify(usuarioJpaRepositorio, times(1)).existsById("1");
    }

    @Test
    void existeUsuario_deberiaRetornarFalseSiNoExiste() {
        when(usuarioJpaRepositorio.existsById("2")).thenReturn(false);

        assertFalse(usuarioRepositorioImpl.existeUsuario("2"));
        verify(usuarioJpaRepositorio, times(1)).existsById("2");
    }
}
