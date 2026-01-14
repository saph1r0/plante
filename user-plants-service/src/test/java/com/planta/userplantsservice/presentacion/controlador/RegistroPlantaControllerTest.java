package com.planta.userplantsservice.presentacion.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planta.userplantsservice.aplicacion.servicios.RegistroPlantaFacade;
import com.planta.userplantsservice.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.userplantsservice.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.userplantsservice.infraestructura.seguridad.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @MockitoBean
    private RegistroPlantaFacade registroPlantaFacade;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void actualizar_retorna200() throws Exception {
        // Simular usuario autenticado manualmente
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user-1", null)
        );

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

        mockMvc.perform(put("/api/registros/{id}", "reg-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("reg-1"))
                .andExpect(jsonPath("$.apodo").value("Mi Planta"))
                .andExpect(jsonPath("$.estado").value("SALUDABLE"));
    }
}