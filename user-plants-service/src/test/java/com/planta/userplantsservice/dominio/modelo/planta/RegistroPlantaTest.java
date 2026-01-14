package com.planta.userplantsservice.dominio.modelo.planta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistroPlantaTest {

    private String usuarioId;
    private RegistroPlanta registroPlanta;

    @BeforeEach
    void setUp() {
        // ARREGLO: Ahora usamos un String para representar al usuario
        usuarioId = "user-123-abc";
        registroPlanta = new RegistroPlanta("Mi plantita", usuarioId);
    }

    @Test
    void constructor_DeberiaCrearRegistroPlantaConValoresPorDefecto() {
        assertNotNull(registroPlanta);
        assertEquals("Mi plantita", registroPlanta.getApodo());
        assertEquals(usuarioId, registroPlanta.getUsuarioId()); // Cambiado a getUsuarioId()
        assertNotNull(registroPlanta.getFechaRegistro());
        assertEquals(EstadoPlanta.SALUDABLE, registroPlanta.getEstado());
    }

    @Test
    void cambiarEstado_DeberiaActualizarEstadoCorrectamente() {
        registroPlanta.cambiarEstado(EstadoPlanta.ENFERMA);
        assertEquals(EstadoPlanta.ENFERMA, registroPlanta.getEstado());
    }

    // NOTA: Se eliminaron los tests de Bitacora porque esa lógica ya no pertenece
    // a este microservicio. La Bitacora será un microservicio independiente.

    @Test
    void setApodo_DeberiaActualizarApodoCorrectamente() {
        registroPlanta.setApodo("Nueva plantita");
        assertEquals("Nueva plantita", registroPlanta.getApodo());
    }

    @Test
    void setUsuarioId_DeberiaActualizarUsuarioIdCorrectamente() {
        String nuevoUsuarioId = "user-456-def";
        registroPlanta.setUsuarioId(nuevoUsuarioId);
        assertEquals(nuevoUsuarioId, registroPlanta.getUsuarioId());
    }

    @Test
    void equals_DeberiaRetornarTrue_CuandoIdsSonIguales() {
        RegistroPlanta rp1 = new RegistroPlanta("Planta A", usuarioId);
        RegistroPlanta rp2 = new RegistroPlanta("Planta B", usuarioId);

        // Ahora el ID es String (MongoDB)
        rp1.setId("id-unico-1");
        rp2.setId("id-unico-1");

        assertEquals(rp1, rp2);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoIdsSonDiferentes() {
        RegistroPlanta rp1 = new RegistroPlanta("Planta A", usuarioId);
        RegistroPlanta rp2 = new RegistroPlanta("Planta B", usuarioId);

        rp1.setId("id-1");
        rp2.setId("id-2");

        assertNotEquals(rp1, rp2);
    }

    @Test
    void hashCode_DeberiaSerConsistente_ConEquals() {
        RegistroPlanta rp1 = new RegistroPlanta("Planta A", usuarioId);
        RegistroPlanta rp2 = new RegistroPlanta("Planta B", usuarioId);

        rp1.setId("hash-test");
        rp2.setId("hash-test");

        assertEquals(rp1.hashCode(), rp2.hashCode());
    }

}