package com.planta.plantapp.dominio.modelo.planta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadoPlantaTest {

    @Test
    void getNombre_DeberiaRetornarNombreCorrecto() {
        assertEquals("Saludable", EstadoPlanta.SALUDABLE.getNombre());
        assertEquals("Necesita Agua", EstadoPlanta.NECESITA_AGUA.getNombre());
        assertEquals("Enferma", EstadoPlanta.ENFERMA.getNombre());
    }

    @Test
    void getDescripcion_DeberiaRetornarDescripcionCorrecta() {
        assertEquals(
                "La planta está en buen estado",
                EstadoPlanta.SALUDABLE.getDescripcion()
        );

        assertEquals(
                "La planta requiere riego",
                EstadoPlanta.NECESITA_AGUA.getDescripcion()
        );
    }

    @Test
    void requiereCuidadoInmediato_DeberiaRetornarTrue_ParaEstadosCriticos() {
        assertTrue(EstadoPlanta.NECESITA_AGUA.requiereCuidadoInmediato());
        assertTrue(EstadoPlanta.ENFERMA.requiereCuidadoInmediato());
        assertTrue(EstadoPlanta.MARCHITA.requiereCuidadoInmediato());
    }

    @Test
    void requiereCuidadoInmediato_DeberiaRetornarFalse_ParaEstadosNoCriticos() {
        assertFalse(EstadoPlanta.SALUDABLE.requiereCuidadoInmediato());
        assertFalse(EstadoPlanta.FLORECIENDO.requiereCuidadoInmediato());
        assertFalse(EstadoPlanta.CRECIENDO.requiereCuidadoInmediato());
        assertFalse(EstadoPlanta.DORMIDA.requiereCuidadoInmediato());
    }

    @Test
    void esEstadoPositivo_DeberiaRetornarTrue_ParaEstadosPositivos() {
        assertTrue(EstadoPlanta.SALUDABLE.esEstadoPositivo());
        assertTrue(EstadoPlanta.FLORECIENDO.esEstadoPositivo());
        assertTrue(EstadoPlanta.CRECIENDO.esEstadoPositivo());
    }

    @Test
    void esEstadoPositivo_DeberiaRetornarFalse_ParaEstadosNegativos() {
        assertFalse(EstadoPlanta.NECESITA_AGUA.esEstadoPositivo());
        assertFalse(EstadoPlanta.ENFERMA.esEstadoPositivo());
        assertFalse(EstadoPlanta.MARCHITA.esEstadoPositivo());
        assertFalse(EstadoPlanta.DORMIDA.esEstadoPositivo());
    }

    @Test
    void toString_DeberiaRetornarNombreDelEstado() {
        assertEquals("Saludable", EstadoPlanta.SALUDABLE.toString());
        assertEquals("Floreciendo", EstadoPlanta.FLORECIENDO.toString());
    }

    @Test
    void enum_DeberiaContenerTodosLosValoresEsperados() {
        EstadoPlanta[] valores = EstadoPlanta.values();

        assertEquals(8, valores.length);
        assertArrayEquals(
                new EstadoPlanta[]{
                        EstadoPlanta.SALUDABLE,
                        EstadoPlanta.NECESITA_AGUA,
                        EstadoPlanta.NECESITA_FERTILIZANTE,
                        EstadoPlanta.ENFERMA,
                        EstadoPlanta.MARCHITA,
                        EstadoPlanta.FLORECIENDO,
                        EstadoPlanta.CRECIENDO,
                        EstadoPlanta.DORMIDA
                },
                valores
        );
    }
}
