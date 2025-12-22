package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvioInAppEstrategiaTest {

    private EnvioInAppEstrategia estrategia;
    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        estrategia = new EnvioInAppEstrategia();
        notificacion = new Notificacion(
            "Test In-App",
            "Mensaje in-app",
            TipoNotificacion.BITACORA_NUEVA,
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
        assertEquals("In-App", estrategia.getNombre());
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

