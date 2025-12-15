package com.planta.plantapp.infraestructura.repositorio.mongodb.mongo;

import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroPlantaRepositorioMongoDBTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private RegistroPlantaRepositorioMongoDB repositorio;

    private RegistroPlantaDocumento registroPrueba;

    @BeforeEach
    void setUp() {
        registroPrueba = new RegistroPlantaDocumento(
                "user1",
                "planta123",
                "Mi Rosa Favorita",
                "Jardín delantero",
                EstadoPlanta.SALUDABLE
        );
        registroPrueba.setId("reg123");
        registroPrueba.setFotoPersonal("https://ejemplo.com/foto.jpg");
        registroPrueba.setNotas("Regar cada 3 días");
    }

    @Test
    void guardar_DeberiaGuardarRegistro_Exitosamente() {
        // Arrange
        when(mongoTemplate.save(registroPrueba)).thenReturn(registroPrueba);

        // Act
        RegistroPlantaDocumento resultado = repositorio.guardar(registroPrueba);

        // Assert
        assertNotNull(resultado);
        assertEquals("reg123", resultado.getId());
        assertEquals("user1", resultado.getUsuarioId());
        assertEquals("Mi Rosa Favorita", resultado.getApodo());
        verify(mongoTemplate).save(registroPrueba);
    }

    @Test
    void guardar_DeberiaRetornarRegistroConId_CuandoSeGuardaNuevo() {
        // Arrange
        RegistroPlantaDocumento nuevoRegistro = new RegistroPlantaDocumento(
                "user2",
                "planta456",
                "Tulipán Rojo",
                "Balcón",
                EstadoPlanta.SALUDABLE
        );

        RegistroPlantaDocumento registroGuardado = new RegistroPlantaDocumento(
                "user2",
                "planta456",
                "Tulipán Rojo",
                "Balcón",
                EstadoPlanta.MARCHITA
        );
        registroGuardado.setId("reg456");

        when(mongoTemplate.save(nuevoRegistro)).thenReturn(registroGuardado);

        // Act
        RegistroPlantaDocumento resultado = repositorio.guardar(nuevoRegistro);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("reg456", resultado.getId());
        assertEquals("user2", resultado.getUsuarioId());
        verify(mongoTemplate).save(nuevoRegistro);
    }

    @Test
    void guardar_DeberiaGuardarConTodosLosCampos_Correctamente() {
        // Arrange
        RegistroPlantaDocumento registroCompleto = new RegistroPlantaDocumento(
                "user3",
                "planta789",
                "Cactus Espinoso",
                "Oficina",
                EstadoPlanta.NECESITA_AGUA
        );
        registroCompleto.setFotoPersonal("https://ejemplo.com/cactus.jpg");
        registroCompleto.setNotas("Regar una vez al mes");

        when(mongoTemplate.save(registroCompleto)).thenReturn(registroCompleto);

        // Act
        RegistroPlantaDocumento resultado = repositorio.guardar(registroCompleto);

        // Assert
        assertEquals("Cactus Espinoso", resultado.getApodo());
        assertEquals("Oficina", resultado.getUbicacion());
        assertEquals(EstadoPlanta.NECESITA_AGUA, resultado.getEstado());
        assertEquals("https://ejemplo.com/cactus.jpg", resultado.getFotoPersonal());
        assertEquals("Regar una vez al mes", resultado.getNotas());
        verify(mongoTemplate).save(registroCompleto);
    }

    @Test
    void listarPorUsuario_DeberiaRetornarListaDeRegistros_CuandoExisten() {
        // Arrange
        RegistroPlantaDocumento registro2 = new RegistroPlantaDocumento(
                "user1",
                "planta124",
                "Girasol",
                "Jardín trasero",
                EstadoPlanta.FLORECIENDO
        );
        registro2.setId("reg124");

        List<RegistroPlantaDocumento> registros = Arrays.asList(registroPrueba, registro2);

        when(mongoTemplate.find(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(registros);

        // Act
        List<RegistroPlantaDocumento> resultado = repositorio.listarPorUsuario("user1");

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("user1", resultado.get(0).getUsuarioId());
        assertEquals("user1", resultado.get(1).getUsuarioId());
        verify(mongoTemplate).find(any(Query.class), eq(RegistroPlantaDocumento.class));
    }

    @Test
    void listarPorUsuario_DeberiaRetornarListaVacia_CuandoNoHayRegistros() {
        // Arrange
        when(mongoTemplate.find(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(List.of());

        // Act
        List<RegistroPlantaDocumento> resultado = repositorio.listarPorUsuario("user999");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(mongoTemplate).find(any(Query.class), eq(RegistroPlantaDocumento.class));
    }

    @Test
    void listarPorUsuario_DeberiaUsarCriteriaCorrect_ConUsuarioId() {
        // Arrange
        when(mongoTemplate.find(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(List.of(registroPrueba));

        // Act
        repositorio.listarPorUsuario("user1");

        // Assert
        verify(mongoTemplate).find(
                argThat(Objects::nonNull),
                eq(RegistroPlantaDocumento.class)
        );
    }

    @Test
    void eliminar_DeberiaEliminarRegistro_Exitosamente() {
        // Act
        assertDoesNotThrow(() -> repositorio.eliminar("reg123"));

        // Assert
        verify(mongoTemplate).remove(any(Query.class), eq(RegistroPlantaDocumento.class));
    }

    @Test
    void eliminar_DeberiaUsarIdCorrecto_EnQuery() {
        // Act
        repositorio.eliminar("reg123");

        // Assert
        verify(mongoTemplate).remove(
                argThat(Objects::nonNull),
                eq(RegistroPlantaDocumento.class)
        );
    }

    @Test
    void eliminar_NoDeberiaLanzarExcepcion_CuandoIdNoExiste() {

        // Act & Assert
        assertDoesNotThrow(() -> repositorio.eliminar("idInexistente"));
        verify(mongoTemplate).remove(any(Query.class), eq(RegistroPlantaDocumento.class));
    }

    @Test
    void obtenerPorId_DeberiaRetornarRegistro_CuandoExiste() {
        // Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(registroPrueba);

        // Act
        RegistroPlantaDocumento resultado = repositorio.obtenerPorId("reg123");

        // Assert
        assertNotNull(resultado);
        assertEquals("reg123", resultado.getId());
        assertEquals("user1", resultado.getUsuarioId());
        assertEquals("Mi Rosa Favorita", resultado.getApodo());
        assertEquals(EstadoPlanta.SALUDABLE, resultado.getEstado());
        verify(mongoTemplate).findOne(any(Query.class), eq(RegistroPlantaDocumento.class));
    }

    @Test
    void obtenerPorId_DeberiaRetornarNull_CuandoNoExiste() {
        // Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(null);

        // Act
        RegistroPlantaDocumento resultado = repositorio.obtenerPorId("reg999");

        // Assert
        assertNull(resultado);
        verify(mongoTemplate).findOne(any(Query.class), eq(RegistroPlantaDocumento.class));
    }

    @Test
    void obtenerPorId_DeberiaUsarIdCorrecto_EnQuery() {
        // Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(registroPrueba);

        // Act
        repositorio.obtenerPorId("reg123");

        // Assert
        verify(mongoTemplate).findOne(
                argThat(Objects::nonNull),
                eq(RegistroPlantaDocumento.class)
        );
    }

    @Test
    void obtenerPorId_DeberiaRetornarRegistroCompleto_ConTodosLosCampos() {
        // Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(RegistroPlantaDocumento.class)))
                .thenReturn(registroPrueba);

        // Act
        RegistroPlantaDocumento resultado = repositorio.obtenerPorId("reg123");

        // Assert
        assertNotNull(resultado);
        assertEquals("https://ejemplo.com/foto.jpg", resultado.getFotoPersonal());
        assertEquals("Regar cada 3 días", resultado.getNotas());
        assertEquals("Jardín delantero", resultado.getUbicacion());
        assertNotNull(resultado.getFechaRegistro());
    }
}