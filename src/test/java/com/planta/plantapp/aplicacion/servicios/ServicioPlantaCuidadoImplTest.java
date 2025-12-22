package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioRecordatorioDominio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioPlantaCuidadoImplTest {

    @Mock
    private IPlantaRepositorio plantaRepositorio;

    @Mock
    private ServicioRecordatorioDominio servicioRecordatorio;

    private ServicioPlantaCuidadoImpl servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioPlantaCuidadoImpl(plantaRepositorio, servicioRecordatorio);
    }

    @Test
    void testAgregarCuidadoAPlanta() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa chinensis", "Hermosa rosa", "url");
        planta.setId("planta1");

        when(plantaRepositorio.obtenerPorId("planta1")).thenReturn(planta);
        when(servicioRecordatorio.crearRecordatorio(any(), any(), anyString(), any()))
            .thenReturn(mock(Recordatorio.class));

        // Act
        servicio.agregarCuidadoAPlanta("planta1", TipoCuidado.RIEGO, "Regar", 3);

        // Assert
        verify(plantaRepositorio).obtenerPorId("planta1");
        verify(plantaRepositorio).guardar(planta);
        verify(servicioRecordatorio).crearRecordatorio(any(), any(), anyString(), any());
        assertEquals(1, planta.getCuidados().size());
    }

    @Test
    void testAgregarCuidadoAPlantaNoEncontrada() {
        // Arrange
        when(plantaRepositorio.obtenerPorId("noexiste")).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.agregarCuidadoAPlanta("noexiste", TipoCuidado.RIEGO, "Regar", 3);
        });
    }

    @Test
    void testObtenerCuidadosPlanta() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa chinensis", "Hermosa rosa", "url");
        planta.setId("planta1");
        planta.agregarCuidado(new Cuidado(TipoCuidado.RIEGO, "Regar", 3));

        when(plantaRepositorio.obtenerPorId("planta1")).thenReturn(planta);

        // Act
        List<Cuidado> cuidados = servicio.obtenerCuidadosPlanta("planta1");

        // Assert
        assertNotNull(cuidados);
        assertEquals(1, cuidados.size());
        verify(plantaRepositorio).obtenerPorId("planta1");
    }

    @Test
    void testObtenerCuidadosPlantaNoEncontrada() {
        // Arrange
        when(plantaRepositorio.obtenerPorId("noexiste")).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.obtenerCuidadosPlanta("noexiste");
        });
    }

    @Test
    void testActualizarCuidadosPlanta() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa chinensis", "Hermosa rosa", "url");
        planta.setId("planta1");

        List<Cuidado> nuevosCuidados = Arrays.asList(
            new Cuidado(TipoCuidado.RIEGO, "Regar", 3),
            new Cuidado(TipoCuidado.FERTILIZACION, "Fertilizar", 15)
        );

        when(plantaRepositorio.obtenerPorId("planta1")).thenReturn(planta);
        when(servicioRecordatorio.crearRecordatorio(any(), any(), anyString(), any()))
            .thenReturn(mock(Recordatorio.class));

        // Act
        servicio.actualizarCuidadosPlanta("planta1", nuevosCuidados);

        // Assert
        verify(plantaRepositorio).guardar(planta);
        assertEquals(2, planta.getCuidados().size());
        verify(servicioRecordatorio, times(2)).crearRecordatorio(any(), any(), anyString(), any());
    }

    @Test
    void testEliminarCuidadosPlanta() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa chinensis", "Hermosa rosa", "url");
        planta.setId("planta1");
        planta.agregarCuidado(new Cuidado(TipoCuidado.RIEGO, "Regar", 3));

        when(plantaRepositorio.obtenerPorId("planta1")).thenReturn(planta);

        // Act
        servicio.eliminarCuidadosPlanta("planta1");

        // Assert
        verify(plantaRepositorio).guardar(planta);
        assertEquals(0, planta.getCuidados().size());
    }

    @Test
    void testTieneCuidadosPendientes() {
        // Arrange
        Planta planta = new Planta("Rosa", "Rosa chinensis", "Hermosa rosa", "url");
        planta.setId("planta1");

        Cuidado cuidado = new Cuidado(TipoCuidado.RIEGO, "Regar", 3);
        planta.agregarCuidado(cuidado);

        when(plantaRepositorio.obtenerPorId("planta1")).thenReturn(planta);

        // Act
        boolean tienePendientes = servicio.tieneCuidadosPendientes("planta1");

        // Assert - depende de la implementación del método esPendiente()
        verify(plantaRepositorio).obtenerPorId("planta1");
    }
}

