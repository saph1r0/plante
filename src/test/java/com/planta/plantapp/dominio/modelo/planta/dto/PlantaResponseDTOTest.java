package com.planta.plantapp.dominio.modelo.planta.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlantaResponseDTOTest {

    @Test
    void constructorVacio_DeberiaCrearInstancia_Exitosamente() {
        // Act
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getNombreComun());
        assertNull(dto.getNombreCientifico());
        assertNull(dto.getTipo());
        assertNull(dto.getDescripcion());
        assertNull(dto.getImagenURL());
        assertNull(dto.getUsuarioId());
        assertNull(dto.getCuidados());
        assertNull(dto.getEtiquetas());
    }

    @Test
    void constructorCompleto_DeberiaCrearInstancia_ConDatosCorrectos() {
        // Act
        PlantaResponseDTO dto = new PlantaResponseDTO(
                "abc123",
                "Rosa",
                "Rosa rubiginosa",
                "Flor",
                "Planta ornamental",
                "https://imagen.com/rosa.jpg",
                1L
        );

        // Assert
        assertNotNull(dto);
        assertEquals("abc123", dto.getId());
        assertEquals("Rosa", dto.getNombreComun());
        assertEquals("Rosa rubiginosa", dto.getNombreCientifico());
        assertEquals("Flor", dto.getTipo());
        assertEquals("Planta ornamental", dto.getDescripcion());
        assertEquals("https://imagen.com/rosa.jpg", dto.getImagenURL());
        assertEquals(1L, dto.getUsuarioId());
    }

    @Test
    void constructorCompleto_DeberiaPermitirValoresNulos() {
        // Act
        PlantaResponseDTO dto = new PlantaResponseDTO(null, null, null, null, null, null, null);

        // Assert
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getNombreComun());
        assertNull(dto.getNombreCientifico());
        assertNull(dto.getTipo());
        assertNull(dto.getDescripcion());
        assertNull(dto.getImagenURL());
        assertNull(dto.getUsuarioId());
    }

    @Test
    void setId_DeberiaAsignarIdCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setId("id123");

        // Assert
        assertEquals("id123", dto.getId());
    }

    @Test
    void setNombreComun_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setNombreComun("Cactus");

        // Assert
        assertEquals("Cactus", dto.getNombreComun());
    }

    @Test
    void setNombreCientifico_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setNombreCientifico("Cactaceae");

        // Assert
        assertEquals("Cactaceae", dto.getNombreCientifico());
    }

    @Test
    void setTipo_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setTipo("Suculenta");

        // Assert
        assertEquals("Suculenta", dto.getTipo());
    }

    @Test
    void setDescripcion_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setDescripcion("Planta resistente");

        // Assert
        assertEquals("Planta resistente", dto.getDescripcion());
    }

    @Test
    void setImagenURL_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setImagenURL("https://imagen.com/cactus.jpg");

        // Assert
        assertEquals("https://imagen.com/cactus.jpg", dto.getImagenURL());
    }

    @Test
    void setUsuarioId_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();

        // Act
        dto.setUsuarioId(10L);

        // Assert
        assertEquals(10L, dto.getUsuarioId());
    }

    @Test
    void setCuidados_DeberiaAsignarListaCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();
        List<CuidadoDTO> cuidados = List.of(new CuidadoDTO());

        // Act
        dto.setCuidados(cuidados);

        // Assert
        assertEquals(cuidados, dto.getCuidados());
    }

    @Test
    void setEtiquetas_DeberiaAsignarListaCorrectamente() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();
        List<EtiquetaDTO> etiquetas = List.of(new EtiquetaDTO());

        // Act
        dto.setEtiquetas(etiquetas);

        // Assert
        assertEquals(etiquetas, dto.getEtiquetas());
    }

    @Test
    void getters_DeberianRetornarValoresCorrectos() {
        // Arrange
        PlantaResponseDTO dto = new PlantaResponseDTO();
        List<CuidadoDTO> cuidados = List.of(new CuidadoDTO());
        List<EtiquetaDTO> etiquetas = List.of(new EtiquetaDTO());

        dto.setId("xyz789");
        dto.setNombreComun("Lavanda");
        dto.setNombreCientifico("Lavandula");
        dto.setTipo("Hierba");
        dto.setDescripcion("Planta aromática");
        dto.setImagenURL("img.jpg");
        dto.setUsuarioId(5L);
        dto.setCuidados(cuidados);
        dto.setEtiquetas(etiquetas);

        // Act & Assert
        assertAll(
                () -> assertEquals("xyz789", dto.getId()),
                () -> assertEquals("Lavanda", dto.getNombreComun()),
                () -> assertEquals("Lavandula", dto.getNombreCientifico()),
                () -> assertEquals("Hierba", dto.getTipo()),
                () -> assertEquals("Planta aromática", dto.getDescripcion()),
                () -> assertEquals("img.jpg", dto.getImagenURL()),
                () -> assertEquals(5L, dto.getUsuarioId()),
                () -> assertEquals(cuidados, dto.getCuidados()),
                () -> assertEquals(etiquetas, dto.getEtiquetas())
        );
    }
}
