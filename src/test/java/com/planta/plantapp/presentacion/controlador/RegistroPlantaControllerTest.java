package com.planta.plantapp.presentacion.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planta.plantapp.aplicacion.servicios.RegistroPlantaFacade;
import com.planta.plantapp.config.JwtService; // 👈 Importante para el error de la terminal
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegistroPlantaController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistroPlantaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistroPlantaFacade registroPlantaFacade;

    @MockBean
    private JwtService jwtService;

    @Test
    void actualizar_retorna200() throws Exception {
        // Arrange
        RegistroPlantaRequestDTO request = new RegistroPlantaRequestDTO();
        request.setApodo("Mi Planta");
        request.setEstado("SALUDABLE");

        RegistroPlantaResponseDTO response = new RegistroPlantaResponseDTO();
        response.setId("reg-1");
        response.setApodo("Mi Planta");
        response.setEstado("SALUDABLE");
        response.setFechaRegistro(new Date());

        when(registroPlantaFacade.actualizar(eq("reg-1"), any(RegistroPlantaRequestDTO.class)))
                .thenReturn(response);

        // Act & Assert
        // Cambié la ruta de /api/registro-plantas a /web/registros para que coincida con tu controlador
        mockMvc.perform(put("/web/registros/{id}", "reg-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("reg-1"))
                .andExpect(jsonPath("$.apodo").value("Mi Planta"));
    }
}