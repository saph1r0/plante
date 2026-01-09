package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistroPlantaFacadeTest {

    private IServicioRegistroPlanta servicioRegistro;
    private IServicioPlanta servicioPlanta;
    private RegistroPlantaFacade facade;

    @BeforeEach
    void setUp() {
        servicioRegistro = mock(IServicioRegistroPlanta.class);
        servicioPlanta = mock(IServicioPlanta.class);
        facade = new RegistroPlantaFacade(servicioRegistro, servicioPlanta);
    }

    @Test
    void actualizar_actualizaCamposCorrectamente() {
        // Arrange
        RegistroPlantaDocumento existente = new RegistroPlantaDocumento();
        existente.setId("1");
        existente.setApodo("Viejo");
        existente.setUbicacion("Sala");
        existente.setEstado(EstadoPlanta.SALUDABLE);
        existente.setNotas("Notas viejas");

        when(servicioRegistro.obtenerPorId("1")).thenReturn(existente);
        when(servicioRegistro.guardar(any())).thenAnswer(i -> i.getArgument(0));

        RegistroPlantaRequestDTO request = new RegistroPlantaRequestDTO();
        request.setApodo("Nuevo");
        request.setUbicacion("Cocina");
        request.setEstado("ENFERMA");
        request.setNotas("Notas nuevas");

        // Act
        RegistroPlantaResponseDTO response = facade.actualizar("1", request);

        // Assert
        assertEquals("Nuevo", response.getApodo());
        assertEquals("Cocina", response.getUbicacion());
        assertEquals("ENFERMA", response.getEstado());
        assertEquals("Notas nuevas", response.getNotas());

        verify(servicioRegistro).guardar(existente);
    }

    @Test
    void actualizar_lanzaExcepcion_siNoExisteRegistro() {
        // Arrange
        when(servicioRegistro.obtenerPorId("no-existe")).thenReturn(null);

        RegistroPlantaRequestDTO request = new RegistroPlantaRequestDTO();

        // Act + Assert
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> facade.actualizar("no-existe", request)
        );

        assertEquals("Registro de planta no encontrado", ex.getMessage());
        verify(servicioRegistro, never()).guardar(any());
    }
}
