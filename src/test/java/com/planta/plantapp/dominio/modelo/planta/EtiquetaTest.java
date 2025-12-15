package com.planta.plantapp.dominio.modelo.planta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtiquetaTest {

    @Test
    void constructorVacio_DeberiaCrearInstanciaConValoresPorDefecto() {
        // Act
        Etiqueta etiqueta = new Etiqueta();

        // Assert
        assertNotNull(etiqueta);
        assertEquals(0, etiqueta.getId());
        assertNull(etiqueta.getNombre());
        assertNull(etiqueta.getColor());
    }

    @Test
    void constructorCompleto_DeberiaCrearEtiquetaConDatosCorrectos() {
        // Act
        Etiqueta etiqueta = new Etiqueta(1, "Interior", "Verde");

        // Assert
        assertEquals(1, etiqueta.getId());
        assertEquals("Interior", etiqueta.getNombre());
        assertEquals("Verde", etiqueta.getColor());
    }

    @Test
    void constructorSinId_DeberiaAsignarNombreYColorCorrectamente() {
        // Act
        Etiqueta etiqueta = new Etiqueta("Exterior", "Azul");

        // Assert
        assertEquals(0, etiqueta.getId());
        assertEquals("Exterior", etiqueta.getNombre());
        assertEquals("Azul", etiqueta.getColor());
    }

    @Test
    void setters_DeberianActualizarValoresCorrectamente() {
        // Arrange
        Etiqueta etiqueta = new Etiqueta();

        // Act
        etiqueta.setId(10);
        etiqueta.setNombre("Sombra");
        etiqueta.setColor("Morado");

        // Assert
        assertEquals(10, etiqueta.getId());
        assertEquals("Sombra", etiqueta.getNombre());
        assertEquals("Morado", etiqueta.getColor());
    }

    @Test
    void equals_DeberiaRetornarTrue_CuandoObjetosSonIguales() {
        // Arrange
        Etiqueta etiqueta1 = new Etiqueta(1, "Interior", "Verde");
        Etiqueta etiqueta2 = new Etiqueta(1, "Interior", "Verde");

        // Act & Assert
        assertEquals(etiqueta1, etiqueta2);
        assertEquals(etiqueta1.hashCode(), etiqueta2.hashCode());
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoObjetosSonDiferentes() {
        Etiqueta etiqueta1 = new Etiqueta(1, "Interior", "Verde");
        Etiqueta etiqueta2 = new Etiqueta(2, "Exterior", "Azul");

        assertNotEquals(etiqueta1, etiqueta2);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoObjetoEsNulo() {
        Etiqueta etiqueta = new Etiqueta(1, "Interior", "Verde");

        assertNotEquals(null, etiqueta);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoObjetoEsDeOtraClase() {
        Etiqueta etiqueta = new Etiqueta(1, "Interior", "Verde");

        assertNotEquals( "Etiqueta", etiqueta);
    }

    @Test
    void toString_DeberiaContenerCamposPrincipales() {
        Etiqueta etiqueta = new Etiqueta(1, "Interior", "Verde");

        String texto = etiqueta.toString();

        assertTrue(texto.contains("id=1"));
        assertTrue(texto.contains("Interior"));
        assertTrue(texto.contains("Verde"));
    }

    @Test
    void hashCode_DeberiaSerConsistente() {
        Etiqueta etiqueta = new Etiqueta(1, "Interior", "Verde");

        int hash1 = etiqueta.hashCode();
        int hash2 = etiqueta.hashCode();

        assertEquals(hash1, hash2);
    }
}
