package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.INotificacionRepositorio;
import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import com.planta.plantapp.dominio.modelo.notificacion.EstadoNotificacion;
import com.planta.plantapp.dominio.modelo.servicios.ServicioNotificacionDominio;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EnvioEmailEstrategia;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EnvioInAppEstrategia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioNotificacionImplTest {

    @Mock
    private ServicioNotificacionDominio servicioDominio;

    @Mock
    private INotificacionRepositorio repositorio;

    @Mock
    private EnvioEmailEstrategia estrategiaEmail;

    @Mock
    private EnvioInAppEstrategia estrategiaInApp;

    private ServicioNotificacionImpl servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioNotificacionImpl(servicioDominio, repositorio);
    }

    @Test
    void testCrearNotificacion() {
        // Arrange
        Notificacion notificacionEsperada = new Notificacion(
            "Test", "Mensaje", TipoNotificacion.INFORMATIVA, "user1"
        );
        when(servicioDominio.crearYEnviar(anyString(), anyString(), any(), anyString(), any()))
            .thenReturn(notificacionEsperada);

        // Act
        Notificacion resultado = servicio.crearNotificacion(
            "Test", "Mensaje", TipoNotificacion.INFORMATIVA, "user1", estrategiaEmail
        );

        // Assert
        assertNotNull(resultado);
        assertEquals("Test", resultado.getTitulo());
        verify(servicioDominio).crearYEnviar(anyString(), anyString(), any(), anyString(), any());
    }

    @Test
    void testObtenerNotificacionesUsuario() {
        // Arrange
        List<Notificacion> notificaciones = Arrays.asList(
            new Notificacion("Test1", "Msg1", TipoNotificacion.INFORMATIVA, "user1"),
            new Notificacion("Test2", "Msg2", TipoNotificacion.ALERTA_URGENTE, "user1")
        );
        when(repositorio.listarPorUsuario("user1")).thenReturn(notificaciones);

        // Act
        List<Notificacion> resultado = servicio.obtenerNotificacionesUsuario("user1");

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositorio).listarPorUsuario("user1");
    }

    @Test
    void testObtenerNotificacionesNoLeidas() {
        // Arrange
        List<Notificacion> noLeidas = Arrays.asList(
            new Notificacion("Test1", "Msg1", TipoNotificacion.INFORMATIVA, "user1")
        );
        when(servicioDominio.obtenerNoLeidas("user1")).thenReturn(noLeidas);

        // Act
        List<Notificacion> resultado = servicio.obtenerNotificacionesNoLeidas("user1");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(servicioDominio).obtenerNoLeidas("user1");
    }

    @Test
    void testMarcarComoLeida() {
        // Arrange
        doNothing().when(servicioDominio).marcarComoLeida("notif1");

        // Act
        servicio.marcarComoLeida("notif1");

        // Assert
        verify(servicioDominio).marcarComoLeida("notif1");
    }

    @Test
    void testMarcarTodasComoLeidas() {
        // Arrange
        doNothing().when(repositorio).marcarTodasComoLeidas("user1");

        // Act
        servicio.marcarTodasComoLeidas("user1");

        // Assert
        verify(repositorio).marcarTodasComoLeidas("user1");
    }

    @Test
    void testEliminarNotificacion() {
        // Arrange
        doNothing().when(repositorio).eliminar("notif1");

        // Act
        servicio.eliminarNotificacion("notif1");

        // Assert
        verify(repositorio).eliminar("notif1");
    }

    @Test
    void testProcesarPendientes() {
        // Arrange
        doNothing().when(servicioDominio).procesarNotificacionesPendientes();

        // Act
        servicio.procesarPendientes();

        // Assert
        verify(servicioDominio).procesarNotificacionesPendientes();
    }

    @Test
    void testObtenerPorTipo() {
        // Arrange
        List<Notificacion> notificaciones = Arrays.asList(
            new Notificacion("Test", "Msg", TipoNotificacion.RECORDATORIO_CUIDADO, "user1")
        );
        when(repositorio.listarPorTipo("user1", TipoNotificacion.RECORDATORIO_CUIDADO))
            .thenReturn(notificaciones);

        // Act
        List<Notificacion> resultado = servicio.obtenerPorTipo("user1", TipoNotificacion.RECORDATORIO_CUIDADO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositorio).listarPorTipo("user1", TipoNotificacion.RECORDATORIO_CUIDADO);
    }
}

