package com.planta.plantapp.dominio.modelo.planta;

import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlantaTest {

    private Planta plantaValida;

    @BeforeEach
    void setUp() {
        plantaValida = new Planta("Tulipán", "Tulipa", "descripción corta", "https://tulipan.com");
    }

    @Test
    void constructor_DeberiaCrearPlantaConDatosValidos_Exitosamente() {
        // Act
        Planta planta = new Planta("Rosa", "Rosa rubiginosa", "Flor roja", "https://rosa.com");

        // Assert
        assertNotNull(planta);
        assertEquals("Rosa", planta.getNombreComun());
        assertEquals("Rosa rubiginosa", planta.getNombreCientifico());
        assertEquals("Flor roja", planta.getDescripcion());
        assertEquals("https://rosa.com", planta.getImagenURL());
        assertNotNull(planta.getEtiquetas());
        assertNotNull(planta.getCuidados());
        assertTrue(planta.getEtiquetas().isEmpty());
        assertTrue(planta.getCuidados().isEmpty());
    }

    @Test
    void constructor_DeberiaLanzarExcepcion_CuandoNombreComunEsNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Planta(null, "Tulipa", "descripción", "url")
        );
        assertEquals("El nombre común no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void constructor_DeberiaLanzarExcepcion_CuandoNombreComunEsVacio() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Planta("", "Tulipa", "descripción", "url")
        );
        assertEquals("El nombre común no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void constructor_DeberiaLanzarExcepcion_CuandoNombreComunEsBlanco() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Planta("   ", "Tulipa", "descripción", "url")
        );
        assertEquals("El nombre común no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void constructor_DeberiaLanzarExcepcion_CuandoNombreCientificoEsNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Planta("Tulipán", null, "descripción", "url")
        );
        assertEquals("El nombre científico no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void constructor_DeberiaLanzarExcepcion_CuandoNombreCientificoEsVacio() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Planta("Tulipán", "", "descripción", "url")
        );
        assertEquals("El nombre científico no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void constructor_DeberiaLanzarExcepcion_CuandoNombreCientificoEsBlanco() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Planta("Tulipán", "   ", "descripción", "url")
        );
        assertEquals("El nombre científico no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void constructorConId_DeberiaCrearPlantaConIdYListasVacias() {
        // Act
        Planta planta = new Planta("planta123");

        // Assert
        assertNotNull(planta);
        assertEquals("planta123", planta.getId());
        assertNotNull(planta.getEtiquetas());
        assertNotNull(planta.getCuidados());
        assertTrue(planta.getEtiquetas().isEmpty());
        assertTrue(planta.getCuidados().isEmpty());
    }

    @Test
    void constructorProtegido_DeberiaCrearPlantaConListasVacias() {
        // Act
        Planta planta = new Planta() {};

        // Assert
        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        assertNotNull(planta.getCuidados());
        assertTrue(planta.getEtiquetas().isEmpty());
        assertTrue(planta.getCuidados().isEmpty());
    }

    @Test
    void cambiarDescripcion_DeberiaCambiarDescripcion_Exitosamente() {
        // Act
        plantaValida.cambiarDescripcion("Nueva descripción detallada");

        // Assert
        assertEquals("Nueva descripción detallada", plantaValida.getDescripcion());
    }

    @Test
    void cambiarImagen_DeberiaCambiarImagenURL_Exitosamente() {
        // Act
        plantaValida.cambiarImagen("https://nueva-imagen.com");

        // Assert
        assertEquals("https://nueva-imagen.com", plantaValida.getImagenURL());
    }

    @Test
    void agregarEtiqueta_DeberiaAgregarEtiqueta_CuandoEsValida() {
        // Arrange
        Etiqueta etiqueta = new Etiqueta("Ornamental", "azul");

        // Act
        plantaValida.agregarEtiqueta(etiqueta);

        // Assert
        assertEquals(1, plantaValida.getEtiquetas().size());
        assertTrue(plantaValida.getEtiquetas().contains(etiqueta));
    }

    @Test
    void agregarEtiqueta_NoDeberiaAgregarEtiquetaDuplicada() {
        // Arrange
        Etiqueta etiqueta = new Etiqueta("Ornamental", "azul");
        plantaValida.agregarEtiqueta(etiqueta);

        // Act
        plantaValida.agregarEtiqueta(etiqueta);

        // Assert
        assertEquals(1, plantaValida.getEtiquetas().size());
    }

    @Test
    void agregarEtiqueta_NoDeberiaAgregarEtiquetaNula() {
        // Act
        plantaValida.agregarEtiqueta(null);

        // Assert
        assertTrue(plantaValida.getEtiquetas().isEmpty());
    }

    @Test
    void quitarEtiqueta_DeberiaQuitarEtiqueta_CuandoExiste() {
        // Arrange
        Etiqueta etiqueta = new Etiqueta("Ornamental", "azul");
        plantaValida.agregarEtiqueta(etiqueta);

        // Act
        plantaValida.quitarEtiqueta(etiqueta);

        // Assert
        assertTrue(plantaValida.getEtiquetas().isEmpty());
    }

    @Test
    void quitarEtiqueta_NoDeberiaLanzarExcepcion_CuandoEtiquetaNoExiste() {
        // Arrange
        Etiqueta etiqueta = new Etiqueta("Inexistente", "blanco");

        // Act & Assert
        assertDoesNotThrow(() -> plantaValida.quitarEtiqueta(etiqueta));
    }

    @Test
    void agregarCuidado_DeberiaAgregarCuidado_CuandoEsValido() {
        // Arrange
        Cuidado cuidado = new Cuidado(TipoCuidado.RIEGO, "Regar cada 3 días", 3);

        // Act
        plantaValida.agregarCuidado(cuidado);

        // Assert
        assertEquals(1, plantaValida.getCuidados().size());
        assertTrue(plantaValida.getCuidados().contains(cuidado));
    }

    @Test
    void agregarCuidado_NoDeberiaAgregarCuidadoNulo() {
        // Act
        plantaValida.agregarCuidado(null);

        // Assert
        assertTrue(plantaValida.getCuidados().isEmpty());
    }

    @Test
    void agregarCuidado_DeberiaPermitirVariasCuidados() {
        // Arrange
        Cuidado cuidado1 = new Cuidado(TipoCuidado.RIEGO, "Regar", 3);
        Cuidado cuidado2 = new Cuidado(TipoCuidado.FERTILIZACION, "Fertilizar", 7);

        // Act
        plantaValida.agregarCuidado(cuidado1);
        plantaValida.agregarCuidado(cuidado2);

        // Assert
        assertEquals(2, plantaValida.getCuidados().size());
    }

    @Test
    void limpiarCuidados_DeberiaEliminarTodosLosCuidados() {
        // Arrange
        plantaValida.agregarCuidado(new Cuidado(TipoCuidado.RIEGO, "Regar", 3));
        plantaValida.agregarCuidado(new Cuidado(TipoCuidado.PODA, "Podar", 30));

        // Act
        plantaValida.limpiarCuidados();

        // Assert
        assertTrue(plantaValida.getCuidados().isEmpty());
    }

    @Test
    void getEtiquetas_DeberiaRetornarCopiaDeLista() {
        // Arrange
        Etiqueta etiqueta = new Etiqueta("Ornamental", "azul");
        plantaValida.agregarEtiqueta(etiqueta);

        // Act
        List<Etiqueta> etiquetas = plantaValida.getEtiquetas();
        etiquetas.clear();

        // Assert - La lista interna no debe haberse modificado
        assertEquals(1, plantaValida.getEtiquetas().size());
    }

    @Test
    void getCuidados_DeberiaRetornarCopiaDeLista() {
        // Arrange
        Cuidado cuidado = new Cuidado(TipoCuidado.RIEGO, "Regar", 3);
        plantaValida.agregarCuidado(cuidado);

        // Act
        List<Cuidado> cuidados = plantaValida.getCuidados();
        cuidados.clear();

        // Assert - La lista interna no debe haberse modificado
        assertEquals(1, plantaValida.getCuidados().size());
    }

    @Test
    void setId_DeberiaAsignarId_Correctamente() {
        // Act
        plantaValida.setId("nuevoId123");

        // Assert
        assertEquals("nuevoId123", plantaValida.getId());
    }

    @Test
    void setEstado_DeberiaAsignarEstado_Correctamente() {
        // Act
        plantaValida.setEstado("Saludable");

        // Assert
        assertEquals("Saludable", plantaValida.getEstado());
    }

    @Test
    void setEtiquetas_DeberiaReemplazarListaDeEtiquetas() {
        // Arrange
        Etiqueta etiqueta1 = new Etiqueta("Ornamental", "azul");
        Etiqueta etiqueta2 = new Etiqueta("Interior", "verde");
        List<Etiqueta> nuevasEtiquetas = List.of(etiqueta1, etiqueta2);

        // Act
        plantaValida.setEtiquetas(nuevasEtiquetas);

        // Assert
        assertEquals(2, plantaValida.getEtiquetas().size());
        assertTrue(plantaValida.getEtiquetas().contains(etiqueta1));
        assertTrue(plantaValida.getEtiquetas().contains(etiqueta2));
    }

    @Test
    void equals_DeberiaRetornarTrue_CuandoSonLaMismaInstancia() {
        // Act & Assert
        assertEquals(plantaValida, plantaValida);
    }

    @Test
    void equals_DeberiaRetornarTrue_CuandoTienenMismoId() {
        // Arrange
        plantaValida.setId("id123");
        Planta otraPlanta = new Planta("Rosa", "Rosa sp", "Otra planta", "url");
        otraPlanta.setId("id123");

        // Act & Assert
        assertEquals(plantaValida, otraPlanta);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoTienenDiferenteId() {
        // Arrange
        plantaValida.setId("id123");
        Planta otraPlanta = new Planta("Rosa", "Rosa sp", "Otra planta", "url");
        otraPlanta.setId("id456");

        // Act & Assert
        assertNotEquals(plantaValida, otraPlanta);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoComparaConNull() {
        // Act & Assert
        assertNotEquals(null, plantaValida);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoComparaConOtraClase() {
        // Act & Assert
        assertNotEquals("No es una planta", plantaValida);
    }

    @Test
    void hashCode_DeberiaSerIgual_ParaPlantasConMismoId() {
        // Arrange
        plantaValida.setId("id123");
        Planta otraPlanta = new Planta("Rosa", "Rosa sp", "Otra planta", "url");
        otraPlanta.setId("id123");

        // Act & Assert
        assertEquals(plantaValida.hashCode(), otraPlanta.hashCode());
    }

    @Test
    void hashCode_DeberiaSoportarIdNulo() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa sp", "Descripción", "url");
        // id es null por defecto

        // Act & Assert
        assertDoesNotThrow(planta::hashCode);
    }
}