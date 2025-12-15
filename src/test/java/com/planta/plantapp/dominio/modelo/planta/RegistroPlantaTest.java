package com.planta.plantapp.dominio.modelo.planta;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegistroPlantaTest {

    private Usuario usuario;
    private RegistroPlanta registroPlanta;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan", "juan@gmail.com", "juancito");
        registroPlanta = new RegistroPlanta("Mi plantita", usuario);
    }

    @Test
    void constructor_DeberiaCrearRegistroPlantaConValoresPorDefecto() {
        // Assert
        assertNotNull(registroPlanta);
        assertEquals("Mi plantita", registroPlanta.getApodo());
        assertEquals(usuario, registroPlanta.getUsuario());
        assertNotNull(registroPlanta.getFechaRegistro());
        assertEquals(EstadoPlanta.SALUDABLE, registroPlanta.getEstado());
        assertNotNull(registroPlanta.getBitacoras());
        assertTrue(registroPlanta.getBitacoras().isEmpty());
    }

    @Test
    void cambiarEstado_DeberiaActualizarEstadoCorrectamente() {
        // Act
        registroPlanta.cambiarEstado(EstadoPlanta.ENFERMA);

        // Assert
        assertEquals(EstadoPlanta.ENFERMA, registroPlanta.getEstado());
    }

    @Test
    void agregarBitacora_DeberiaAgregarBitacoraCorrectamente() {
        // Arrange
        Bitacora bitacora = new Bitacora();

        // Act
        registroPlanta.agregarBitacora(bitacora);

        // Assert
        assertEquals(1, registroPlanta.getBitacoras().size());
        assertTrue(registroPlanta.getBitacoras().contains(bitacora));
    }

    @Test
    void agregarBitacora_DeberiaInicializarSet_CuandoEsNulo() {
        // Arrange
        registroPlanta.setBitacoras(null);
        Bitacora bitacora = new Bitacora();

        // Act
        registroPlanta.agregarBitacora(bitacora);

        // Assert
        assertNotNull(registroPlanta.getBitacoras());
        assertTrue(registroPlanta.getBitacoras().contains(bitacora));
    }

    @Test
    void setApodo_DeberiaActualizarApodoCorrectamente() {
        // Act
        registroPlanta.setApodo("Nueva plantita");

        // Assert
        assertEquals("Nueva plantita", registroPlanta.getApodo());
    }

    @Test
    void setEstado_DeberiaActualizarEstadoCorrectamente() {
        // Act
        registroPlanta.setEstado(EstadoPlanta.MARCHITA);

        // Assert
        assertEquals(EstadoPlanta.MARCHITA, registroPlanta.getEstado());
    }

    @Test
    void setUsuario_DeberiaActualizarUsuarioCorrectamente() {
        // Arrange
        Usuario nuevoUsuario = new Usuario("Juan", "juan@gmail.com", "juancito");

        // Act
        registroPlanta.setUsuario(nuevoUsuario);

        // Assert
        assertEquals(nuevoUsuario, registroPlanta.getUsuario());
    }

    @Test
    void setBitacoras_DeberiaActualizarBitacorasCorrectamente() {
        // Arrange
        Set<Bitacora> bitacoras = new HashSet<>();

        // Act
        registroPlanta.setBitacoras(bitacoras);

        // Assert
        assertEquals(bitacoras, registroPlanta.getBitacoras());
    }

    @Test
    void equals_DeberiaRetornarTrue_CuandoIdsSonIguales() {
        // Arrange
        RegistroPlanta rp1 = new RegistroPlanta("Planta A", usuario);
        RegistroPlanta rp2 = new RegistroPlanta("Planta B", usuario);

        rp1.setEstado(EstadoPlanta.SALUDABLE);
        rp2.setEstado(EstadoPlanta.ENFERMA);

        // Simular mismo ID (caso típico de JPA)
        setId(rp1, 1);
        setId(rp2, 1);

        // Act & Assert
        assertEquals(rp1, rp2);
    }

    @Test
    void equals_DeberiaRetornarFalse_CuandoIdsSonDiferentes() {
        // Arrange
        RegistroPlanta rp1 = new RegistroPlanta("Planta A", usuario);
        RegistroPlanta rp2 = new RegistroPlanta("Planta B", usuario);

        setId(rp1, 1);
        setId(rp2, 2);

        // Act & Assert
        assertNotEquals(rp1, rp2);
    }

    @Test
    void hashCode_DeberiaSerConsistente_ConEquals() {
        // Arrange
        RegistroPlanta rp1 = new RegistroPlanta("Planta A", usuario);
        RegistroPlanta rp2 = new RegistroPlanta("Planta B", usuario);

        setId(rp1, 5);
        setId(rp2, 5);

        // Act & Assert
        assertEquals(rp1.hashCode(), rp2.hashCode());
    }

    @Test
    void hashCode_NoDeberiaLanzarExcepcion() {
        // Act & Assert
        assertDoesNotThrow(registroPlanta::hashCode);
    }

    // 🔧 Helper para setear ID (simula JPA)
    private void setId(RegistroPlanta registroPlanta, int id) {
        try {
            var field = RegistroPlanta.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(registroPlanta, id);
        } catch (Exception e) {
            fail("No se pudo asignar el ID para pruebas");
        }
    }

    static class EstadoPlantaTest {

        @Test
        void getNombre_DeberiaRetornarNombreCorrecto() {
            assertEquals("Saludable", EstadoPlanta.SALUDABLE.getNombre());
            assertEquals("Necesita Agua", EstadoPlanta.NECESITA_AGUA.getNombre());
            assertEquals("Enferma", EstadoPlanta.ENFERMA.getNombre());
        }

        @Test
        void getDescripcion_DeberiaRetornarDescripcionCorrecta() {
            assertEquals(
                    "La planta está en buen estado",
                    EstadoPlanta.SALUDABLE.getDescripcion()
            );

            assertEquals(
                    "La planta requiere riego",
                    EstadoPlanta.NECESITA_AGUA.getDescripcion()
            );
        }

        @Test
        void requiereCuidadoInmediato_DeberiaRetornarTrue_ParaEstadosCriticos() {
            assertTrue(EstadoPlanta.NECESITA_AGUA.requiereCuidadoInmediato());
            assertTrue(EstadoPlanta.ENFERMA.requiereCuidadoInmediato());
            assertTrue(EstadoPlanta.MARCHITA.requiereCuidadoInmediato());
        }

        @Test
        void requiereCuidadoInmediato_DeberiaRetornarFalse_ParaEstadosNoCriticos() {
            assertFalse(EstadoPlanta.SALUDABLE.requiereCuidadoInmediato());
            assertFalse(EstadoPlanta.FLORECIENDO.requiereCuidadoInmediato());
            assertFalse(EstadoPlanta.CRECIENDO.requiereCuidadoInmediato());
            assertFalse(EstadoPlanta.DORMIDA.requiereCuidadoInmediato());
        }

        @Test
        void esEstadoPositivo_DeberiaRetornarTrue_ParaEstadosPositivos() {
            assertTrue(EstadoPlanta.SALUDABLE.esEstadoPositivo());
            assertTrue(EstadoPlanta.FLORECIENDO.esEstadoPositivo());
            assertTrue(EstadoPlanta.CRECIENDO.esEstadoPositivo());
        }

        @Test
        void esEstadoPositivo_DeberiaRetornarFalse_ParaEstadosNegativos() {
            assertFalse(EstadoPlanta.NECESITA_AGUA.esEstadoPositivo());
            assertFalse(EstadoPlanta.ENFERMA.esEstadoPositivo());
            assertFalse(EstadoPlanta.MARCHITA.esEstadoPositivo());
            assertFalse(EstadoPlanta.DORMIDA.esEstadoPositivo());
        }

        @Test
        void toString_DeberiaRetornarNombreDelEstado() {
            assertEquals("Saludable", EstadoPlanta.SALUDABLE.toString());
            assertEquals("Floreciendo", EstadoPlanta.FLORECIENDO.toString());
        }

        @Test
        void enum_DeberiaContenerTodosLosValoresEsperados() {
            EstadoPlanta[] valores = EstadoPlanta.values();

            assertEquals(8, valores.length);
            assertArrayEquals(
                    new EstadoPlanta[]{
                            EstadoPlanta.SALUDABLE,
                            EstadoPlanta.NECESITA_AGUA,
                            EstadoPlanta.NECESITA_FERTILIZANTE,
                            EstadoPlanta.ENFERMA,
                            EstadoPlanta.MARCHITA,
                            EstadoPlanta.FLORECIENDO,
                            EstadoPlanta.CRECIENDO,
                            EstadoPlanta.DORMIDA
                    },
                    valores
            );
        }
    }
}
