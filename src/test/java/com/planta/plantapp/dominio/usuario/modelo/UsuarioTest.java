package com.planta.plantapp.dominio.usuario.modelo;

import com.planta.userservice.dominio.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private com.planta.userservice.dominio.modelo.Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new com.planta.userservice.dominio.modelo.Usuario(1L, "Juan", "juan@gmail.com", "abc12345");
    }

    // =========================
    // CREACIÓN Y VALIDACIONES
    // =========================

    @Test
    void crearUsuarioValido_deberiaCrearCorrectamente() {
        assertEquals("Juan", usuarioValido.getNombre());
        assertEquals("juan@gmail.com", usuarioValido.getCorreo());
        assertFalse(usuarioValido.isActivo());
        assertNotNull(usuarioValido.getFechaCreacion());
        assertNull(usuarioValido.getFechaUltimoAcceso());
    }

    @Test
    void crearUsuario_conNombreNulo_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new com.planta.userservice.dominio.modelo.Usuario(1L, null, "correo@gmail.com", "abc12345"));
    }

    @Test
    void crearUsuario_conNombreConNumeros_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new com.planta.userservice.dominio.modelo.Usuario(1L, "Juan123", "correo@gmail.com", "abc12345"));
    }

    @Test
    void crearUsuario_conCorreoInvalido_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new com.planta.userservice.dominio.modelo.Usuario(1L, "Juan", "correo#gmail.com", "abc12345"));
    }

    @Test
    void crearUsuario_conContrasenaCorta_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new com.planta.userservice.dominio.modelo.Usuario(1L, "Juan", "correo@gmail.com", "abc12"));
    }

    @Test
    void crearUsuario_conContrasenaSinNumero_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new com.planta.userservice.dominio.modelo.Usuario(1L, "Juan", "correo@gmail.com", "abcdefgh"));
    }

    // =========================
    // NORMALIZACIÓN DE DATOS
    // =========================

    @Test
    void correoConMayusculas_deberiaNormalizarse() {
        com.planta.userservice.dominio.modelo.Usuario u = new com.planta.userservice.dominio.modelo.Usuario(1L, "Juan", "JUAN@GMAIL.COM", "abc12345");
        assertEquals("juan@gmail.com", u.getCorreo());
    }

    @Test
    void nombreYContrasenaConEspacios_deberianHacerseTrim() {
        com.planta.userservice.dominio.modelo.Usuario u = new com.planta.userservice.dominio.modelo.Usuario(1L, "  Juan  ", "juan@gmail.com", "  abc12345  ");
        assertEquals("Juan", u.getNombre());
        assertEquals("abc12345", u.getContrasena());
    }

    // =========================
    // ACTIVACIÓN DE CUENTA
    // =========================

    @Test
    void activarCuenta_deberiaCambiarEstadoAActivo() {
        usuarioValido.activarCuenta();
        assertTrue(usuarioValido.isActivo());
        assertNotNull(usuarioValido.getFechaUltimoAcceso());
    }

    @Test
    void activarCuenta_yaActiva_deberiaLanzarExcepcion() {
        usuarioValido.activarCuenta();
        assertThrows(IllegalStateException.class, usuarioValido::activarCuenta);
    }

    // =========================
    // CONSTRUCTORES
    // =========================

    @Test
    void constructorSecundario_deberiaAsignarCamposCorrectamente() {
        com.planta.userservice.dominio.modelo.Usuario usuario = new com.planta.userservice.dominio.modelo.Usuario("Maria", "maria@gmail.com", "clave1234");
        assertEquals("Maria", usuario.getNombre());
        assertEquals("maria@gmail.com", usuario.getCorreo());
        assertEquals("clave1234", usuario.getContrasena());
    }

    @Test
    void constructorSecundario_permiteUsuarioInvalido() {
        com.planta.userservice.dominio.modelo.Usuario usuario = new com.planta.userservice.dominio.modelo.Usuario("", "correo-invalido", "123");
        assertEquals("", usuario.getNombre());
    }

    // =========================
    // EQUALS / HASHCODE
    // =========================

    @Test
    void equalsYHashCode_deberianSerConsistentes() {
        com.planta.userservice.dominio.modelo.Usuario u1 = new com.planta.userservice.dominio.modelo.Usuario(1L, "Ana", "ana@gmail.com", "pass1234");
        com.planta.userservice.dominio.modelo.Usuario u2 = new com.planta.userservice.dominio.modelo.Usuario(1L, "Ana", "ana@gmail.com", "pass1234");
        com.planta.userservice.dominio.modelo.Usuario u3 = new com.planta.userservice.dominio.modelo.Usuario(2L, "Ana", "ana@gmail.com", "pass1234");

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotEquals(u1, u3);
    }

    // =========================
    // GETTERS / SETTERS
    // =========================

    @Test
    void settersYGetters_deberianFuncionarCorrectamente() {
        com.planta.userservice.dominio.modelo.Usuario u = new com.planta.userservice.dominio.modelo.Usuario(1L, "Luis", "luis@gmail.com", "clave1234");

        u.setNombreCompleto("Luis Perez");
        u.setActivo(true);
        u.setContrasena("nueva123");
        u.setFechaCreacion(LocalDateTime.now());
        u.setFechaUltimoAcceso(LocalDateTime.now());
        u.setPlantas(Collections.emptyList());

        assertEquals("Luis Perez", u.getNombre());
        assertTrue(u.isActivo());
        assertEquals("nueva123", u.getContrasena());
        assertNotNull(u.getFechaCreacion());
        assertNotNull(u.getFechaUltimoAcceso());
        assertNotNull(u.getPlantas());
    }

    // =========================
    // SEGURIDAD / ReDoS
    // =========================

    @Test
    void nombreMuyLargo_noDebeColgarse() {
        String nombreLargo = "a".repeat(10_000);
        assertDoesNotThrow(() ->
                new Usuario(1L, nombreLargo, "test@gmail.com", "abc12345"));
    }
}