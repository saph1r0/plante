package com.planta.plantapp.presentacion.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planta.plantapp.aplicacion.servicios.RegistroPlantaFacade;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistroPlantaControllerTest {

    private MockMvc mockMvc;
    private RegistroPlantaFacade registroPlantaFacade;
    private RegistroPlantaController controller;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Mock del facade
        registroPlantaFacade = mock(RegistroPlantaFacade.class);

        // Instanciamos el controller con el mock
        controller = new RegistroPlantaController(registroPlantaFacade);

        // Configuramos MockMvc standalone
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Instanciamos ObjectMapper
        objectMapper = new ObjectMapper();
    }

    @Test
    void actualizar_retorna200() throws Exception {
        // 🔹 Simular usuario autenticado (JWT)
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user-1", null)
        );

        // DTO de request
        RegistroPlantaRequestDTO request = new RegistroPlantaRequestDTO();
        request.setApodo("Mi Planta");
        request.setEstado("SALUDABLE");

        // DTO de response esperado
        RegistroPlantaResponseDTO response = new RegistroPlantaResponseDTO();
        response.setId("reg-1");
        response.setApodo("Mi Planta");
        response.setEstado("SALUDABLE");
        response.setFechaRegistro(new Date());

        // Configurar comportamiento del mock
        when(registroPlantaFacade.actualizar(eq("reg-1"), any(RegistroPlantaRequestDTO.class)))
                .thenReturn(response);

        // Realizar petición PUT y verificar resultados
        mockMvc.perform(put("/api/registros/{id}", "reg-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("reg-1"))
                .andExpect(jsonPath("$.apodo").value("Mi Planta"))
                .andExpect(jsonPath("$.estado").value("SALUDABLE"));
    }
}
