package com.planta.plantapp.dominio.modelo.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicadorEventosTest {

    private PublicadorEventos<String> publicador;
    private ObservadorEvento<String> observador1;
    private ObservadorEvento<String> observador2;

    @BeforeEach
    void setUp() {
        publicador = new PublicadorEventos<>();
        observador1 = mock(ObservadorEvento.class);
        observador2 = mock(ObservadorEvento.class);
    }

    @Test
    void testSuscribir() {
        publicador.suscribir(observador1);
        assertEquals(1, publicador.cantidadObservadores());
    }

    @Test
    void testSuscribirMultiples() {
        publicador.suscribir(observador1);
        publicador.suscribir(observador2);
        assertEquals(2, publicador.cantidadObservadores());
    }

    @Test
    void testSuscribirDuplicado() {
        publicador.suscribir(observador1);
        publicador.suscribir(observador1);
        assertEquals(1, publicador.cantidadObservadores());
    }

    @Test
    void testSuscribirNull() {
        publicador.suscribir(null);
        assertEquals(0, publicador.cantidadObservadores());
    }

    @Test
    void testDesuscribir() {
        publicador.suscribir(observador1);
        publicador.desuscribir(observador1);
        assertEquals(0, publicador.cantidadObservadores());
    }

    @Test
    void testNotificar() {
        publicador.suscribir(observador1);
        publicador.suscribir(observador2);

        String evento = "Test Evento";
        publicador.notificar(evento);

        verify(observador1).actualizar(evento);
        verify(observador2).actualizar(evento);
    }

    @Test
    void testNotificarSinObservadores() {
        // No debe lanzar excepción
        assertDoesNotThrow(() -> publicador.notificar("Test"));
    }

    @Test
    void testCantidadObservadores() {
        assertEquals(0, publicador.cantidadObservadores());

        publicador.suscribir(observador1);
        assertEquals(1, publicador.cantidadObservadores());

        publicador.suscribir(observador2);
        assertEquals(2, publicador.cantidadObservadores());

        publicador.desuscribir(observador1);
        assertEquals(1, publicador.cantidadObservadores());
    }
}

