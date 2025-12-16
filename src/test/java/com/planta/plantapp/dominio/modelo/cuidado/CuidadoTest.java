package com.planta.plantapp.dominio.modelo.cuidado;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CuidadoTest {

    private Cuidado cuidado;

    @Test
    void constructor_conParametros_inicializaCorrectamente() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Regar abundantemente", Integer.valueOf(3));

        assertNotNull(cuidado);
        assertEquals(TipoCuidado.RIEGO, cuidado.getTipo());
        assertEquals("Regar abundantemente", cuidado.getDescripcion());
        assertEquals(3, cuidado.getFrecuenciaDias());
        assertNotNull(cuidado.getFechaAplicacion());
        assertNotNull(cuidado.getFechaProxima());
    }

    @Test
    void constructor_vacio_creaInstancia() {
        cuidado = new Cuidado();

        assertNotNull(cuidado);
        assertNull(cuidado.getTipo());
        assertNull(cuidado.getDescripcion());
        assertNull(cuidado.getFrecuenciaDias());
    }

    @Test
    void constructor_conTresParametros_inicializaSinFechas() {
        cuidado = new Cuidado(TipoCuidado.FERTILIZACION, "Fertilizar mensualmente", 30);

        assertEquals(TipoCuidado.FERTILIZACION, cuidado.getTipo());
        assertEquals("Fertilizar mensualmente", cuidado.getDescripcion());
        assertEquals(30, cuidado.getFrecuenciaDias());
        // Este constructor no inicializa fechas
        assertNull(cuidado.getFechaAplicacion());
        assertNull(cuidado.getFechaProxima());
    }

    @Test
    void constructor_estableceFechaAplicacionActual() {
        LocalDateTime antes = LocalDateTime.now();
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego regular", Integer.valueOf(2));
        LocalDateTime despues = LocalDateTime.now();

        assertNotNull(cuidado.getFechaAplicacion());
        assertTrue(cuidado.getFechaAplicacion().isAfter(antes.minusSeconds(1)));
        assertTrue(cuidado.getFechaAplicacion().isBefore(despues.plusSeconds(1)));
    }

    @Test
    void programarProximo_conFrecuenciaPositiva_calculaFechaCorrecta() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(5));
        LocalDateTime fechaEsperada = cuidado.getFechaAplicacion().plusDays(5);

        assertEquals(fechaEsperada.getDayOfYear(), cuidado.getFechaProxima().getDayOfYear());
    }

    @Test
    void programarProximo_conFrecuenciaNull_noEstableceFechaProxima() {
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.PODA);
        cuidado.setDescripcion("Poda anual");
        cuidado.setFrecuenciaDias(null);
        cuidado.setFechaAplicacion(LocalDateTime.now());

        cuidado.programarProximo();

        assertNull(cuidado.getFechaProxima());
    }

    @Test
    void programarProximo_conFrecuenciaCero_noEstableceFechaProxima() {
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.LIMPIEZA);
        cuidado.setFrecuenciaDias(0);
        cuidado.setFechaAplicacion(LocalDateTime.now());

        cuidado.programarProximo();

        assertNull(cuidado.getFechaProxima());
    }

    @Test
    void programarProximo_conFrecuenciaNegativa_noEstableceFechaProxima() {
        cuidado = new Cuidado();
        cuidado.setFrecuenciaDias(-5);
        cuidado.setFechaAplicacion(LocalDateTime.now());

        cuidado.programarProximo();

        assertNull(cuidado.getFechaProxima());
    }

    @Test
    void programarProximo_actualizaFechaProximaCorrectamente() {
        cuidado = new Cuidado();
        cuidado.setFrecuenciaDias(7);
        cuidado.setFechaAplicacion(LocalDateTime.of(2024, 1, 1, 10, 0));

        cuidado.programarProximo();

        assertEquals(LocalDateTime.of(2024, 1, 8, 10, 0), cuidado.getFechaProxima());
    }

    @Test
    void esPendiente_cuandoFechaProximaEsFutura_retornaTrue() {
        cuidado = new Cuidado();
        cuidado.setFechaProxima(LocalDateTime.now().plusDays(5));

        assertTrue(cuidado.esPendiente());
    }

    @Test
    void esPendiente_cuandoFechaProximaEsPasada_retornaFalse() {
        cuidado = new Cuidado();
        cuidado.setFechaProxima(LocalDateTime.now().minusDays(5));

        assertFalse(cuidado.esPendiente());
    }

    @Test
    void esPendiente_cuandoFechaProximaEsNull_retornaFalse() {
        cuidado = new Cuidado();
        cuidado.setFechaProxima(null);

        assertFalse(cuidado.esPendiente());
    }

    @Test
    void esPendiente_cuandoFechaProximaEsAhora_retornaFalse() {
        cuidado = new Cuidado();
        cuidado.setFechaProxima(LocalDateTime.now());

        // Puede ser false o true dependiendo de los milisegundos,
        // pero generalmente será false porque isAfter no incluye igualdad
        assertFalse(cuidado.esPendiente());
    }

    @Test
    void setters_establecenValoresCorrectamente() {
        cuidado = new Cuidado();
        LocalDateTime fecha = LocalDateTime.of(2024, 6, 15, 14, 30);
        LocalDateTime fechaProx = LocalDateTime.of(2024, 6, 22, 14, 30);

        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego profundo");
        cuidado.setFrecuenciaDias(7);
        cuidado.setFechaAplicacion(fecha);
        cuidado.setFechaProxima(fechaProx);
        cuidado.setNotas("Usar agua tibia");

        assertEquals(TipoCuidado.RIEGO, cuidado.getTipo());
        assertEquals("Riego profundo", cuidado.getDescripcion());
        assertEquals(7, cuidado.getFrecuenciaDias());
        assertEquals(fecha, cuidado.getFechaAplicacion());
        assertEquals(fechaProx, cuidado.getFechaProxima());
        assertEquals("Usar agua tibia", cuidado.getNotas());
    }

    @Test
    void setNotas_aceptaNull() {
        cuidado = new Cuidado(TipoCuidado.PODA, "Poda ligera", 30);
        cuidado.setNotas(null);

        assertNull(cuidado.getNotas());
    }

    @Test
    void setNotas_aceptaStringVacio() {
        cuidado = new Cuidado(TipoCuidado.PODA, "Poda ligera", 30);
        cuidado.setNotas("");

        assertEquals("", cuidado.getNotas());
    }

    @Test
    void equals_mismoCuidado_retornaTrue() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", 3);

        assertEquals(cuidado, cuidado);
    }

    @Test
    void equals_cuidadosIguales_retornaTrue() {
        LocalDateTime fecha = LocalDateTime.of(2024, 1, 1, 10, 0);

        Cuidado c1 = new Cuidado();
        c1.setTipo(TipoCuidado.RIEGO);
        c1.setDescripcion("Riego regular");
        c1.setFechaAplicacion(fecha);

        Cuidado c2 = new Cuidado();
        c2.setTipo(TipoCuidado.RIEGO);
        c2.setDescripcion("Riego regular");
        c2.setFechaAplicacion(fecha);

        assertEquals(c1, c2);
    }

    @Test
    void equals_cuidadosDiferenteTipo_retornaFalse() {
        LocalDateTime fecha = LocalDateTime.of(2024, 1, 1, 10, 0);

        Cuidado c1 = new Cuidado();
        c1.setTipo(TipoCuidado.RIEGO);
        c1.setDescripcion("Riego");
        c1.setFechaAplicacion(fecha);

        Cuidado c2 = new Cuidado();
        c2.setTipo(TipoCuidado.FERTILIZACION);
        c2.setDescripcion("Riego");
        c2.setFechaAplicacion(fecha);

        assertNotEquals(c1, c2);
    }

    @Test
    void equals_cuidadosDiferenteDescripcion_retornaFalse() {
        LocalDateTime fecha = LocalDateTime.of(2024, 1, 1, 10, 0);

        Cuidado c1 = new Cuidado();
        c1.setTipo(TipoCuidado.RIEGO);
        c1.setDescripcion("Riego profundo");
        c1.setFechaAplicacion(fecha);

        Cuidado c2 = new Cuidado();
        c2.setTipo(TipoCuidado.RIEGO);
        c2.setDescripcion("Riego superficial");
        c2.setFechaAplicacion(fecha);

        assertNotEquals(c1, c2);
    }

    @Test
    void equals_cuidadosDiferenteFecha_retornaFalse() {
        Cuidado c1 = new Cuidado();
        c1.setTipo(TipoCuidado.RIEGO);
        c1.setDescripcion("Riego");
        c1.setFechaAplicacion(LocalDateTime.of(2024, 1, 1, 10, 0));

        Cuidado c2 = new Cuidado();
        c2.setTipo(TipoCuidado.RIEGO);
        c2.setDescripcion("Riego");
        c2.setFechaAplicacion(LocalDateTime.of(2024, 1, 2, 10, 0));

        assertNotEquals(c1, c2);
    }

    @Test
    void equals_comparacionConNull_retornaFalse() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", 3);

        assertNotEquals(null, cuidado);
    }

    @Test
    void equals_comparacionConOtraClase_retornaFalse() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", 3);
        String otroObjeto = "No es un cuidado";

        assertNotEquals(cuidado, otroObjeto);
    }

    @Test
    void hashCode_cuidadosIguales_mismoHashCode() {
        LocalDateTime fecha = LocalDateTime.of(2024, 1, 1, 10, 0);

        Cuidado c1 = new Cuidado();
        c1.setTipo(TipoCuidado.RIEGO);
        c1.setDescripcion("Riego");
        c1.setFechaAplicacion(fecha);

        Cuidado c2 = new Cuidado();
        c2.setTipo(TipoCuidado.RIEGO);
        c2.setDescripcion("Riego");
        c2.setFechaAplicacion(fecha);

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void hashCode_cuidadosDiferentes_diferenteHashCode() {
        Cuidado c1 = new Cuidado();
        c1.setTipo(TipoCuidado.RIEGO);
        c1.setDescripcion("Riego");
        c1.setFechaAplicacion(LocalDateTime.of(2024, 1, 1, 10, 0));

        Cuidado c2 = new Cuidado();
        c2.setTipo(TipoCuidado.FERTILIZACION);
        c2.setDescripcion("Fertilización");
        c2.setFechaAplicacion(LocalDateTime.of(2024, 1, 2, 10, 0));

        assertNotEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void hashCode_mismoCuidado_consistente() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", 3);

        int hash1 = cuidado.hashCode();
        int hash2 = cuidado.hashCode();

        assertEquals(hash1, hash2);
    }

    @Test
    void toString_contieneInformacionBasica() {
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego semanal");
        cuidado.setFrecuenciaDias(7);
        cuidado.setNotas("No regar en exceso");

        String resultado = cuidado.toString();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.contains("Riego semanal"));
    }

    @Test
    void toString_noLanzaExcepcion() {
        cuidado = new Cuidado(TipoCuidado.PODA, "Poda anual", 365);

        assertDoesNotThrow(() -> cuidado.toString());
    }

    @Test
    void toString_conCamposNull_noLanzaExcepcion() {
        cuidado = new Cuidado();

        assertDoesNotThrow(() -> cuidado.toString());
        assertNotNull(cuidado.toString());
    }

    @Test
    void escenario_riegoSemanal_calculaCorrectamente() {
        // Crear un cuidado de riego semanal
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego cada 7 días", Integer.valueOf(7));

        // Verificar que se inicializó correctamente
        assertNotNull(cuidado.getFechaAplicacion());
        assertNotNull(cuidado.getFechaProxima());

        // La próxima fecha debe ser 7 días después
        LocalDateTime fechaEsperada = cuidado.getFechaAplicacion().plusDays(7);
        assertEquals(fechaEsperada.getDayOfYear(), cuidado.getFechaProxima().getDayOfYear());

        // Debe estar pendiente
        assertTrue(cuidado.esPendiente());
    }

    @Test
    void escenario_cuidadoVencido_noEsPendiente() {
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.FERTILIZACION);
        cuidado.setFechaAplicacion(LocalDateTime.now().minusDays(30));
        cuidado.setFrecuenciaDias(15);
        cuidado.programarProximo();

        // El cuidado programado hace 30 días con frecuencia de 15 días está vencido
        assertFalse(cuidado.esPendiente());
    }

    @Test
    void escenario_modificarFrecuencia_reprogramaCorrectamente() {
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(7));
        LocalDateTime fechaOriginal = cuidado.getFechaProxima();

        // Cambiar frecuencia y reprogramar
        cuidado.setFrecuenciaDias(3);
        cuidado.programarProximo();

        assertNotEquals(fechaOriginal, cuidado.getFechaProxima());
        assertEquals(
                cuidado.getFechaAplicacion().plusDays(3).getDayOfYear(),
                cuidado.getFechaProxima().getDayOfYear()
        );
    }
}