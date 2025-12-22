package com.planta.plantapp.aplicacion.observadores;

import com.planta.plantapp.aplicacion.servicios.ServicioNotificacionImpl;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.eventos.CuidadoAgregadoEvento;
import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EnvioInAppEstrategia;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificadorCuidadoAgregadoTest {

    @Mock
    private ServicioNotificacionImpl servicioNotificacion;

    @Mock
    private EnvioInAppEstrategia estrategiaInApp;

    private NotificadorCuidadoAgregado notificador;

    @BeforeEach
    void setUp() {
        notificador = new NotificadorCuidadoAgregado(servicioNotificacion, estrategiaInApp);
    }

    @Test
    void testActualizar() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa chinensis", "Hermosa rosa", "url");
        Cuidado cuidado = new Cuidado(TipoCuidado.RIEGO, "Regar diario", 1);
        CuidadoAgregadoEvento evento = new CuidadoAgregadoEvento(planta, cuidado);

        when(servicioNotificacion.crearNotificacion(anyString(), anyString(), any(), anyString(), any()))
            .thenReturn(new Notificacion());

        // Act
        notificador.actualizar(evento);

        // Assert
        verify(servicioNotificacion).crearNotificacion(
            eq("Nuevo Cuidado Agregado"),
            anyString(),
            any(),
            anyString(),
            eq(estrategiaInApp)
        );
    }

    @Test
    void testActualizarConEventoCompleto() {
        // Arrange
        Planta planta = new Planta("Cactus", "Cactus spp", "Cactus", "url");
        Cuidado cuidado = new Cuidado(TipoCuidado.FERTILIZACION, "Fertilizar mensual", 30);
        CuidadoAgregadoEvento evento = new CuidadoAgregadoEvento(planta, cuidado);

        when(servicioNotificacion.crearNotificacion(anyString(), anyString(), any(), anyString(), any()))
            .thenReturn(new Notificacion());

        // Act
        notificador.actualizar(evento);

        // Assert
        verify(servicioNotificacion, times(1)).crearNotificacion(
            anyString(),
            contains("Cactus"),
            any(),
            anyString(),
            any()
        );
    }
}

