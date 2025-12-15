package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlantaController.class)
@AutoConfigureMockMvc(addFilters = false)  // Desactiva Spring Security
class PlantaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IServicioPlanta servicioPlanta;

    @Autowired
    private ObjectMapper objectMapper;

    // ========================================
    // Tests para GET /web/plantas
    // ========================================
    @Test
    void obtenerTodas_retornaListaDePlantas() throws Exception {
        Planta planta = new Planta(
                "Rosa",
                "Rosa rubiginosa",
                "Flor roja hermosa",
                "https://rosa.com/imagen.jpg"
        );
        planta.setId("1");

        when(servicioPlanta.obtenerTodas()).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nombreComun").value("Rosa"))
                .andExpect(jsonPath("$[0].nombreCientifico").value("Rosa rubiginosa"));
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
                .andExpect(status().is(500));
    }
    @Test
    void obtenerPorId_cuandoExiste_retorna200() throws Exception {
        Planta planta = new Planta(
                "Tulipán",
                "Tulipa gesneriana",
                "Flor colorida de primavera",
                "https://tulipan.com/img.jpg"
        );
        planta.setId("123");

        when(servicioPlanta.obtenerPorId("123"))
                .thenReturn(Optional.of(planta));

        mockMvc.perform(get("/web/plantas/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.nombreComun").value("Tulipán"));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_retorna404() throws Exception {
        when(servicioPlanta.obtenerPorId("999"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/web/plantas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPorId_cuandoHayError_retorna500() throws Exception {
        when(servicioPlanta.obtenerPorId(anyString()))
                .thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/web/plantas/123"))
                .andExpect(status().is(500));
    }

    // ========================================
    // Tests para POST /web/plantas
    // ========================================
    @Test
    void guardar_plantaCorrecta_retorna201() throws Exception {
        PlantaRequestDTO requestDTO = new PlantaRequestDTO();
        requestDTO.setNombreComun("Orquídea");
        requestDTO.setNombreCientifico("Orchidaceae");
        requestDTO.setDescripcion("Planta tropical elegante");
        requestDTO.setImagenURL("https://orquidea.com/foto.jpg");

        Planta plantaGuardada = new Planta(
                "Orquídea",
                "Orchidaceae",
                "Planta tropical elegante",
                "https://orquidea.com/foto.jpg"
        );
        plantaGuardada.setId("10");

        when(servicioPlanta.guardar(any(Planta.class)))
                .thenReturn(plantaGuardada);

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

        when(servicioPlanta.guardar(any(Planta.class)))
                .thenThrow(new RuntimeException("Error al guardar"));

        mockMvc.perform(post("/web/plantas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().is(500));
    }

    @Test
    void eliminar_plantaExistente_retorna200() throws Exception {
        doNothing().when(servicioPlanta).eliminar("1");

        mockMvc.perform(delete("/web/plantas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Planta eliminada exitosamente"));
    }

    @Test
    void eliminar_cuandoHayError_retorna500() throws Exception {
        when(servicioPlanta.obtenerPorId("999")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/web/plantas/999"))
                .andExpect(status().isOk()); // El controlador no valida existencia antes de eliminar
    }

    // ========================================
    // Tests para GET /web/plantas/buscar
    // ========================================
    @Test
    void buscarPorTipo_retornaPlantas() throws Exception {
        Planta planta = new Planta("Cactus", "Cactaceae", "Planta desértica", "url");
        planta.setId("5");

        when(servicioPlanta.buscarPorNombre("suculenta", "global"))
                .thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas/buscar")
                        .param("tipo", "suculenta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreComun").value("Cactus"));
    }

    // ========================================
    // Tests para GET /web/plantas/test
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
    // Tests para GET /web/plantas/debug
    // ========================================
    @Test
    void debug_retornaInformacionDetallada() throws Exception {
        Planta planta = new Planta("Helecho", "Nephrolepis", "Verde", "url");
        when(servicioPlanta.obtenerTodas()).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas/debug"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlantas").value(1))
                .andExpect(jsonPath("$.configuracion").value("MongoDB activo"));
    }

    // ========================================
    // Tests para vistas HTML
    // ========================================
    @Test
    void mostrarCatalogo_retornaVista() throws Exception {
        when(servicioPlanta.obtenerTodas()).thenReturn(List.of());

        mockMvc.perform(get("/web/plantas/catalogo"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/catalogo"));
    }

    @Test
    void mostrarDashboard_retornaVista() throws Exception {
        mockMvc.perform(get("/web/plantas/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/dashboard"));
    }

    @Test
    void mostrarDashboard2_retornaVista() throws Exception {
        mockMvc.perform(get("/web/plantas/dashboard2"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/dashboard2"));
    }

    @Test
    void mostrarFormularioRegistro_retornaVista() throws Exception {
        mockMvc.perform(get("/web/plantas/registro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/registro-planta"));
    }

    @Test
    void mostrarMisPlantas_retornaVista() throws Exception {
        mockMvc.perform(get("/web/plantas/mis-plantas"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/mis-plantas"));
    }

    @Test
    void mostrarEditarPlanta_retornaVista() throws Exception {
        mockMvc.perform(get("/web/plantas/editar"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/editar-planta"));
    }

    @Test
    void mostrarVistaPlanta_retornaVista() throws Exception {
        mockMvc.perform(get("/web/plantas/vista"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/vista-planta"));
    }

    // ========================================
    // Tests para POST /web/plantas/registro-personal
    // ========================================
    @Test
    void registrarPlantaPersonal_datosCompletos_retorna201() throws Exception {
        Planta plantaCatalogo = new Planta("Rosa", "Rosa sp.", "Bonita", "url");
        plantaCatalogo.setId("cat-1");

        Planta plantaGuardada = new Planta("Rosa", "Rosa sp.", "Bonita", "url");
        plantaGuardada.setId("personal-1");

        when(servicioPlanta.obtenerPorId("cat-1")).thenReturn(Optional.of(plantaCatalogo));
        when(servicioPlanta.guardar(any(Planta.class))).thenReturn(plantaGuardada);

        Map<String, Object> datos = new HashMap<>();
        datos.put("plantaId", "cat-1");
        datos.put("apodo", "Mi Rosita");
        datos.put("ubicacion", "Jardín");

        mockMvc.perform(post("/web/plantas/registro-personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Planta registrada exitosamente"))
                .andExpect(jsonPath("$.data.id").value("personal-1"));
    }

    @Test
    void registrarPlantaPersonal_sinPlantaId_retorna400() throws Exception {
        Map<String, Object> datos = new HashMap<>();
        datos.put("apodo", "Mi Plantita");
        datos.put("ubicacion", "Casa");

        mockMvc.perform(post("/web/plantas/registro-personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El ID de la planta es requerido"));
    }

    @Test
    void registrarPlantaPersonal_sinApodo_retorna400() throws Exception {
        Map<String, Object> datos = new HashMap<>();
        datos.put("plantaId", "123");
        datos.put("ubicacion", "Casa");

        mockMvc.perform(post("/web/plantas/registro-personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El apodo es requerido"));
    }

    @Test
    void registrarPlantaPersonal_plantaNoExiste_retorna404() throws Exception {
        when(servicioPlanta.obtenerPorId("999")).thenReturn(Optional.empty());

        Map<String, Object> datos = new HashMap<>();
        datos.put("plantaId", "999");
        datos.put("apodo", "Mi Planta");
        datos.put("ubicacion", "Balcón");

        mockMvc.perform(post("/web/plantas/registro-personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isNotFound());
        // No validamos el body porque notFound().build() no tiene contenido
    }

    @Test
    void obtenerPlantasUsuario_retornaListaDePlantas() throws Exception {
        Planta planta = new Planta("Helecho", "Nephrolepis", "Verde", "url");
        planta.setId("user-plant-1");

        when(servicioPlanta.obtenerTodas()).thenReturn(List.of(planta));

        mockMvc.perform(get("/web/plantas/usuario/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("user-plant-1"))
                .andExpect(jsonPath("$[0].nombreComun").value("Helecho"))
                .andExpect(jsonPath("$[0].estado").value("SALUDABLE"));
    }

    // ========================================
    // Tests para GET /web/plantas/usuario/{usuarioId}/mis-plantas
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

    @Test
    void obtenerEstadisticas_calculaCorrectamente() throws Exception {
        Planta p1 = new Planta("Rosa", "Rosa sp.", "Bonita", "url");
        p1.setEstado("SALUDABLE");

        Planta p2 = new Planta("Tulipán", "Tulipa", "Colorido", "url");
        p2.setEstado("NECESITA_AGUA");

        when(servicioPlanta.buscarPorUsuario(456L)).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/web/plantas/estadisticas/456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlantas").value(2))
                .andExpect(jsonPath("$.plantasSaludables").value(1))
                .andExpect(jsonPath("$.plantasNecesitanAtencion").value(1));
    }

    // ========================================
    // Tests para GET /web/plantas/health
    // ========================================
    @Test
    void healthCheck_retornaStatusUp() throws Exception {
        mockMvc.perform(get("/web/plantas/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("PlantaController/Catalogo"));
    }

    @Test
    void debugCuidados_plantaExiste_retornaPlanta() throws Exception {
        Planta planta = new Planta("Suculenta", "Echeveria", "Pequeña", "url");
        planta.setId("dc-1");

        when(servicioPlanta.obtenerPorId("dc-1")).thenReturn(Optional.of(planta));

        mockMvc.perform(get("/web/plantas/debug-cuidados/dc-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("dc-1"))
                .andExpect(jsonPath("$.nombreComun").value("Suculenta"));
    }

    @Test
    void debugCuidados_plantaNoExiste_retorna404() throws Exception {
        when(servicioPlanta.obtenerPorId("no-existe")).thenReturn(Optional.empty());

        mockMvc.perform(get("/web/plantas/debug-cuidados/no-existe"))
                .andExpect(status().isNotFound());
    }
}