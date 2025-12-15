package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import com.planta.plantapp.infraestructura.repositorio.mongodb.mongo.RegistroPlantaRepositorioMongoDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioRegistroPlantaImplTest {

    @Mock
    private RegistroPlantaRepositorioMongoDB repositorioMock;

    @InjectMocks
    private ServicioRegistroPlantaImpl servicio;

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
        registroPrueba.setFechaRegistro(new Date());
    }

    @Test
    void guardar_DeberiaGuardarRegistro_Exitosamente() {
        // Arrange
        when(repositorioMock.guardar(registroPrueba)).thenReturn(registroPrueba);

        // Act
        RegistroPlantaDocumento resultado = servicio.guardar(registroPrueba);

        // Assert
        assertNotNull(resultado);
        assertEquals("reg123", resultado.getId());
        assertEquals("user1", resultado.getUsuarioId());
        assertEquals("Mi Rosa Favorita", resultado.getApodo());
        verify(repositorioMock).guardar(registroPrueba);
    }

    @Test
    void guardar_DeberiaRetornarRegistroConId_CuandoEsNuevo() {
        // Arrange
        RegistroPlantaDocumento nuevoRegistro = new RegistroPlantaDocumento(
                "user2",
                "planta456",
                "Tulipán Amarillo",
                "Balcón",
                EstadoPlanta.CRECIENDO
        );

        RegistroPlantaDocumento registroGuardado = new RegistroPlantaDocumento(
                "user2",
                "planta456",
                "Tulipán Amarillo",
                "Balcón",
                EstadoPlanta.CRECIENDO
        );
        registroGuardado.setId("reg456");

        when(repositorioMock.guardar(nuevoRegistro)).thenReturn(registroGuardado);

        // Act
        RegistroPlantaDocumento resultado = servicio.guardar(nuevoRegistro);

        // Assert
        assertNotNull(resultado);
        assertEquals("reg456", resultado.getId());
        assertEquals("user2", resultado.getUsuarioId());
        verify(repositorioMock).guardar(nuevoRegistro);
    }

    @Test
    void guardar_DeberiaPropagarseLlamada_AlRepositorio() {
        // Arrange
        when(repositorioMock.guardar(any(RegistroPlantaDocumento.class)))
                .thenReturn(registroPrueba);

        // Act
        servicio.guardar(registroPrueba);

        // Assert
        verify(repositorioMock, times(1)).guardar(registroPrueba);
    }

    @Test
    void listarPorUsuario_DeberiaRetornarListaDeRegistros_CuandoExisten() {
        // Arrange
        RegistroPlantaDocumento registro2 = new RegistroPlantaDocumento(
                "user1",
                "planta124",
                "Girasol Grande",
                "Jardín trasero",
                EstadoPlanta.FLORECIENDO
        );
        registro2.setId("reg124");

        List<RegistroPlantaDocumento> registros = Arrays.asList(registroPrueba, registro2);
        when(repositorioMock.listarPorUsuario("user1")).thenReturn(registros);

        // Act
        List<RegistroPlantaDocumento> resultado = servicio.listarPorUsuario("user1");

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("user1", resultado.get(0).getUsuarioId());
        assertEquals("user1", resultado.get(1).getUsuarioId());
        verify(repositorioMock).listarPorUsuario("user1");
    }

    @Test
    void listarPorUsuario_DeberiaRetornarListaVacia_CuandoNoHayRegistros() {
        // Arrange
        when(repositorioMock.listarPorUsuario("user999")).thenReturn(List.of());

        // Act
        List<RegistroPlantaDocumento> resultado = servicio.listarPorUsuario("user999");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repositorioMock).listarPorUsuario("user999");
    }

    @Test
    void listarPorUsuario_DeberiaPropagarseLlamada_AlRepositorio() {
        // Arrange
        when(repositorioMock.listarPorUsuario("user1")).thenReturn(List.of(registroPrueba));

        // Act
        servicio.listarPorUsuario("user1");

        // Assert
        verify(repositorioMock, times(1)).listarPorUsuario("user1");
    }

    @Test
    void listarPorUsuario_DeberiaRetornarTodosLosCampos_Correctamente() {
        // Arrange
        List<RegistroPlantaDocumento> registros = List.of(registroPrueba);
        when(repositorioMock.listarPorUsuario("user1")).thenReturn(registros);

        // Act
        List<RegistroPlantaDocumento> resultado = servicio.listarPorUsuario("user1");

        // Assert
        RegistroPlantaDocumento registro = resultado.get(0);
        assertEquals("Mi Rosa Favorita", registro.getApodo());
        assertEquals("Jardín delantero", registro.getUbicacion());
        assertEquals(EstadoPlanta.SALUDABLE, registro.getEstado());
        assertEquals("https://ejemplo.com/foto.jpg", registro.getFotoPersonal());
        assertEquals("Regar cada 3 días", registro.getNotas());
    }

    @Test
    void eliminar_DeberiaEliminarRegistro_Exitosamente() {
        // Arrange
        doNothing().when(repositorioMock).eliminar("reg123");

        // Act & Assert
        assertDoesNotThrow(() -> servicio.eliminar("reg123"));
        verify(repositorioMock).eliminar("reg123");
    }

    @Test
    void eliminar_DeberiaPropagarseLlamada_AlRepositorio() {
        // Arrange
        doNothing().when(repositorioMock).eliminar("reg123");

        // Act
        servicio.eliminar("reg123");

        // Assert
        verify(repositorioMock, times(1)).eliminar("reg123");
    }

    @Test
    void eliminar_NoDeberiaLanzarExcepcion_CuandoIdNoExiste() {
        // Arrange
        doNothing().when(repositorioMock).eliminar("idInexistente");

        // Act & Assert
        assertDoesNotThrow(() -> servicio.eliminar("idInexistente"));
        verify(repositorioMock).eliminar("idInexistente");
    }

    @Test
    void eliminar_DeberiaUsarIdCorrecto() {
        // Arrange
        String idPrueba = "reg999";
        doNothing().when(repositorioMock).eliminar(idPrueba);

        // Act
        servicio.eliminar(idPrueba);

        // Assert
        verify(repositorioMock).eliminar(idPrueba);
    }

    @Test
    void obtenerPorId_DeberiaRetornarRegistro_CuandoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("reg123")).thenReturn(registroPrueba);

        // Act
        RegistroPlantaDocumento resultado = servicio.obtenerPorId("reg123");

        // Assert
        assertNotNull(resultado);
        assertEquals("reg123", resultado.getId());
        assertEquals("user1", resultado.getUsuarioId());
        assertEquals("Mi Rosa Favorita", resultado.getApodo());
        verify(repositorioMock).obtenerPorId("reg123");
    }

    @Test
    void obtenerPorId_DeberiaRetornarNull_CuandoNoExiste() {
        // Arrange
        when(repositorioMock.obtenerPorId("reg999")).thenReturn(null);

        // Act
        RegistroPlantaDocumento resultado = servicio.obtenerPorId("reg999");

        // Assert
        assertNull(resultado);
        verify(repositorioMock).obtenerPorId("reg999");
    }

    @Test
    void obtenerPorId_DeberiaPropagarseLlamada_AlRepositorio() {
        // Arrange
        when(repositorioMock.obtenerPorId("reg123")).thenReturn(registroPrueba);

        // Act
        servicio.obtenerPorId("reg123");

        // Assert
        verify(repositorioMock, times(1)).obtenerPorId("reg123");
    }

    @Test
    void obtenerPorId_DeberiaRetornarTodosLosCampos_Correctamente() {
        // Arrange
        when(repositorioMock.obtenerPorId("reg123")).thenReturn(registroPrueba);

        // Act
        RegistroPlantaDocumento resultado = servicio.obtenerPorId("reg123");

        // Assert
        assertNotNull(resultado);
        assertEquals("planta123", resultado.getPlantaId());
        assertEquals(EstadoPlanta.SALUDABLE, resultado.getEstado());
        assertEquals("Jardín delantero", resultado.getUbicacion());
        assertEquals("https://ejemplo.com/foto.jpg", resultado.getFotoPersonal());
        assertEquals("Regar cada 3 días", resultado.getNotas());
        assertNotNull(resultado.getFechaRegistro());
    }
}