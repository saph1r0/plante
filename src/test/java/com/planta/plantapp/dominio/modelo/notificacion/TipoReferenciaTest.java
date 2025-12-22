package com.planta.plantapp.dominio.modelo.notificacion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoReferenciaTest {

    @Test
    void testTodosLosTipos() {
        assertEquals(5, TipoReferencia.values().length);
    }

    @Test
    void testPlanta() {
        assertEquals("Planta", TipoReferencia.PLANTA.getDescripcion());
    }

    @Test
    void testRecordatorio() {
        assertEquals("Recordatorio", TipoReferencia.RECORDATORIO.getDescripcion());
    }

    @Test
    void testBitacora() {
        assertEquals("Bitácora", TipoReferencia.BITACORA.getDescripcion());
    }

    @Test
    void testCuidado() {
        assertEquals("Cuidado", TipoReferencia.CUIDADO.getDescripcion());
    }

    @Test
    void testUsuario() {
        assertEquals("Usuario", TipoReferencia.USUARIO.getDescripcion());
    }

    @Test
    void testValueOf() {
        TipoReferencia tipo = TipoReferencia.valueOf("PLANTA");
        assertNotNull(tipo);
        assertEquals(TipoReferencia.PLANTA, tipo);
    }
}


