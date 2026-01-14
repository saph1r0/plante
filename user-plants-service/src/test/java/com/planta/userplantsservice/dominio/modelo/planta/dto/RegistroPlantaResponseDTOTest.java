package com.planta.userplantsservice.dominio.modelo.planta.dto;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class RegistroPlantaResponseDTOTest {

    @Test
    void testEstructuraDeRespuestaCompleta() {
        RegistroPlantaResponseDTO dto = new RegistroPlantaResponseDTO();
        Date fecha = new Date();

        dto.setId("mongo-id-999");
        dto.setUsuarioId("user-123");
        dto.setFechaRegistro(fecha);
        dto.setEstado("FLORECIENDO");

        assertEquals("mongo-id-999", dto.getId());
        assertEquals("user-123", dto.getUsuarioId());
        assertEquals(fecha, dto.getFechaRegistro());
        assertEquals("FLORECIENDO", dto.getEstado());
    }
}