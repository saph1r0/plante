package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlantaRepositorioMongoDBTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private PlantaRepositorioMongoDB repositorio;

    private Planta plantaPrueba;

    @BeforeEach
    void setUp() {
        plantaPrueba = new Planta(
                "Rosa",
                "Rosa rubiginosa",
                "Planta ornamental de flores rojas",
                "https://ejemplo.com/rosa.jpg"
        );
    }

    @Test
    void obtenerPorId_DeberiaRetornarPlanta_CuandoExiste() {
        // Arrange
        when(mongoTemplate.findById("123", Planta.class)).thenReturn(plantaPrueba);

        // Act
        Planta resultado = repositorio.obtenerPorId("123");

        // Assert
        assertNotNull(resultado);
        assertEquals("Rosa", resultado.getNombreComun());
        verify(mongoTemplate).findById("123", Planta.class);
    }

    @Test
    void obtenerPorId_DeberiaRetornarNull_CuandoNoExiste() {
        // Arrange
        when(mongoTemplate.findById("999", Planta.class)).thenReturn(null);

        // Act
        Planta resultado = repositorio.obtenerPorId("999");

        // Assert
        assertNull(resultado);
    }

    @Test
    void listarPorUsuario_DeberiaRetornarTodasLasPlantas_CuandoEsGlobal() {
        // Arrange
        Planta planta2 = new Planta(
                "Tulipán",
                "Tulipa gesneriana",
                "Flor de primavera",
                "https://ejemplo.com/tulipan.jpg"
        );
        List<Planta> plantas = Arrays.asList(plantaPrueba, planta2);
        when(mongoTemplate.findAll(Planta.class)).thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.listarPorUsuario("global");

        // Assert
        assertEquals(2, resultado.size());
        verify(mongoTemplate).findAll(Planta.class);
    }

    @Test
    void listarPorUsuario_DeberiaFiltrarPorUsuario_CuandoNoEsGlobal() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(mongoTemplate.find(any(Query.class), eq(Planta.class))).thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.listarPorUsuario("user1");

        // Assert
        assertEquals(1, resultado.size());
        verify(mongoTemplate).find(any(Query.class), eq(Planta.class));
    }

    @Test
    void listarPorUsuario_DeberiaLanzarExcepcion_CuandoMongoFalla() {
        // Arrange
        when(mongoTemplate.findAll(Planta.class))
                .thenThrow(new RuntimeException("Error de conexión"));

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> repositorio.listarPorUsuario("global"));

        verify(mongoTemplate).findAll(Planta.class);
    }

    @Test
    void guardar_DeberiaGuardarPlanta_Exitosamente() {
        // Arrange
        when(mongoTemplate.save(plantaPrueba)).thenReturn(plantaPrueba);

        // Act
        assertDoesNotThrow(() -> repositorio.guardar(plantaPrueba));

        // Assert
        verify(mongoTemplate).save(plantaPrueba);
    }

    // ✨ NUEVO: Cubre el catch de guardar()
    @Test
    void guardar_DeberiaCapturarExcepcion_CuandoMongoFalla() {
        // Arrange
        when(mongoTemplate.save(any(Planta.class)))
                .thenThrow(new RuntimeException("Error de conexión MongoDB"));

        // Act - No debe lanzar excepción (la captura internamente)
        assertThrows(IllegalStateException.class, () -> repositorio.guardar(plantaPrueba));

        // Assert
        verify(mongoTemplate).save(plantaPrueba);
    }

    @Test
    void eliminar_DeberiaEliminarPlanta_Exitosamente() {
        // Act
        assertDoesNotThrow(() -> repositorio.eliminar("123"));

        // Assert
        verify(mongoTemplate).remove(any(Query.class), eq(Planta.class));
    }

    // ✨ NUEVO: Cubre el catch de eliminar()
    @Test
    void eliminar_DeberiaCapturarExcepcion_CuandoMongoFalla() {
        // Arrange
        doThrow(new RuntimeException("Error al eliminar"))
                .when(mongoTemplate).remove(any(Query.class), eq(Planta.class));

        // Act - No debe lanzar excepción
        assertThrows(IllegalStateException.class, () -> repositorio.eliminar("123"));

        // Assert
        verify(mongoTemplate).remove(any(Query.class), eq(Planta.class));
    }

    @Test
    void buscarPorNombre_DeberiaRetornarPlantas_CuandoCoinciden() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(mongoTemplate.find(any(Query.class), eq(Planta.class))).thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.buscarPorNombre("Rosa", "user1");

        // Assert
        assertEquals(1, resultado.size());
        verify(mongoTemplate).find(any(Query.class), eq(Planta.class));
    }

    @Test
    void buscarPorNombre_DeberiaUsarGlobal_CuandoUsuarioEsGlobal() {
        // Arrange
        when(mongoTemplate.find(any(Query.class), eq(Planta.class))).thenReturn(List.of());

        // Act
        List<Planta> resultado = repositorio.buscarPorNombre("Rosa", "global");

        // Assert
        assertNotNull(resultado);
        verify(mongoTemplate).find(any(Query.class), eq(Planta.class));
    }

    @Test
    void buscarPorNombre_DeberiaLanzarExcepcion_CuandoMongoFalla() {
        // Arrange
        when(mongoTemplate.find(any(Query.class), eq(Planta.class)))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> repositorio.buscarPorNombre("Rosa", "user1"));

        verify(mongoTemplate).find(any(Query.class), eq(Planta.class));
    }

    @Test
    void buscarPorNombre_DeberiaRetornarTodasLasPlantas_CuandoNombreVacioYUsuarioGlobal() {
        // Arrange
        List<Planta> plantas = Arrays.asList(plantaPrueba);
        when(mongoTemplate.findAll(Planta.class)).thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.buscarPorNombre("", "global");

        // Assert
        assertEquals(1, resultado.size());
        verify(mongoTemplate).findAll(Planta.class);
    }

    @Test
    void buscarPorNombre_DeberiaRetornarTodasLasPlantas_CuandoNombreNullYUsuarioGlobal() {
        // Arrange
        List<Planta> plantas = Arrays.asList(plantaPrueba);
        when(mongoTemplate.findAll(Planta.class)).thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.buscarPorNombre(null, "global");

        // Assert
        assertEquals(1, resultado.size());
        verify(mongoTemplate).findAll(Planta.class);
    }

    @Test
    void buscarPorNombre_DeberiaFiltrarPorUsuario_CuandoNombreVacio() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(mongoTemplate.find(any(Query.class), eq(Planta.class)))
                .thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.buscarPorNombre("", "user1");

        // Assert
        assertEquals(1, resultado.size());
        verify(mongoTemplate).find(any(Query.class), eq(Planta.class));
    }

    //Cubre actualizarEstado()
    @Test
    void actualizarEstado_DeberiaActualizarCorrectamente() {
        // Act
        assertDoesNotThrow(() -> repositorio.actualizarEstado("123", "Marchita"));

        // Assert
        verify(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(Planta.class));
    }

    //Cubre el catch de actualizarEstado()
    @Test
    void actualizarEstado_DeberiaCapturarExcepcion_CuandoMongoFalla() {
        // Arrange
        doThrow(new RuntimeException("Error al actualizar"))
                .when(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(Planta.class));

        // Act - No debe lanzar excepción
        assertThrows(IllegalStateException.class, () -> repositorio.actualizarEstado("123", "Crecimiento"));

        // Assert
        verify(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(Planta.class));
    }

    //Cubre buscarPorTipo()
    @Test
    void buscarPorTipo_DeberiaRetornarPlantas_DelTipoIndicado() {
        // Arrange
        List<Planta> plantas = List.of(plantaPrueba);
        when(mongoTemplate.find(any(Query.class), eq(Planta.class))).thenReturn(plantas);

        // Act
        List<Planta> resultado = repositorio.buscarPorTipo("Ornamental");

        // Assert
        assertEquals(1, resultado.size());
        verify(mongoTemplate).find(any(Query.class), eq(Planta.class));
    }

    //Cubre el catch de buscarPorTipo() que LANZA excepción
    @Test
    void buscarPorTipo_DeberiaLanzarExcepcion_CuandoMongoFalla() {
        // Arrange
        when(mongoTemplate.find(any(Query.class), eq(Planta.class)))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert - Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.buscarPorTipo("Ornamental"));
    }

    @Test
    void contarPorUsuario_DeberiaRetornarTotal_CuandoEsGlobal() {
        // Arrange
        when(mongoTemplate.count(any(Query.class), eq(Planta.class))).thenReturn(5L);

        // Act
        Long resultado = repositorio.contarPorUsuario("global");

        // Assert
        assertEquals(5L, resultado);
        verify(mongoTemplate).count(any(Query.class), eq(Planta.class));
    }

    // Cubre el ELSE de contarPorUsuario()
    @Test
    void contarPorUsuario_DeberiaRetornarTotal_ParaUsuarioEspecifico() {
        // Arrange
        when(mongoTemplate.count(any(Query.class), eq(Planta.class))).thenReturn(3L);

        // Act
        Long resultado = repositorio.contarPorUsuario("user1");

        // Assert
        assertEquals(3L, resultado);
    }

    //Cubre el catch de contarPorUsuario() que LANZA excepción
    @Test
    void contarPorUsuario_DeberiaLanzarExcepcion_CuandoMongoFalla() {
        // Arrange
        when(mongoTemplate.count(any(Query.class), eq(Planta.class)))
                .thenThrow(new RuntimeException("Error de BD"));

        // Act & Assert - Debe lanzar IllegalStateException
        assertThrows(IllegalStateException.class,
                () -> repositorio.contarPorUsuario("user1"));
    }
}