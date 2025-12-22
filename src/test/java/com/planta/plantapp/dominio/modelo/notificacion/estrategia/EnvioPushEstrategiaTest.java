package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvioPushEstrategiaTest {

    private EnvioPushEstrategia estrategia;
    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        estrategia = new EnvioPushEstrategia();
        notificacion = new Notificacion(
            "Test Push",
            "Mensaje de prueba push",
            TipoNotificacion.ALERTA_URGENTE,
            "user123"
        );
    }

    @Test
    void testEnviar() {
        boolean resultado = estrategia.enviar(notificacion);
        assertTrue(resultado);
    }

    @Test
    void testGetNombre() {
        assertEquals("Push", estrategia.getNombre());
    }

    @Test
    void testEstaDisponible() {
        assertTrue(estrategia.estaDisponible());
    }

    @Test
    void testEnviarConNotificacionNula() {
        assertDoesNotThrow(() -> estrategia.enviar(null));
    }
}

