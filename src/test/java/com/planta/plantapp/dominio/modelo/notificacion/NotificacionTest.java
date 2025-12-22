package com.planta.plantapp.dominio.modelo.notificacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion(
            "Test Titulo",
            "Test Mensaje",
            TipoNotificacion.INFORMATIVA,
            "user123"
        );
    }

    @Test
    void testConstructor() {
        assertNotNull(notificacion);
        assertEquals("Test Titulo", notificacion.getTitulo());
        assertEquals("Test Mensaje", notificacion.getMensaje());
        assertEquals(TipoNotificacion.INFORMATIVA, notificacion.getTipo());
        assertEquals("user123", notificacion.getUsuarioId());
        assertEquals(EstadoNotificacion.PENDIENTE, notificacion.getEstado());
        assertNotNull(notificacion.getFechaCreacion());
    }

    @Test
    void testConstructorVacio() {
        Notificacion n = new Notificacion();
        assertNotNull(n);
        assertNotNull(n.getFechaCreacion());
        assertEquals(EstadoNotificacion.PENDIENTE, n.getEstado());
    }

    @Test
    void testMarcarComoEnviada() {
        notificacion.marcarComoEnviada();
        assertEquals(EstadoNotificacion.ENVIADA, notificacion.getEstado());
        assertNotNull(notificacion.getFechaEnvio());
    }

    @Test
    void testMarcarComoLeida() {
        notificacion.marcarComoLeida();
        assertEquals(EstadoNotificacion.LEIDA, notificacion.getEstado());
    }

    @Test
    void testMarcarComoFallida() {
        notificacion.marcarComoFallida();
        assertEquals(EstadoNotificacion.FALLIDA, notificacion.getEstado());
    }

    @Test
    void testEstaPendiente() {
        assertTrue(notificacion.estaPendiente());
        notificacion.marcarComoEnviada();
        assertFalse(notificacion.estaPendiente());
    }

    @Test
    void testEstaEnviada() {
        assertFalse(notificacion.estaEnviada());
        notificacion.marcarComoEnviada();
        assertTrue(notificacion.estaEnviada());
    }

    @Test
    void testSetters() {
        notificacion.setId("notif123");
        notificacion.setTitulo("Nuevo Titulo");
        notificacion.setMensaje("Nuevo Mensaje");
        notificacion.setTipo(TipoNotificacion.ALERTA_URGENTE);
        notificacion.setEstado(EstadoNotificacion.LEIDA);
        notificacion.setUsuarioId("user456");
        notificacion.setReferenciaId("ref789");
        notificacion.setTipoReferencia(TipoReferencia.PLANTA);

        LocalDateTime fecha = LocalDateTime.now();
        notificacion.setFechaCreacion(fecha);
        notificacion.setFechaEnvio(fecha);

        assertEquals("notif123", notificacion.getId());
        assertEquals("Nuevo Titulo", notificacion.getTitulo());
        assertEquals("Nuevo Mensaje", notificacion.getMensaje());
        assertEquals(TipoNotificacion.ALERTA_URGENTE, notificacion.getTipo());
        assertEquals(EstadoNotificacion.LEIDA, notificacion.getEstado());
        assertEquals("user456", notificacion.getUsuarioId());
        assertEquals("ref789", notificacion.getReferenciaId());
        assertEquals(TipoReferencia.PLANTA, notificacion.getTipoReferencia());
        assertEquals(fecha, notificacion.getFechaCreacion());
        assertEquals(fecha, notificacion.getFechaEnvio());
    }
}

