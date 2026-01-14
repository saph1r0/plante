package com.planta.plantapp.presentacion.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PlantaControllerTest {

    private MockMvc mockMvc;
    private IServicioPlanta servicioPlanta;
    private PlantaController controller;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        servicioPlanta = mock(IServicioPlanta.class);
        controller = new PlantaController(servicioPlanta);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    // ========================================
    // GET /web/plantas
    // ========================================
    @Test
    void obtenerTodas_retornaListaDePlantas() throws Exception {
        Planta planta = new Planta("Rosa", "Rosa rubiginosa", "Flor roja hermosa", "https://rosa.com/imagen.jpg");
        planta.setId("1");
        when(servicioPlanta.obtenerTodas()).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nombreComun").value("Rosa"));
    }

    @Test
    void obtenerTodas_cuandoNoHayPlantas_retornaListaVacia() throws Exception {
        when(servicioPlanta.obtenerTodas()).thenReturn(List.of());

        mockMvc.perform(get("/web/plantas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void obtenerTodas_cuandoHayError_retorna500() throws Exception {
        when(servicioPlanta.obtenerTodas()).thenThrow(new RuntimeException("Error de BD"));

        mockMvc.perform(get("/web/plantas"))
                .andExpect(status().isInternalServerError());
    }

    // ========================================
    // GET /web/plantas/{id}
    // ========================================
    @Test
    void obtenerPorId_cuandoExiste_retorna200() throws Exception {
        Planta planta = new Planta("Tulipán", "Tulipa gesneriana", "Flor colorida", "https://tulipan.com/img.jpg");
        planta.setId("123");

        when(servicioPlanta.obtenerPorId("123")).thenReturn(Optional.of(planta));

        mockMvc.perform(get("/web/plantas/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.nombreComun").value("Tulipán"));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_retorna404() throws Exception {
        when(servicioPlanta.obtenerPorId("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/web/plantas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPorId_cuandoHayError_retorna500() throws Exception {
        when(servicioPlanta.obtenerPorId(anyString())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/web/plantas/123"))
                .andExpect(status().isInternalServerError());
    }

    // ========================================
    // POST /web/plantas
    // ========================================
    @Test
    void guardar_plantaCorrecta_retorna201() throws Exception {
        PlantaRequestDTO requestDTO = new PlantaRequestDTO();
        requestDTO.setNombreComun("Orquídea");
        requestDTO.setNombreCientifico("Orchidaceae");
        requestDTO.setDescripcion("Planta tropical elegante");
        requestDTO.setImagenURL("https://orquidea.com/foto.jpg");

        Planta plantaGuardada = new Planta("Orquídea", "Orchidaceae", "Planta tropical elegante", "https://orquidea.com/foto.jpg");
        plantaGuardada.setId("10");

        when(servicioPlanta.guardar(any(Planta.class))).thenReturn(plantaGuardada);

        mockMvc.perform(post("/web/plantas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("10"))
                .andExpect(jsonPath("$.nombreComun").value("Orquídea"));
    }

    @Test
    void guardar_cuandoHayError_retorna500() throws Exception {
        PlantaRequestDTO requestDTO = new PlantaRequestDTO();
        requestDTO.setNombreComun("Cactus");

        when(servicioPlanta.guardar(any(Planta.class))).thenThrow(new RuntimeException("Error al guardar"));

        mockMvc.perform(post("/web/plantas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }

    // ========================================
    // DELETE /web/plantas/{id}
    // ========================================
    @Test
    void eliminar_plantaExistente_retorna200() throws Exception {
        doNothing().when(servicioPlanta).eliminar("1");

        mockMvc.perform(delete("/web/plantas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Planta eliminada exitosamente"));
    }

    @Test
    void eliminar_cuandoHayError_retorna500() throws Exception {
        doThrow(new RuntimeException("Error")).when(servicioPlanta).eliminar("999");

        mockMvc.perform(delete("/web/plantas/999"))
                .andExpect(status().isInternalServerError());
    }

    // ========================================
    // GET /web/plantas/buscar
    // ========================================
    @Test
    void buscarPorTipo_retornaPlantas() throws Exception {
        Planta planta = new Planta("Cactus", "Cactaceae", "Planta desértica", "url");
        planta.setId("5");

        when(servicioPlanta.buscarPorNombre("suculenta", "global")).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas/buscar").param("tipo", "suculenta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreComun").value("Cactus"));
    }

    // ========================================
    // GET /web/plantas/test
    // ========================================
    @Test
    void test_conexionExitosa_retornaMapaDeEstado() throws Exception {
        Planta planta = new Planta("Rosa", "Rosa sp.", "Bella", "url");
        planta.setId("1");

        when(servicioPlanta.obtenerTodas()).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Conexión MongoDB exitosa"))
                .andExpect(jsonPath("$.totalPlantas").value(1));
    }

    @Test
    void test_cuandoNoHayPlantas_retornaInfoSinPlantas() throws Exception {
        when(servicioPlanta.obtenerTodas()).thenReturn(List.of());

        mockMvc.perform(get("/web/plantas/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlantas").value(0))
                .andExpect(jsonPath("$.info").value("No hay plantas en la base de datos"));
    }

    // ========================================
    // GET /web/plantas/usuario/{usuarioId}/mis-plantas
    // ========================================
    @Test
    void obtenerMisPlantas_retornaPlantasDelUsuario() throws Exception {
        Planta planta = new Planta("Cactus", "Cactaceae", "Espinoso", "url");
        planta.setId("mp-1");

        when(servicioPlanta.buscarPorUsuario(123L)).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas/usuario/123/mis-plantas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("mp-1"))
                .andExpect(jsonPath("$[0].nombreComun").value("Cactus"));
    }

    // ========================================
    // GET /web/plantas/estadisticas/{usuarioId}
    // ========================================
    @Test
    void obtenerEstadisticas_calculaCorrectamente() throws Exception {
        Planta p1 = new Planta("Rosa", "Rosa sp.", "Bonita", "url");
        p1.setEstado("SALUDABLE");

        Planta p2 = new Planta("Tulipán", "Tulipa", "Colorido", "url");
        p2.setEstado("NECESITA_ATENCION");

        when(servicioPlanta.buscarPorUsuario(456L)).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/web/plantas/estadisticas/456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlantas").value(2))
                .andExpect(jsonPath("$.plantasSaludables").value(1))
                .andExpect(jsonPath("$.plantasNecesitanAtencion").value(1));
    }

    // ========================================
    // GET /web/plantas/health
    // ========================================
    @Test
    void healthCheck_retornaStatusUp() throws Exception {
        mockMvc.perform(get("/web/plantas/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("PlantaController/Catalogo"));
    }
}
