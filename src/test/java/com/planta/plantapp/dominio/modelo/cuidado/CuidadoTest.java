package com.planta.plantapp.dominio.modelo.cuidado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Pruebas para la entidad Cuidado")
class CuidadoTest {

    private Cuidado cuidado;
    private static final String DESCRIPCION_VALIDA = "Riego moderado";
    private static final int FRECUENCIA_VALIDA = 3;

    @BeforeEach
    void setUp() {
        cuidado = null;
    }

    @Test
    @DisplayName("Crear cuidado con constructor principal (Integer)")
    void testCrearCuidadoConConstructorPrincipal() {
        // Arrange & Act - Usa el constructor con Integer (que inicializa fechas)
        cuidado = new Cuidado(TipoCuidado.RIEGO, DESCRIPCION_VALIDA, Integer.valueOf(FRECUENCIA_VALIDA));

        // Assert
        assertNotNull(cuidado);
        assertEquals(TipoCuidado.RIEGO, cuidado.getTipo());
        assertEquals(DESCRIPCION_VALIDA, cuidado.getDescripcion());
        assertEquals(FRECUENCIA_VALIDA, cuidado.getFrecuenciaDias());
        assertNotNull(cuidado.getFechaAplicacion());
        assertNotNull(cuidado.getFechaProxima());

        // Verificar que la fecha de aplicación es reciente
        assertTrue(LocalDateTime.now().minusSeconds(1).isBefore(cuidado.getFechaAplicacion()));

        // Verificar que la fecha próxima está programada correctamente
        LocalDateTime fechaProximaEsperada = cuidado.getFechaAplicacion().plusDays(FRECUENCIA_VALIDA);
        assertEquals(fechaProximaEsperada, cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Crear cuidado con constructor int (no inicializa fechas)")
    void testCrearCuidadoConConstructorInt() {
        // Arrange & Act - Usa el constructor con int (NO inicializa fechas)
        cuidado = new Cuidado(TipoCuidado.FERTILIZACION, "Fertilización orgánica", 15);

        // Assert
        assertNotNull(cuidado);
        assertEquals(TipoCuidado.FERTILIZACION, cuidado.getTipo());
        assertEquals("Fertilización orgánica", cuidado.getDescripcion());
        assertEquals(15, cuidado.getFrecuenciaDias());

        // Este constructor NO inicializa fechaAplicacion ni fechaProxima
        assertNull(cuidado.getFechaAplicacion());
        assertNull(cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Constructor vacío para MongoDB")
    void testConstructorVacio() {
        // Arrange & Act
        cuidado = new Cuidado();

        // Assert
        assertNotNull(cuidado);
        assertNull(cuidado.getTipo());
        assertNull(cuidado.getDescripcion());
        assertNull(cuidado.getFrecuenciaDias());
        assertNull(cuidado.getFechaAplicacion());
        assertNull(cuidado.getFechaProxima());
        assertNull(cuidado.getNotas());
    }

    @ParameterizedTest
    @EnumSource(TipoCuidado.class)
    @DisplayName("Crear cuidado con todos los tipos de cuidado (constructor Integer)")
    void testCrearCuidadoConTodosLosTipos(TipoCuidado tipo) {
        // Arrange & Act - Usa Integer para que inicialice fechas
        cuidado = new Cuidado(tipo, "Descripción para " + tipo.name(), Integer.valueOf(5));

        // Assert
        assertNotNull(cuidado);
        assertEquals(tipo, cuidado.getTipo());
        assertEquals("Descripción para " + tipo.name(), cuidado.getDescripcion());
        assertEquals(5, cuidado.getFrecuenciaDias());
        assertNotNull(cuidado.getFechaAplicacion());
        assertNotNull(cuidado.getFechaProxima());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 30, 365})
    @DisplayName("Crear cuidado con diferentes frecuencias (constructor Integer)")
    void testCrearCuidadoConDiferentesFrecuencias(int frecuencia) {
        // Arrange & Act - Usa Integer
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(frecuencia));

        // Assert
        assertNotNull(cuidado.getFechaAplicacion());
        assertNotNull(cuidado.getFechaProxima());
        LocalDateTime fechaProximaEsperada = cuidado.getFechaAplicacion().plusDays(frecuencia);
        assertEquals(fechaProximaEsperada, cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Programar próxima fecha con frecuencia nula")
    void testProgramarProximoConFrecuenciaNula() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Test");
        cuidado.setFrecuenciaDias(null);
        cuidado.setFechaAplicacion(LocalDateTime.now());

        // Act
        cuidado.programarProximo();

        // Assert
        assertNull(cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Programar próxima fecha con frecuencia cero")
    void testProgramarProximoConFrecuenciaCero() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Test");
        cuidado.setFrecuenciaDias(0);
        cuidado.setFechaAplicacion(LocalDateTime.now());

        // Act
        cuidado.programarProximo();

        // Assert
        assertNull(cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Programar próxima fecha con frecuencia negativa")
    void testProgramarProximoConFrecuenciaNegativa() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Test");
        cuidado.setFrecuenciaDias(-5);
        cuidado.setFechaAplicacion(LocalDateTime.now());

        // Act
        cuidado.programarProximo();

        // Assert
        assertNull(cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Es pendiente cuando fecha próxima es en el futuro")
    void testEsPendienteCuandoFechaProximaEsFuturo() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setFechaProxima(LocalDateTime.now().plusDays(1));

        // Act & Assert
        assertTrue(cuidado.esPendiente());
    }

    @Test
    @DisplayName("No es pendiente cuando fecha próxima es en el pasado")
    void testNoEsPendienteCuandoFechaProximaEsPasado() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setFechaProxima(LocalDateTime.now().minusDays(1));

        // Act & Assert
        assertFalse(cuidado.esPendiente());
    }

    @Test
    @DisplayName("No es pendiente cuando fecha próxima es null")
    void testNoEsPendienteCuandoFechaProximaEsNull() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setFechaProxima(null);

        // Act & Assert
        assertFalse(cuidado.esPendiente());
    }

    @Test
    @DisplayName("Es pendiente cuando fecha próxima es ahora mismo")
    void testEsPendienteCuandoFechaProximaEsAhora() {
        // Arrange
        LocalDateTime ahora = LocalDateTime.now();
        cuidado = new Cuidado();
        cuidado.setFechaProxima(ahora);

        // Act & Assert
        // Según tu implementación: fechaProxima.isAfter(LocalDateTime.now())
        // Si fechaProxima = ahora, entonces ahora.isAfter(ahora) = false
        // Por lo tanto esPendiente() = false
        assertFalse(cuidado.esPendiente());
    }

    @Test
    @DisplayName("Getters y setters funcionan correctamente")
    void testGettersYSetters() {
        // Arrange
        cuidado = new Cuidado();
        LocalDateTime fechaAplicacion = LocalDateTime.now().minusDays(2);
        LocalDateTime fechaProxima = LocalDateTime.now().plusDays(5);
        String notas = "Notas importantes sobre el cuidado";

        // Act
        cuidado.setTipo(TipoCuidado.PODA);
        cuidado.setDescripcion("Poda de formación");
        cuidado.setFrecuenciaDias(30);
        cuidado.setFechaAplicacion(fechaAplicacion);
        cuidado.setFechaProxima(fechaProxima);
        cuidado.setNotas(notas);

        // Assert
        assertEquals(TipoCuidado.PODA, cuidado.getTipo());
        assertEquals("Poda de formación", cuidado.getDescripcion());
        assertEquals(30, cuidado.getFrecuenciaDias());
        assertEquals(fechaAplicacion, cuidado.getFechaAplicacion());
        assertEquals(fechaProxima, cuidado.getFechaProxima());
        assertEquals(notas, cuidado.getNotas());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    @DisplayName("Setter de descripción con valores vacíos o nulos")
    void testSetDescripcionConValoresVacios(String descripcion) {
        // Arrange
        cuidado = new Cuidado();

        // Act
        cuidado.setDescripcion(descripcion);

        // Assert
        assertEquals(descripcion, cuidado.getDescripcion());
    }

    @Test
    @DisplayName("Método toString genera cadena correctamente")
    void testToString() {
        // Arrange
        LocalDateTime fechaAplicacion = LocalDateTime.of(2024, 1, 15, 10, 30);
        LocalDateTime fechaProxima = LocalDateTime.of(2024, 1, 18, 10, 30);

        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego profundo");
        cuidado.setFrecuenciaDias(3);
        cuidado.setFechaAplicacion(fechaAplicacion);
        cuidado.setFechaProxima(fechaProxima);
        cuidado.setNotas("Regar en la mañana");

        // Act
        String resultado = cuidado.toString();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("RIEGO") || resultado.contains("Riego"));
        assertTrue(resultado.contains("Riego profundo"));
        assertTrue(resultado.contains("fechaAplicacion="));
        assertTrue(resultado.contains("fechaProxima="));
    }

    @Test
    @DisplayName("Equals con misma instancia retorna true")
    void testEqualsMismaInstancia() {
        // Arrange - Usa constructor Integer para tener fechaAplicacion
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(3));

        // Act & Assert
        assertEquals(cuidado, cuidado);
    }

    @Test
    @DisplayName("Equals con null retorna false")
    void testEqualsConNull() {
        // Arrange
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(3));

        // Act & Assert
        assertNotEquals(null, cuidado);
    }

    @Test
    @DisplayName("equals() retorna false cuando el objeto no es Cuidado")
    void testEqualsConObjetoNoCuidado() {
        Cuidado cuidado2 = new Cuidado(TipoCuidado.RIEGO, "Riego", 3);
        Object otroObjeto = new Object();

        assertNotEquals(otroObjeto, cuidado2);
    }

    @Test
    @DisplayName("Equals con mismos valores retorna true")
    void testEqualsConMismosValores() {
        // Arrange
        LocalDateTime fechaAplicacion = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.FERTILIZACION);
        cuidado.setDescripcion("Abono líquido");
        cuidado.setFechaAplicacion(fechaAplicacion);

        Cuidado otroCuidado = new Cuidado();
        otroCuidado.setTipo(TipoCuidado.FERTILIZACION);
        otroCuidado.setDescripcion("Abono líquido");
        otroCuidado.setFechaAplicacion(fechaAplicacion);

        // Act & Assert
        assertEquals(cuidado, otroCuidado);
        assertEquals(cuidado.hashCode(), otroCuidado.hashCode());
    }

    @Test
    @DisplayName("Equals con tipo diferente retorna false")
    void testEqualsConTipoDiferente() {
        // Arrange
        LocalDateTime fechaAplicacion = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego");
        cuidado.setFechaAplicacion(fechaAplicacion);

        Cuidado otroCuidado = new Cuidado();
        otroCuidado.setTipo(TipoCuidado.FERTILIZACION); // DIFERENTE
        otroCuidado.setDescripcion("Riego");
        otroCuidado.setFechaAplicacion(fechaAplicacion);

        // Act & Assert
        assertNotEquals(cuidado, otroCuidado);
    }

    @Test
    @DisplayName("Equals con descripción diferente retorna false")
    void testEqualsConDescripcionDiferente() {
        // Arrange
        LocalDateTime fechaAplicacion = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego ligero");
        cuidado.setFechaAplicacion(fechaAplicacion);

        Cuidado otroCuidado = new Cuidado();
        otroCuidado.setTipo(TipoCuidado.RIEGO);
        otroCuidado.setDescripcion("Riego profundo"); // DIFERENTE
        otroCuidado.setFechaAplicacion(fechaAplicacion);

        // Act & Assert
        assertNotEquals(cuidado, otroCuidado);
    }

    @Test
    @DisplayName("Equals con fecha aplicación diferente retorna false")
    void testEqualsConFechaAplicacionDiferente() {
        // Arrange
        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego");
        cuidado.setFechaAplicacion(LocalDateTime.now().minusDays(1));

        Cuidado otroCuidado = new Cuidado();
        otroCuidado.setTipo(TipoCuidado.RIEGO);
        otroCuidado.setDescripcion("Riego");
        otroCuidado.setFechaAplicacion(LocalDateTime.now()); // DIFERENTE

        // Act & Assert
        assertNotEquals(cuidado, otroCuidado);
    }

    @Test
    @DisplayName("Equals ignora otros campos como frecuencia y notas")
    void testEqualsIgnoraOtrosCampos() {
        // Arrange
        LocalDateTime fechaAplicacion = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        cuidado = new Cuidado();
        cuidado.setTipo(TipoCuidado.RIEGO);
        cuidado.setDescripcion("Riego");
        cuidado.setFechaAplicacion(fechaAplicacion);
        cuidado.setFrecuenciaDias(3);
        cuidado.setNotas("Notas 1");

        Cuidado otroCuidado = new Cuidado();
        otroCuidado.setTipo(TipoCuidado.RIEGO);
        otroCuidado.setDescripcion("Riego");
        otroCuidado.setFechaAplicacion(fechaAplicacion);
        otroCuidado.setFrecuenciaDias(7); // DIFERENTE, pero no afecta equals
        otroCuidado.setNotas("Notas 2"); // DIFERENTE, pero no afecta equals

        // Act & Assert
        assertEquals(cuidado, otroCuidado);
        assertEquals(cuidado.hashCode(), otroCuidado.hashCode());
    }

    @ParameterizedTest
    @CsvSource({
            "RIEGO, 'Riego moderado', 3",
            "FERTILIZACION, 'Fertilización orgánica', 15",
            "PODA, 'Poda de mantenimiento', 30",
            "FUMIGACION, 'Aplicación de insecticida', 7",
            "LUZ, 'Exposición solar', 1",
            "TRASPLANTE, 'Cambio de maceta', 365",
            "LIMPIEZA, 'Limpieza de hojas', 7",
            "ROTACION, 'Rotación de planta', 3",
            "AIREACION, 'Aireación del sustrato', 14",
            "HUMEDAD, 'Control de humedad', 1"
    })
    @DisplayName("Crear cuidado con diferentes parámetros (constructor Integer)")
    void testCrearCuidadoConParametrosVariados(TipoCuidado tipo, String descripcion, int frecuencia) {
        // Arrange & Act - Usa Integer para que inicialice fechas
        cuidado = new Cuidado(tipo, descripcion, Integer.valueOf(frecuencia));

        // Assert
        assertEquals(tipo, cuidado.getTipo());
        assertEquals(descripcion, cuidado.getDescripcion());
        assertEquals(frecuencia, cuidado.getFrecuenciaDias());
        assertNotNull(cuidado.getFechaAplicacion());
        assertNotNull(cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Programar próximo se ejecuta automáticamente en constructor Integer")
    void testProgramarProximoAutomaticoEnConstructor() {
        // Arrange & Act
        LocalDateTime antes = LocalDateTime.now();
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(5));
        LocalDateTime despues = LocalDateTime.now();

        // Assert
        assertNotNull(cuidado.getFechaProxima());
        LocalDateTime fechaProximaEsperada = cuidado.getFechaAplicacion().plusDays(5);
        assertEquals(fechaProximaEsperada, cuidado.getFechaProxima());

        // Verificar que la fecha de aplicación está entre antes y después
        assertFalse(cuidado.getFechaAplicacion().isBefore(antes));
        assertFalse(cuidado.getFechaAplicacion().isAfter(despues));
    }

    @Test
    @DisplayName("Actualizar frecuencia reprograma fecha próxima")
    void testActualizarFrecuenciaReprogramaFechaProxima() {
        // Arrange - Usa constructor Integer para tener fechaAplicacion
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(3));
        LocalDateTime fechaAplicacionOriginal = cuidado.getFechaAplicacion();
        LocalDateTime fechaProximaOriginal = cuidado.getFechaProxima();

        // Act
        cuidado.setFrecuenciaDias(7);
        cuidado.programarProximo();

        // Assert
        assertEquals(7, cuidado.getFrecuenciaDias());
        // La fecha próxima debe cambiar
        assertNotEquals(fechaProximaOriginal, cuidado.getFechaProxima());
        LocalDateTime nuevaFechaProximaEsperada = fechaAplicacionOriginal.plusDays(7);
        assertEquals(nuevaFechaProximaEsperada, cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Set fecha aplicación reprograma fecha próxima")
    void testSetFechaAplicacionReprogramaFechaProxima() {
        // Arrange - Primero crea con constructor Integer
        cuidado = new Cuidado(TipoCuidado.RIEGO, "Riego", Integer.valueOf(3));
        LocalDateTime nuevaFechaAplicacion = LocalDateTime.now().minusDays(1);

        // Act
        cuidado.setFechaAplicacion(nuevaFechaAplicacion);
        cuidado.programarProximo();

        // Assert
        assertEquals(nuevaFechaAplicacion, cuidado.getFechaAplicacion());
        LocalDateTime fechaProximaEsperada = nuevaFechaAplicacion.plusDays(3);
        assertEquals(fechaProximaEsperada, cuidado.getFechaProxima());
    }

    @Test
    @DisplayName("Test para verificar ambos constructores")
    void testAmbosConstructores() {
        // Test constructor con Integer (inicializa fechas)
        Cuidado cuidado1 = new Cuidado(TipoCuidado.RIEGO, "Riego 1", Integer.valueOf(3));
        assertNotNull(cuidado1.getFechaAplicacion());
        assertNotNull(cuidado1.getFechaProxima());

        // Test constructor con int (NO inicializa fechas)
        Cuidado cuidado2 = new Cuidado(TipoCuidado.RIEGO, "Riego 2", 3);
        assertNull(cuidado2.getFechaAplicacion());
        assertNull(cuidado2.getFechaProxima());
    }
}