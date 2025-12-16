package com.planta.plantapp.dominio.modelo.cuidado;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TareaCuidadoTest {

    private TareaCuidado tarea;
    private Date fechaProgramada;

    @BeforeEach
    void setUp() {
        fechaProgramada = new Date();
    }

    @Test
    void constructor_vacio_inicializaRealizadoEnFalse() {
        tarea = new TareaCuidado();

        assertNotNull(tarea);
        assertFalse(tarea.isRealizado());
        assertNull(tarea.getTipo());
        assertNull(tarea.getFechaProgramada());
        assertNull(tarea.getFechaRealizada());
        assertNull(tarea.getNota());
        assertEquals(0, tarea.getId());
    }

    @Test
    void constructor_conParametros_inicializaCorrectamente() {
        tarea = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Regar por la mañana");

        assertEquals(1, tarea.getId());
        assertEquals(TipoCuidado.RIEGO, tarea.getTipo());
        assertEquals(fechaProgramada, tarea.getFechaProgramada());
        assertEquals("Regar por la mañana", tarea.getNota());
        assertFalse(tarea.isRealizado());
        assertNull(tarea.getFechaRealizada());
    }

    @Test
    void constructor_conNotaNull_aceptaNull() {
        tarea = new TareaCuidado(2, TipoCuidado.FERTILIZACION, fechaProgramada, null);

        assertEquals(2, tarea.getId());
        assertEquals(TipoCuidado.FERTILIZACION, tarea.getTipo());
        assertNull(tarea.getNota());
        assertFalse(tarea.isRealizado());
    }

    @Test
    void constructor_conDiferentesTiposCuidado_funcionaParaTodos() {
        TareaCuidado tareaRiego = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Riego");
        TareaCuidado tareaFertilizacion = new TareaCuidado(2, TipoCuidado.FERTILIZACION, fechaProgramada, "Fertilizar");
        TareaCuidado tareaPoda = new TareaCuidado(3, TipoCuidado.PODA, fechaProgramada, "Podar");
        TareaCuidado tareaLimpieza = new TareaCuidado(4, TipoCuidado.LIMPIEZA, fechaProgramada, "Limpiar");

        assertEquals(TipoCuidado.RIEGO, tareaRiego.getTipo());
        assertEquals(TipoCuidado.FERTILIZACION, tareaFertilizacion.getTipo());
        assertEquals(TipoCuidado.PODA, tareaPoda.getTipo());
        assertEquals(TipoCuidado.LIMPIEZA, tareaLimpieza.getTipo());
    }

    @Test
    void marcarComoRealizada_cambiaEstadoARealizado() {
        tarea = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Riego matutino");

        assertFalse(tarea.isRealizado());
        assertNull(tarea.getFechaRealizada());

        tarea.marcarComoRealizada();

        assertTrue(tarea.isRealizado());
        assertNotNull(tarea.getFechaRealizada());
    }

    @Test
    void marcarComoRealizada_estableceFechaRealizadaActual() {
        tarea = new TareaCuidado(1, TipoCuidado.FERTILIZACION, fechaProgramada, "Fertilizar");

        Date antes = new Date();
        tarea.marcarComoRealizada();
        Date despues = new Date();

        assertNotNull(tarea.getFechaRealizada());
        assertTrue(tarea.getFechaRealizada().getTime() >= antes.getTime());
        assertTrue(tarea.getFechaRealizada().getTime() <= despues.getTime());
    }

    @Test
    void marcarComoRealizada_puedeSerLlamadaMultiplesVeces() {
        tarea = new TareaCuidado(1, TipoCuidado.PODA, fechaProgramada, "Podar ramas");

        tarea.marcarComoRealizada();
        Date primeraFecha = tarea.getFechaRealizada();

        // Segunda llamada actualiza la fecha
        tarea.marcarComoRealizada();
        Date segundaFecha = tarea.getFechaRealizada();

        // La tarea sigue realizada y tiene fecha
        assertTrue(tarea.isRealizado());
        assertNotNull(segundaFecha);
        // La segunda fecha debe ser mayor o igual a la primera
        assertTrue(segundaFecha.getTime() >= primeraFecha.getTime());
    }

    @Test
    void marcarComoRealizada_enTareaVacia_funcionaCorrectamente() {
        tarea = new TareaCuidado();

        assertFalse(tarea.isRealizado());

        tarea.marcarComoRealizada();

        assertTrue(tarea.isRealizado());
        assertNotNull(tarea.getFechaRealizada());
    }

    @Test
    void setId_estableceIdCorrectamente() {
        tarea = new TareaCuidado();

        tarea.setId(100);

        assertEquals(100, tarea.getId());
    }

    @Test
    void setId_aceptaValoresNegativos() {
        tarea = new TareaCuidado();

        tarea.setId(-1);

        assertEquals(-1, tarea.getId());
    }

    @Test
    void setTipo_estableceTipoCorrectamente() {
        tarea = new TareaCuidado();

        tarea.setTipo(TipoCuidado.RIEGO);
        assertEquals(TipoCuidado.RIEGO, tarea.getTipo());

        tarea.setTipo(TipoCuidado.FERTILIZACION);
        assertEquals(TipoCuidado.FERTILIZACION, tarea.getTipo());
    }

    @Test
    void setTipo_aceptaNull() {
        tarea = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Riego");

        tarea.setTipo(null);

        assertNull(tarea.getTipo());
    }

    @Test
    void setFechaProgramada_estableceFechaCorrectamente() {
        tarea = new TareaCuidado();
        Date nuevaFecha = new Date();

        tarea.setFechaProgramada(nuevaFecha);

        assertEquals(nuevaFecha, tarea.getFechaProgramada());
    }

    @Test
    void setFechaProgramada_aceptaFechaFutura() {
        tarea = new TareaCuidado();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date fechaFutura = cal.getTime();

        tarea.setFechaProgramada(fechaFutura);

        assertEquals(fechaFutura, tarea.getFechaProgramada());
        assertTrue(tarea.getFechaProgramada().after(new Date()));
    }

    @Test
    void setFechaProgramada_aceptaFechaPasada() {
        tarea = new TareaCuidado();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date fechaPasada = cal.getTime();

        tarea.setFechaProgramada(fechaPasada);

        assertEquals(fechaPasada, tarea.getFechaProgramada());
        assertTrue(tarea.getFechaProgramada().before(new Date()));
    }

    @Test
    void setFechaRealizada_estableceFechaCorrectamente() {
        tarea = new TareaCuidado();
        Date fechaRealizada = new Date();

        tarea.setFechaRealizada(fechaRealizada);

        assertEquals(fechaRealizada, tarea.getFechaRealizada());
    }

    @Test
    void setRealizado_cambiaEstado() {
        tarea = new TareaCuidado();

        assertFalse(tarea.isRealizado());

        tarea.setRealizado(true);
        assertTrue(tarea.isRealizado());

        tarea.setRealizado(false);
        assertFalse(tarea.isRealizado());
    }

    @Test
    void setRealizado_noEstableceFechaRealizada() {
        tarea = new TareaCuidado();

        tarea.setRealizado(true);

        assertTrue(tarea.isRealizado());
        assertNull(tarea.getFechaRealizada()); // setRealizado no establece la fecha
    }

    @Test
    void setNota_estableceNotaCorrectamente() {
        tarea = new TareaCuidado();

        tarea.setNota("Esta es una nota de prueba");

        assertEquals("Esta es una nota de prueba", tarea.getNota());
    }

    @Test
    void setNota_aceptaNull() {
        tarea = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Nota inicial");

        tarea.setNota(null);

        assertNull(tarea.getNota());
    }

    @Test
    void setNota_aceptaStringVacio() {
        tarea = new TareaCuidado();

        tarea.setNota("");

        assertEquals("", tarea.getNota());
    }

    @Test
    void setNota_aceptaNotasLargas() {
        tarea = new TareaCuidado();
        String notaLarga = "Esta es una nota muy larga que contiene muchos detalles sobre el cuidado de la planta. " +
                "Debe regarse en la mañana temprano, evitando las horas de sol fuerte. " +
                "Utilizar agua a temperatura ambiente.";

        tarea.setNota(notaLarga);

        assertEquals(notaLarga, tarea.getNota());
    }

    @Test
    void escenario_tareaCompletaDesdeCreacionHastaRealizacion() {
        // Crear tarea programada
        Date fechaProgramacion = new Date();
        tarea = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramacion, "Regar en la mañana");

        // Verificar estado inicial
        assertEquals(1, tarea.getId());
        assertEquals(TipoCuidado.RIEGO, tarea.getTipo());
        assertFalse(tarea.isRealizado());
        assertNull(tarea.getFechaRealizada());

        // Marcar como realizada
        tarea.marcarComoRealizada();

        // Verificar estado final
        assertTrue(tarea.isRealizado());
        assertNotNull(tarea.getFechaRealizada());
        assertTrue(tarea.getFechaRealizada().getTime() >= fechaProgramacion.getTime());
    }

    @Test
    void escenario_modificarTareaDespuesDeCreacion() {
        tarea = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Nota inicial");

        // Modificar todos los campos
        tarea.setId(999);
        tarea.setTipo(TipoCuidado.FERTILIZACION);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date nuevaFecha = cal.getTime();
        tarea.setFechaProgramada(nuevaFecha);

        tarea.setNota("Nota modificada");
        tarea.setRealizado(true);

        // Verificar cambios
        assertEquals(999, tarea.getId());
        assertEquals(TipoCuidado.FERTILIZACION, tarea.getTipo());
        assertEquals(nuevaFecha, tarea.getFechaProgramada());
        assertEquals("Nota modificada", tarea.getNota());
        assertTrue(tarea.isRealizado());
    }

    @Test
    void escenario_tareaVencida() {
        // Crear tarea con fecha pasada
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date fechaVencida = cal.getTime();

        tarea = new TareaCuidado(1, TipoCuidado.PODA, fechaVencida, "Poda pendiente");

        // La tarea está vencida pero no realizada
        assertTrue(tarea.getFechaProgramada().before(new Date()));
        assertFalse(tarea.isRealizado());
    }

    @Test
    void escenario_tareaFutura() {
        // Crear tarea con fecha futura
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date fechaFutura = cal.getTime();

        tarea = new TareaCuidado(1, TipoCuidado.FERTILIZACION, fechaFutura, "Fertilizar próximamente");

        // La tarea es futura y no está realizada
        assertTrue(tarea.getFechaProgramada().after(new Date()));
        assertFalse(tarea.isRealizado());
    }

    @Test
    void escenario_realizarTareaAntesDeLoPlaneado() {
        // Tarea programada para mañana
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date manana = cal.getTime();

        tarea = new TareaCuidado(1, TipoCuidado.LIMPIEZA, manana, "Limpiar hojas");

        // Realizarla hoy
        tarea.marcarComoRealizada();

        // La tarea está realizada antes de la fecha programada
        assertTrue(tarea.isRealizado());
        assertTrue(tarea.getFechaRealizada().before(tarea.getFechaProgramada()));
    }

    @Test
    void escenario_crearMultiplesTareas() {
        TareaCuidado tarea1 = new TareaCuidado(1, TipoCuidado.RIEGO, fechaProgramada, "Riego 1");
        TareaCuidado tarea2 = new TareaCuidado(2, TipoCuidado.FERTILIZACION, fechaProgramada, "Fertilizar");
        TareaCuidado tarea3 = new TareaCuidado(3, TipoCuidado.PODA, fechaProgramada, "Podar");

        // Verificar que son independientes
        tarea1.marcarComoRealizada();

        assertTrue(tarea1.isRealizado());
        assertFalse(tarea2.isRealizado());
        assertFalse(tarea3.isRealizado());
    }

    @Test
    void edgeCase_idCero() {
        tarea = new TareaCuidado(0, TipoCuidado.RIEGO, fechaProgramada, "Tarea con ID 0");

        assertEquals(0, tarea.getId());
    }

    @Test
    void edgeCase_todosLosCamposNull() {
        tarea = new TareaCuidado();

        assertEquals(0, tarea.getId());
        assertNull(tarea.getTipo());
        assertNull(tarea.getFechaProgramada());
        assertNull(tarea.getFechaRealizada());
        assertFalse(tarea.isRealizado());
        assertNull(tarea.getNota());
    }

    @Test
    void edgeCase_marcarRealizadaSinFechaProgramada() {
        tarea = new TareaCuidado();
        // No tiene fecha programada

        tarea.marcarComoRealizada();

        assertTrue(tarea.isRealizado());
        assertNotNull(tarea.getFechaRealizada());
    }
}