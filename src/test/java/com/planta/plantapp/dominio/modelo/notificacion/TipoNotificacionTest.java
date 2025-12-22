package com.planta.plantapp.dominio.modelo.notificacion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoNotificacionTest {

    @Test
    void testTodosLosTipos() {
        assertEquals(6, TipoNotificacion.values().length);
    }

    @Test
    void testRecordatorioCuidado() {
        assertEquals("Recordatorio de Cuidado", TipoNotificacion.RECORDATORIO_CUIDADO.getDescripcion());
    }

    @Test
    void testAlertaUrgente() {
        assertEquals("Alerta Urgente", TipoNotificacion.ALERTA_URGENTE.getDescripcion());
    }

    @Test
    void testBitacoraNueva() {
        assertEquals("Nueva Entrada en Bitácora", TipoNotificacion.BITACORA_NUEVA.getDescripcion());
    }

    @Test
    void testPlantaRegistrada() {
        assertEquals("Planta Registrada", TipoNotificacion.PLANTA_REGISTRADA.getDescripcion());
    }

    @Test
    void testEstadoPlantaCambiado() {
        assertEquals("Estado de Planta Modificado", TipoNotificacion.ESTADO_PLANTA_CAMBIADO.getDescripcion());
    }

    @Test
    void testInformativa() {
        assertEquals("Información General", TipoNotificacion.INFORMATIVA.getDescripcion());
    }

    @Test
    void testValueOf() {
        TipoNotificacion tipo = TipoNotificacion.valueOf("RECORDATORIO_CUIDADO");
        assertNotNull(tipo);
        assertEquals(TipoNotificacion.RECORDATORIO_CUIDADO, tipo);
    }
}

