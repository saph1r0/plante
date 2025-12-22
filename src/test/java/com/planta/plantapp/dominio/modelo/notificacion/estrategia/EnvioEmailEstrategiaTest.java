package com.planta.plantapp.dominio.modelo.notificacion.estrategia;

import com.planta.plantapp.dominio.modelo.notificacion.Notificacion;
import com.planta.plantapp.dominio.modelo.notificacion.TipoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvioEmailEstrategiaTest {

    private EnvioEmailEstrategia estrategia;
    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        estrategia = new EnvioEmailEstrategia();
        notificacion = new Notificacion(
            "Test Email",
            "Mensaje de prueba",
            TipoNotificacion.INFORMATIVA,
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
        assertEquals("Email", estrategia.getNombre());
    }

    @Test
    void testEstaDisponible() {
        assertTrue(estrategia.estaDisponible());
    }

    @Test
    void testEnviarConNotificacionNula() {
        // Debería manejar el caso null
        assertDoesNotThrow(() -> estrategia.enviar(null));
    }
}

