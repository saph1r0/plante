package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.excepciones.PlantaNotFoundException;
import com.planta.plantapp.aplicacion.excepciones.PlantaServiceException;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioPlantaImplTest {

    @Mock
    private IPlantaRepositorio repositorioMock;

    @InjectMocks
    private ServicioPlantaImpl servicio;

    private Planta plantaPrueba;

    @BeforeEach
    void setUp() {
        plantaPrueba = new Planta(
                "Rosa",
                "Rosa rubiginosa",
                "Planta ornamental de flores rojas",
                "https://ejemplo.com/rosa.jpg"
        );
        plantaPrueba.setId("planta123");
        plantaPrueba.setEstado("Saludable");
    }

    @Test
    void obtenerTodas_DeberiaRetornarListaDePlantas_Exitosamente() {
        // Arrange
        Planta planta2 = new Planta("Tulipán", "Tulipa", "Flor", "url");
        List<Planta> plantas = Arrays.asList(plantaPrueba, planta2);
        when(repositorioMock.listarPorUsuario("global")).thenReturn(plantas);

        // Act
        List<Planta> resultado = servicio.obtenerTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositorioMock).listarPorUsuario("global");
    }

    @Test
    void obtenerTodas_DeberiaRetornarListaVacia_CuandoHayExcepcion() {
        // Arrange
        when(repositorioMock.listarPorUsuario("global"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act
        List<Planta> resultado = servicio.obtenerTodas();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repositorioMock).listarPorUsuario("global");
    }

    @Test
    void obtenerPorId_DeberiaRetornarOptionalConPlanta_CuandoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("planta123")).thenReturn(plantaPrueba);

        // Act
        Optional<Planta> resultado = servicio.obtenerPorId("planta123");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Rosa", resultado.get().getNombreComun());
        verify(repositorioMock).obtenerPorId("planta123");
    }

    @Test
    void obtenerPorId_DeberiaRetornarOptionalVacio_CuandoNoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("planta999")).thenReturn(null);

        // Act
        Optional<Planta> resultado = servicio.obtenerPorId("planta999");

        // Assert
        assertFalse(resultado.isPresent());
        verify(repositorioMock).obtenerPorId("planta999");
    }

    @Test
    void obtenerPorId_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        when(repositorioMock.obtenerPorId("planta123"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.obtenerPorId("planta123"));
        verify(repositorioMock).obtenerPorId("planta123");
    }

    @Test
    void obtenerPorIdLong_DeberiaRetornarPlanta_CuandoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("123")).thenReturn(plantaPrueba);

        // Act
        Planta resultado = servicio.obtenerPorId(123L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Rosa", resultado.getNombreComun());
        verify(repositorioMock).obtenerPorId("123");
    }

    @Test
    void obtenerPorIdLong_DeberiaLanzarPlantaNotFoundException_CuandoNoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("999")).thenReturn(null);

        // Act & Assert
        assertThrows(PlantaNotFoundException.class,
                () -> servicio.obtenerPorId(999L));
        verify(repositorioMock).obtenerPorId("999");
    }

    @Test
    void guardar_DeberiaGuardarPlanta_Exitosamente() {
        // Arrange
        doNothing().when(repositorioMock).guardar(plantaPrueba);

        // Act
        Planta resultado = servicio.guardar(plantaPrueba);

        // Assert
        assertNotNull(resultado);
        assertEquals("Rosa", resultado.getNombreComun());
        verify(repositorioMock).guardar(plantaPrueba);
    }

    @Test
    void guardar_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        doThrow(new RuntimeException("Error al guardar"))
                .when(repositorioMock).guardar(plantaPrueba);

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.guardar(plantaPrueba));
        verify(repositorioMock).guardar(plantaPrueba);
    }

    @Test
    void eliminarString_DeberiaEliminarPlanta_Exitosamente() {
        // Arrange
        doNothing().when(repositorioMock).eliminar("planta123");

        // Act & Assert
        assertDoesNotThrow(() -> servicio.eliminar("planta123"));
        verify(repositorioMock).eliminar("planta123");
    }

    @Test
    void eliminarString_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        doThrow(new RuntimeException("Error al eliminar"))
                .when(repositorioMock).eliminar("planta123");

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.eliminar("planta123"));
        verify(repositorioMock).eliminar("planta123");
    }

    @Test
    void eliminarLong_DeberiaEliminarPlanta_Exitosamente() {
        // Arrange
        doNothing().when(repositorioMock).eliminar("123");

        // Act & Assert
        assertDoesNotThrow(() -> servicio.eliminar(123L));
        verify(repositorioMock).eliminar("123");
    }

    @Test
    void buscarPorTipo_DeberiaRetornarListaDePlantas_Exitosamente() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(repositorioMock.buscarPorTipo("Ornamental")).thenReturn(plantas);

        // Act
        List<Planta> resultado = servicio.buscarPorTipo("Ornamental");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositorioMock).buscarPorTipo("Ornamental");
    }

    @Test
    void buscarPorTipo_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        when(repositorioMock.buscarPorTipo("Ornamental"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.buscarPorTipo("Ornamental"));
        verify(repositorioMock).buscarPorTipo("Ornamental");
    }

    @Test
    void buscarPorUsuario_DeberiaRetornarListaDePlantas_Exitosamente() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(repositorioMock.listarPorUsuario("1")).thenReturn(plantas);

        // Act
        List<Planta> resultado = servicio.buscarPorUsuario(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositorioMock).listarPorUsuario("1");
    }

    @Test
    void buscarPorUsuario_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        when(repositorioMock.listarPorUsuario("1"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.buscarPorUsuario(1L));
        verify(repositorioMock).listarPorUsuario("1");
    }

    @Test
    void listarPorUsuario_DeberiaRetornarListaDePlantas_Exitosamente() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(repositorioMock.listarPorUsuario("1")).thenReturn(plantas);

        // Act
        List<Planta> resultado = servicio.listarPorUsuario(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositorioMock).listarPorUsuario("1");
    }

    @Test
    void agregarCuidado_DeberiaAgregarCuidado_Exitosamente() {
        // Arrange
        when(repositorioMock.obtenerPorId("1")).thenReturn(plantaPrueba);
        doNothing().when(repositorioMock).guardar(plantaPrueba);

        // Act & Assert
        assertDoesNotThrow(() -> servicio.agregarCuidado(1L, TipoCuidado.RIEGO, 3, "Regar cada 3 días"));
        verify(repositorioMock).obtenerPorId("1");
        verify(repositorioMock).guardar(plantaPrueba);
    }

    @Test
    void agregarCuidado_DeberiaLanzarPlantaNotFoundException_CuandoPlantaNoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("999")).thenReturn(null);

        // Act & Assert
        assertThrows(PlantaNotFoundException.class,
                () -> servicio.agregarCuidado(999L, TipoCuidado.RIEGO, 3, "Notas"));
        verify(repositorioMock).obtenerPorId("999");
    }

    @Test
    void agregarCuidado_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        when(repositorioMock.obtenerPorId("1"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.agregarCuidado(1L, TipoCuidado.RIEGO, 3, "Notas"));
        verify(repositorioMock).obtenerPorId("1");
    }

    @Test
    void contarPorEstado_DeberiaRetornarCantidad_Correctamente() {
        // Arrange
        Planta planta2 = new Planta("Tulipán", "Tulipa", "Flor", "url");
        planta2.setEstado("Saludable");
        Planta planta3 = new Planta("Cactus", "Cactaceae", "Suculenta", "url");
        planta3.setEstado("Marchita");

        List<Planta> plantas = Arrays.asList(plantaPrueba, planta2, planta3);
        when(repositorioMock.listarPorUsuario("global")).thenReturn(plantas);

        // Act
        int resultado = servicio.contarPorEstado("Saludable");

        // Assert
        assertEquals(2, resultado);
        verify(repositorioMock).listarPorUsuario("global");
    }

    @Test
    void contarPorEstado_DeberiaRetornarCero_CuandoNoHayCoincidencias() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(repositorioMock.listarPorUsuario("global")).thenReturn(plantas);

        // Act
        int resultado = servicio.contarPorEstado("Marchita");

        // Assert
        assertEquals(0, resultado);
        verify(repositorioMock).listarPorUsuario("global");
    }

    @Test
    void contarPorEstado_DeberiaRetornarCero_CuandoObtenerTodasFalla() {
        // Arrange
        when(repositorioMock.listarPorUsuario("global"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act
        int resultado = servicio.contarPorEstado("Saludable");

        // Assert
        assertEquals(0, resultado); // obtenerTodas() retorna lista vacía cuando falla
        verify(repositorioMock).listarPorUsuario("global");
    }

    @Test
    void buscarPorNombre_DeberiaRetornarListaDePlantas_Exitosamente() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(repositorioMock.buscarPorNombre("Rosa", "user1")).thenReturn(plantas);

        // Act
        List<Planta> resultado = servicio.buscarPorNombre("Rosa", "user1");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositorioMock).buscarPorNombre("Rosa", "user1");
    }

    @Test
    void buscarPorNombre_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        when(repositorioMock.buscarPorNombre("Rosa", "user1"))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.buscarPorNombre("Rosa", "user1"));
        verify(repositorioMock).buscarPorNombre("Rosa", "user1");
    }

    @Test
    void actualizarEstado_DeberiaActualizarEstado_Exitosamente() {
        // Arrange
        doNothing().when(repositorioMock).actualizarEstado("planta123", "Marchita");

        // Act & Assert
        assertDoesNotThrow(() -> servicio.actualizarEstado("planta123", "Marchita"));
        verify(repositorioMock).actualizarEstado("planta123", "Marchita");
    }

    @Test
    void actualizarEstado_DeberiaLanzarPlantaServiceException_CuandoHayError() {
        // Arrange
        doThrow(new RuntimeException("Error al actualizar"))
                .when(repositorioMock).actualizarEstado("planta123", "Marchita");

        // Act & Assert
        assertThrows(PlantaServiceException.class,
                () -> servicio.actualizarEstado("planta123", "Marchita"));
        verify(repositorioMock).actualizarEstado("planta123", "Marchita");
    }

    @Test
    void contarPorUsuario_DeberiaRetornarCantidad_Correctamente() {
        // Arrange
        when(repositorioMock.contarPorUsuario("user1")).thenReturn(5L);

        // Act
        Long resultado = servicio.contarPorUsuario("user1");

        // Assert
        assertEquals(5L, resultado);
        verify(repositorioMock).contarPorUsuario("user1");
    }
}