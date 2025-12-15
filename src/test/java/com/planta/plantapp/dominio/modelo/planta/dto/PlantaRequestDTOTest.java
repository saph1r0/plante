package com.planta.plantapp.dominio.modelo.planta.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlantaRequestDTOTest {

    @Test
    void constructorVacio_DeberiaCrearInstancia_Exitosamente() {
        // Act
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Assert
        assertNotNull(dto);
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
        PlantaRequestDTO dto = new PlantaRequestDTO(
                "Rosa",
                "Rosa rubiginosa",
                "Flor",
                "Planta ornamental",
                "http://imagen.com/rosa.jpg",
                1L
        );

        // Assert
        assertNotNull(dto);
        assertEquals("Rosa", dto.getNombreComun());
        assertEquals("Rosa rubiginosa", dto.getNombreCientifico());
        assertEquals("Flor", dto.getTipo());
        assertEquals("Planta ornamental", dto.getDescripcion());
        assertEquals("http://imagen.com/rosa.jpg", dto.getImagenURL());
        assertEquals(1L, dto.getUsuarioId());
    }

    @Test
    void constructorCompleto_DeberiaPermitirValoresNulos() {
        // Act
        PlantaRequestDTO dto = new PlantaRequestDTO(null, null, null, null, null, null);

        // Assert
        assertNotNull(dto);
        assertNull(dto.getNombreComun());
        assertNull(dto.getNombreCientifico());
        assertNull(dto.getTipo());
        assertNull(dto.getDescripcion());
        assertNull(dto.getImagenURL());
        assertNull(dto.getUsuarioId());
    }

    @Test
    void setNombreComun_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Act
        dto.setNombreComun("Cactus");

        // Assert
        assertEquals("Cactus", dto.getNombreComun());
    }

    @Test
    void setNombreCientifico_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Act
        dto.setNombreCientifico("Cactaceae");

        // Assert
        assertEquals("Cactaceae", dto.getNombreCientifico());
    }

    @Test
    void setTipo_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Act
        dto.setTipo("Suculenta");

        // Assert
        assertEquals("Suculenta", dto.getTipo());
    }

    @Test
    void setDescripcion_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Act
        dto.setDescripcion("Planta resistente");

        // Assert
        assertEquals("Planta resistente", dto.getDescripcion());
    }

    @Test
    void setImagenURL_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Act
        dto.setImagenURL("http://imagen.com/cactus.jpg");

        // Assert
        assertEquals("http://imagen.com/cactus.jpg", dto.getImagenURL());
    }

    @Test
    void setUsuarioId_DeberiaAsignarCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();

        // Act
        dto.setUsuarioId(10L);

        // Assert
        assertEquals(10L, dto.getUsuarioId());
    }

    @Test
    void setCuidados_DeberiaAsignarListaCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();
        List<CuidadoDTO> cuidados = List.of(new CuidadoDTO());

        // Act
        dto.setCuidados(cuidados);

        // Assert
        assertEquals(cuidados, dto.getCuidados());
    }

    @Test
    void setEtiquetas_DeberiaAsignarListaCorrectamente() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();
        List<EtiquetaDTO> etiquetas = List.of(new EtiquetaDTO());

        // Act
        dto.setEtiquetas(etiquetas);

        // Assert
        assertEquals(etiquetas, dto.getEtiquetas());
    }

    @Test
    void getters_DeberianRetornarValoresCorrectos() {
        // Arrange
        PlantaRequestDTO dto = new PlantaRequestDTO();
        List<CuidadoDTO> cuidados = List.of(new CuidadoDTO());
        List<EtiquetaDTO> etiquetas = List.of(new EtiquetaDTO());

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
