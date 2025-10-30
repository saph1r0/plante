package com.planta.plantapp.dominio.usuario.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new Usuario(1L, "Juan", "juan@gmail.com", "abc12345");
    }

    //  Caso 1: Usuario válido
    @Test
    void crearUsuarioValido_deberiaCrearCorrectamente() {
        assertEquals("Juan", usuarioValido.getNombre());
        assertEquals("juan@gmail.com", usuarioValido.getCorreo());
        assertFalse(usuarioValido.isActivo());
        assertNotNull(usuarioValido.getFechaCreacion());
    }

    //  Caso 2: Nombre nulo
    @Test
    void crearUsuario_conNombreNulo_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(1L, null, "correo@gmail.com", "abc12345"));
    }

    //  Caso 3: Nombre con números
    @Test
    void crearUsuario_conNombreConNumeros_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(1L, "Juan123", "correo@gmail.com", "abc12345"));
    }

    //  Caso 4: Correo inválido
    @Test
    void crearUsuario_conCorreoInvalido_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(1L, "Juan", "correo#gmail.com", "abc12345"));
    }

    //  Caso 5: Contraseña corta
    @Test
    void crearUsuario_conContrasenaCorta_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(1L, "Juan", "correo@gmail.com", "abc12"));
    }

    //  Caso 6: Contraseña sin número
    @Test
    void crearUsuario_conContrasenaSinNumero_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(1L, "Juan", "correo@gmail.com", "abcdefgh"));
    }

    // ⚙ Caso 7: Activar cuenta
    @Test
    void activarCuenta_deberiaCambiarEstadoAActivo() {
        usuarioValido.activarCuenta();
        assertTrue(usuarioValido.isActivo());
        assertNotNull(usuarioValido.getFechaUltimoAcceso());
    }

    //  Caso 8: Activar cuenta ya activa
    @Test
    void activarCuenta_yaActiva_deberiaLanzarExcepcion() {
        usuarioValido.activarCuenta();
        assertThrows(IllegalStateException.class, usuarioValido::activarCuenta);
    }
    @Test
    void constructorSecundario_deberiaAsignarCamposCorrectamente() {
        Usuario usuario = new Usuario("Maria", "maria@gmail.com", "clave1234");
        assertEquals("Maria", usuario.getNombre());
        assertEquals("maria@gmail.com", usuario.getCorreo());
        assertEquals("clave1234", usuario.getContrasena());
    }

    @Test
    void equalsYHashCode_deberianSerConsistentes() {
        Usuario u1 = new Usuario(1L, "Ana", "ana@gmail.com", "pass1234");
        Usuario u2 = new Usuario(1L, "Ana", "ana@gmail.com", "pass1234");
        Usuario u3 = new Usuario(2L, "Ana", "ana@gmail.com", "pass1234");

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotEquals(u1, u3);
    }

    @Test
    void settersYGetters_deberianFuncionarCorrectamente() {
        Usuario u = new Usuario(1L, "Luis", "luis@gmail.com", "clave1234");

        u.setNombreCompleto("Luis Perez");
        u.setActivo(true);
        u.setContrasena("nueva123");
        u.setFechaCreacion(LocalDateTime.now());
        u.setFechaUltimoAcceso(LocalDateTime.now());

        assertEquals("Luis Perez", u.getNombre());
        assertTrue(u.isActivo());
        assertEquals("nueva123", u.getContrasena());
        assertNotNull(u.getFechaCreacion());
        assertNotNull(u.getFechaUltimoAcceso());
    }

}
