package com.planta.plantapp.dominio.modelo.planta.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtiquetaDTOTest {

    @Test
    void constructorVacio_DeberiaCrearInstancia_Exitosamente() {
        // Act
        EtiquetaDTO etiqueta = new EtiquetaDTO();

        // Assert
        assertNotNull(etiqueta);
        assertEquals(0, etiqueta.getId());
        assertNull(etiqueta.getNombre());
        assertNull(etiqueta.getColor());
    }

    @Test
    void constructorCompleto_DeberiaCrearInstancia_ConDatosCorrectos() {
        // Act
        EtiquetaDTO etiqueta = new EtiquetaDTO(1, "Interior", "verde");

        // Assert
        assertNotNull(etiqueta);
        assertEquals(1, etiqueta.getId());
        assertEquals("Interior", etiqueta.getNombre());
        assertEquals("verde", etiqueta.getColor());
    }

    @Test
    void constructorCompleto_DeberiaPermitirValoresNulos() {
        // Act
        EtiquetaDTO etiqueta = new EtiquetaDTO(0, null, null);

        // Assert
        assertNotNull(etiqueta);
        assertEquals(0, etiqueta.getId());
        assertNull(etiqueta.getNombre());
        assertNull(etiqueta.getColor());
    }

    @Test
    void setId_DeberiaAsignarId_Correctamente() {
        // Arrange
        EtiquetaDTO etiqueta = new EtiquetaDTO();

        // Act
        etiqueta.setId(5);

        // Assert
        assertEquals(5, etiqueta.getId());
    }

    @Test
    void setNombre_DeberiaAsignarNombre_Correctamente() {
        // Arrange
        EtiquetaDTO etiqueta = new EtiquetaDTO();

        // Act
        etiqueta.setNombre("Exterior");

        // Assert
        assertEquals("Exterior", etiqueta.getNombre());
    }

    @Test
    void setNombre_DeberiaPermitirNulo() {
        // Arrange
        EtiquetaDTO etiqueta = new EtiquetaDTO(1, "Interior", "verde");

        // Act
        etiqueta.setNombre(null);

        // Assert
        assertNull(etiqueta.getNombre());
    }

    @Test
    void setColor_DeberiaAsignarColor_Correctamente() {
        // Arrange
        EtiquetaDTO etiqueta = new EtiquetaDTO();

        // Act
        etiqueta.setColor("azul");

        // Assert
        assertEquals("azul", etiqueta.getColor());
    }

    @Test
    void setColor_DeberiaPermitirNulo() {
        // Arrange
        EtiquetaDTO etiqueta = new EtiquetaDTO(1, "Interior", "verde");

        // Act
        etiqueta.setColor(null);

        // Assert
        assertNull(etiqueta.getColor());
    }

    @Test
    void getters_DeberianRetornarValoresCorrectos() {
        // Arrange
        EtiquetaDTO etiqueta = new EtiquetaDTO(10, "Oficina", "gris");

        // Act & Assert
        assertAll(
                () -> assertEquals(10, etiqueta.getId()),
                () -> assertEquals("Oficina", etiqueta.getNombre()),
                () -> assertEquals("gris", etiqueta.getColor())
        );
    }
}
