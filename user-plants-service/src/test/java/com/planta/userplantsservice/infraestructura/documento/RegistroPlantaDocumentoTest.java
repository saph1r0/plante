package com.planta.userplantsservice.infraestructura.documento;

import com.planta.userplantsservice.dominio.modelo.planta.EstadoPlanta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistroPlantaDocumentoTest {

    @Test
    void constructor_DeberiaInicializarFechaYEstadoPorDefecto() {
        RegistroPlantaDocumento doc = new RegistroPlantaDocumento(
                "u1", "p1", "Apodo", "Sala", null);

        assertNotNull(doc.getFechaRegistro());
        assertEquals(EstadoPlanta.SALUDABLE, doc.getEstado());
        assertEquals("u1", doc.getUsuarioId());
    }
}