package com.planta.plantapp.dominio.modelo.planta.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("java:S2094")
class CuidadoDTOTest {

    @Test
    void constructorVacio_DeberiaCrearInstancia_Exitosamente() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO();

        // Assert
        assertNotNull(cuidado);
        assertNull(cuidado.getTipo());
        assertNull(cuidado.getDescripcion());
        assertEquals(0, cuidado.getFrecuenciaDias());
    }

    @Test
    void constructorCompleto_DeberiaCrearInstancia_ConDatosCorrectos() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO("RIEGO", "Regar cada 3 días", 3);

        // Assert
        assertNotNull(cuidado);
        assertEquals("RIEGO", cuidado.getTipo());
        assertEquals("Regar cada 3 días", cuidado.getDescripcion());
        assertEquals(3, cuidado.getFrecuenciaDias());
    }

    @Test
    void constructorCompleto_DeberiaPermitirValoresNulos() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO(null, null, 0);

        // Assert
        assertNotNull(cuidado);
        assertNull(cuidado.getTipo());
        assertNull(cuidado.getDescripcion());
        assertEquals(0, cuidado.getFrecuenciaDias());
    }

    @Test
    void setTipo_DeberiaAsignarTipo_Correctamente() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO();

        // Act
        cuidado.setTipo("FERTILIZACION");

        // Assert
        assertEquals("FERTILIZACION", cuidado.getTipo());
    }

    @Test
    void setTipo_DeberiaPermitirNulo() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO("RIEGO", "Descripción", 3);

        // Act
        cuidado.setTipo(null);

        // Assert
        assertNull(cuidado.getTipo());
    }

    @Test
    void setDescripcion_DeberiaAsignarDescripcion_Correctamente() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO();

        // Act
        cuidado.setDescripcion("Fertilizar mensualmente con abono orgánico");

        // Assert
        assertEquals("Fertilizar mensualmente con abono orgánico", cuidado.getDescripcion());
    }

    @Test
    void setDescripcion_DeberiaPermitirNulo() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO("RIEGO", "Descripción", 3);

        // Act
        cuidado.setDescripcion(null);

        // Assert
        assertNull(cuidado.getDescripcion());
    }

    @Test
    void setFrecuencia_DeberiaAsignarFrecuencia_Correctamente() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO();

        // Act
        cuidado.setFrecuencia(7);

        // Assert
        assertEquals(7, cuidado.getFrecuenciaDias());
    }

    @Test
    void setFrecuencia_DeberiaPermitirCero() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO("RIEGO", "Descripción", 5);

        // Act
        cuidado.setFrecuencia(0);

        // Assert
        assertEquals(0, cuidado.getFrecuenciaDias());
    }

    @Test
    void setFrecuencia_DeberiaPermitirNumerosNegativos() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO();

        // Act
        cuidado.setFrecuencia(-1);

        // Assert
        assertEquals(-1, cuidado.getFrecuenciaDias());
    }

    @Test
    void deberiaPermitirModificarTodosLosCampos_DespuesDeCreacion() {
        // Arrange
        CuidadoDTO cuidado = new CuidadoDTO("RIEGO", "Descripción inicial", 3);

        // Act
        cuidado.setTipo("PODA");
        cuidado.setDescripcion("Nueva descripción");
        cuidado.setFrecuencia(15);

        // Assert
        assertEquals("PODA", cuidado.getTipo());
        assertEquals("Nueva descripción", cuidado.getDescripcion());
        assertEquals(15, cuidado.getFrecuenciaDias());
    }

    @Test
    void deberiaCrearDTO_ConDatosTipicosDeRiego() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO("RIEGO", "Regar abundantemente", 3);

        // Assert
        assertEquals("RIEGO", cuidado.getTipo());
        assertEquals("Regar abundantemente", cuidado.getDescripcion());
        assertEquals(3, cuidado.getFrecuenciaDias());
    }

    @Test
    void deberiaCrearDTO_ConDatosTipicosDeFertilizacion() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO("FERTILIZACION", "Aplicar fertilizante NPK", 30);

        // Assert
        assertEquals("FERTILIZACION", cuidado.getTipo());
        assertEquals("Aplicar fertilizante NPK", cuidado.getDescripcion());
        assertEquals(30, cuidado.getFrecuenciaDias());
    }

    @Test
    void deberiaCrearDTO_ConDatosTipicosDePoda() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO("PODA", "Podar ramas secas", 90);

        // Assert
        assertEquals("PODA", cuidado.getTipo());
        assertEquals("Podar ramas secas", cuidado.getDescripcion());
        assertEquals(90, cuidado.getFrecuenciaDias());
    }

    @Test
    void deberiaPermitirFrecuenciasMuyAltas() {
        // Act
        CuidadoDTO cuidado = new CuidadoDTO("CONTROL_PLAGAS", "Inspección anual", 365);

        // Assert
        assertEquals(365, cuidado.getFrecuenciaDias());
    }

    @Test
    void deberiaPermitirDescripcionesLargas() {
        // Arrange
        String descripcionLarga = "Esta es una descripción muy detallada que explica " +
                "paso por paso cómo realizar el cuidado de la planta, incluyendo " +
                "recomendaciones específicas sobre temperatura, humedad y otros factores.";

        // Act
        CuidadoDTO cuidado = new CuidadoDTO("CUIDADO_ESPECIAL", descripcionLarga, 7);

        // Assert
        assertEquals(descripcionLarga, cuidado.getDescripcion());
    }
}