package com.planta.userplantsservice.dominio.modelo.planta.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistroPlantaRequestDTOTest {

    @Test
    void testGettersYSetters() {
        RegistroPlantaRequestDTO dto = new RegistroPlantaRequestDTO();

        dto.setPlantaId("p123");
        dto.setApodo("Mi Helecho");
        dto.setUbicacion("Balcón");
        dto.setEstado("SALUDABLE");
        dto.setFotoPersonal("http://foto.jpg");
        dto.setNotas("Regar poco");

        assertEquals("p123", dto.getPlantaId());
        assertEquals("Mi Helecho", dto.getApodo());
        assertEquals("Balcón", dto.getUbicacion());
        assertEquals("SALUDABLE", dto.getEstado());
        assertEquals("http://foto.jpg", dto.getFotoPersonal());
        assertEquals("Regar poco", dto.getNotas());
    }
}