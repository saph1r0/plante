package com.planta.plantapp.dominio.modelo.servicios;

import com.planta.plantapp.dominio.modelo.INotificacionRepositorio;
import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.notificacion.EstadoNotificacion;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EstrategiaEnvioNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioNotificacionDominioTest {

    @Mock
    private INotificacionRepositorio repositorio;

    @Mock
    private EstrategiaEnvioNotificacion estrategia1;

    @Mock
    private EstrategiaEnvioNotificacion estrategia2;

    private ServicioNotificacionDominio servicio;

    @BeforeEach
    void setUp() {
        List<EstrategiaEnvioNotificacion> estrategias = Arrays.asList(estrategia1, estrategia2);
        servicio = new ServicioNotificacionDominio(repositorio, estrategias);
    }

    @Test
    void testCrearYEnviarConExito() {
        // Arrange
        when(estrategia1.estaDisponible()).thenReturn(true);
        when(estrategia1.enviar(any())).thenReturn(true);
        doNothing().when(repositorio).guardar(any());

        // Act
        Notificacion resultado = servicio.crearYEnviar(
            "Titulo",
            "Mensaje",
            TipoNotificacion.INFORMATIVA,
            "user1",
            estrategia1
        );

        // Assert
        assertNotNull(resultado);
        assertEquals("Titulo", resultado.getTitulo());
        assertEquals(EstadoNotificacion.ENVIADA, resultado.getEstado());
        verify(repositorio, times(2)).guardar(any());
        verify(estrategia1).enviar(any());
    }

    @Test
    void testCrearYEnviarConFallo() {
        // Arrange
        when(estrategia1.estaDisponible()).thenReturn(true);
        when(estrategia1.enviar(any())).thenReturn(false);

        // Act
        Notificacion resultado = servicio.crearYEnviar(
            "Titulo",
            "Mensaje",
            TipoNotificacion.ALERTA_URGENTE,
            "user1",
            estrategia1
        );

        // Assert
        assertEquals(EstadoNotificacion.FALLIDA, resultado.getEstado());
        verify(repositorio, times(2)).guardar(any());
    }

    @Test
    void testCrearYEnviarSinEstrategia() {
        // Act
        Notificacion resultado = servicio.crearYEnviar(
            "Titulo",
            "Mensaje",
            TipoNotificacion.INFORMATIVA,
            "user1",
            null
        );

        // Assert
        assertNotNull(resultado);
        assertEquals(EstadoNotificacion.PENDIENTE, resultado.getEstado());
        verify(repositorio, times(1)).guardar(any());
    }

    @Test
    void testCrearYEnviarEstrategiaNoDisponible() {
        // Arrange
        when(estrategia1.estaDisponible()).thenReturn(false);

        // Act
        Notificacion resultado = servicio.crearYEnviar(
            "Titulo",
            "Mensaje",
            TipoNotificacion.INFORMATIVA,
            "user1",
            estrategia1
        );

        // Assert
        assertEquals(EstadoNotificacion.PENDIENTE, resultado.getEstado());
        verify(estrategia1, never()).enviar(any());
    }

    @Test
    void testProcesarNotificacionesPendientes() {
        // Arrange
        Notificacion n1 = new Notificacion("T1", "M1", TipoNotificacion.INFORMATIVA, "user1");
        Notificacion n2 = new Notificacion("T2", "M2", TipoNotificacion.ALERTA_URGENTE, "user1");

        when(repositorio.listarPendientes()).thenReturn(Arrays.asList(n1, n2));
        when(estrategia1.estaDisponible()).thenReturn(true);
        when(estrategia1.enviar(any())).thenReturn(true);

        // Act
        servicio.procesarNotificacionesPendientes();

        // Assert
        verify(repositorio).listarPendientes();
        verify(estrategia1, times(2)).enviar(any());
        verify(repositorio, times(2)).guardar(any());
    }

    @Test
    void testProcesarNotificacionesPendientesSinEstrategiasDisponibles() {
        // Arrange
        Notificacion n1 = new Notificacion("T1", "M1", TipoNotificacion.INFORMATIVA, "user1");

        when(repositorio.listarPendientes()).thenReturn(Arrays.asList(n1));
        when(estrategia1.estaDisponible()).thenReturn(false);
        when(estrategia2.estaDisponible()).thenReturn(false);

        // Act
        servicio.procesarNotificacionesPendientes();

        // Assert
        verify(estrategia1, never()).enviar(any());
        verify(estrategia2, never()).enviar(any());
    }

    @Test
    void testMarcarComoLeida() {
        // Arrange
        Notificacion notificacion = new Notificacion("T", "M", TipoNotificacion.INFORMATIVA, "user1");
        when(repositorio.obtenerPorId("notif1")).thenReturn(notificacion);

        // Act
        servicio.marcarComoLeida("notif1");

        // Assert
        assertEquals(EstadoNotificacion.LEIDA, notificacion.getEstado());
        verify(repositorio).guardar(notificacion);
    }

    @Test
    void testMarcarComoLeidaNoExiste() {
        // Arrange
        when(repositorio.obtenerPorId("noexiste")).thenReturn(null);

        // Act
        servicio.marcarComoLeida("noexiste");

        // Assert
        verify(repositorio, never()).guardar(any());
    }

    @Test
    void testObtenerNoLeidas() {
        // Arrange
        List<Notificacion> noLeidas = Arrays.asList(
            new Notificacion("T1", "M1", TipoNotificacion.INFORMATIVA, "user1")
        );
        when(repositorio.listarPorUsuarioYEstado("user1", EstadoNotificacion.ENVIADA))
            .thenReturn(noLeidas);

        // Act
        List<Notificacion> resultado = servicio.obtenerNoLeidas("user1");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositorio).listarPorUsuarioYEstado("user1", EstadoNotificacion.ENVIADA);
    }
}

