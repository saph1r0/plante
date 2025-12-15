package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroPlantaController.class)
@AutoConfigureMockMvc(addFilters = false)  // Desactiva Spring Security
class RegistroPlantaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IServicioRegistroPlanta servicio;

    @MockitoBean
    private IServicioPlanta servicioPlanta;

    @Autowired
    private ObjectMapper objectMapper;

    // ========================================
    // Tests para POST /web/registros
    // ========================================
    @Test
    void registrar_datosCompletos_retorna201() throws Exception {
        Map<String, Object> datos = new HashMap<>();
        datos.put("usuarioId", "user123");
        datos.put("plantaId", "plant456");
        datos.put("apodo", "Mi Rosita");
        datos.put("ubicacion", "Jardín");
        datos.put("estado", "SALUDABLE");
        datos.put("fotoPersonal", "https://foto.jpg");

        RegistroPlantaDocumento registroGuardado = new RegistroPlantaDocumento(
                "user123", "plant456", "Mi Rosita", "Jardín", EstadoPlanta.SALUDABLE
        );
        registroGuardado.setId("reg-001");
        registroGuardado.setFotoPersonal("https://foto.jpg");

        when(servicio.guardar(any(RegistroPlantaDocumento.class)))
                .thenReturn(registroGuardado);

        mockMvc.perform(post("/web/registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("reg-001"))
                .andExpect(jsonPath("$.apodo").value("Mi Rosita"))
                .andExpect(jsonPath("$.usuarioId").value("user123"));
    }

    @Test
    void registrar_sinFotoPersonal_retorna201() throws Exception {
        Map<String, Object> datos = new HashMap<>();
        datos.put("usuarioId", "user456");
        datos.put("plantaId", "plant789");
        datos.put("apodo", "Cactus");
        datos.put("ubicacion", "Balcón");
        datos.put("estado", "NECESITA_AGUA");

        RegistroPlantaDocumento registroGuardado = new RegistroPlantaDocumento(
                "user456", "plant789", "Cactus", "Balcón", EstadoPlanta.NECESITA_AGUA
        );
        registroGuardado.setId("reg-002");

        when(servicio.guardar(any(RegistroPlantaDocumento.class)))
                .thenReturn(registroGuardado);

        mockMvc.perform(post("/web/registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("reg-002"));
    }

    @Test
    void registrar_sinEstado_registraCorrectamente() throws Exception {
        Map<String, Object> datos = new HashMap<>();
        datos.put("usuarioId", "user789");
        datos.put("plantaId", "plant111");
        datos.put("apodo", "Helecho");
        datos.put("ubicacion", "Sala");

        RegistroPlantaDocumento registroGuardado = new RegistroPlantaDocumento(
                "user789", "plant111", "Helecho", "Sala", null
        );
        registroGuardado.setId("reg-003");

        when(servicio.guardar(any(RegistroPlantaDocumento.class)))
                .thenReturn(registroGuardado);

        mockMvc.perform(post("/web/registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isCreated());
    }

    @Test
    void registrar_cuandoHayError_retorna500() throws Exception {
        Map<String, Object> datos = new HashMap<>();
        datos.put("usuarioId", "user000");
        datos.put("plantaId", "plant000");
        datos.put("apodo", "Error Plant");

        when(servicio.guardar(any(RegistroPlantaDocumento.class)))
                .thenThrow(new RuntimeException("Error de base de datos"));

        mockMvc.perform(post("/web/registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().is(500));
    }

    @Test
    void obtenerPorId_cuandoExiste_retorna200ConPlanta() throws Exception {
        RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                "user123", "plant456", "Mi Rosa", "Jardín", EstadoPlanta.SALUDABLE
        );
        registro.setId("reg-100");
        registro.setFotoPersonal("foto.jpg");
        registro.setNotas("Hermosa planta");

        Planta planta = new Planta(
                "Rosa", "Rosa rubiginosa", "Flor roja", "https://rosa.jpg"
        );
        planta.setId("plant456");

        when(servicio.obtenerPorId("reg-100")).thenReturn(registro);
        when(servicioPlanta.obtenerPorId("plant456")).thenReturn(Optional.of(planta));

        mockMvc.perform(get("/web/registros/reg-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("reg-100"))
                .andExpect(jsonPath("$.apodo").value("Mi Rosa"))
                .andExpect(jsonPath("$.ubicacion").value("Jardín"))
                .andExpect(jsonPath("$.planta.nombreComun").value("Rosa"))
                .andExpect(jsonPath("$.planta.id").value("plant456"));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_retorna404() throws Exception {
        when(servicio.obtenerPorId("reg-999")).thenReturn(null);

        mockMvc.perform(get("/web/registros/reg-999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPorId_cuandoPlantaNoExisteEnCatalogo_retornaMapaVacio() throws Exception {
        RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                "user123", "plant-inexistente", "Mi Planta", "Casa", EstadoPlanta.SALUDABLE
        );
        registro.setId("reg-200");

        when(servicio.obtenerPorId("reg-200")).thenReturn(registro);
        when(servicioPlanta.obtenerPorId("plant-inexistente")).thenReturn(Optional.empty());

        mockMvc.perform(get("/web/registros/reg-200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("reg-200"))
                .andExpect(jsonPath("$.planta").isEmpty());
    }

    @Test
    void listarPorUsuario_retornaListaEnriquecida() throws Exception {
        RegistroPlantaDocumento reg1 = new RegistroPlantaDocumento(
                "user123", "plant1", "Rosa", "Jardín", EstadoPlanta.SALUDABLE
        );
        reg1.setId("reg-001");

        RegistroPlantaDocumento reg2 = new RegistroPlantaDocumento(
                "user123", "plant2", "Tulipán", "Balcón", EstadoPlanta.NECESITA_AGUA
        );
        reg2.setId("reg-002");

        Planta planta1 = new Planta("Rosa", "Rosa sp.", "Bonita", "url1");
        planta1.setId("plant1");
        Planta planta2 = new Planta("Tulipán", "Tulipa", "Colorido", "url2");
        planta2.setId("plant2");

        when(servicio.listarPorUsuario("user123")).thenReturn(List.of(reg1, reg2));
        when(servicioPlanta.obtenerPorId("plant1")).thenReturn(Optional.of(planta1));
        when(servicioPlanta.obtenerPorId("plant2")).thenReturn(Optional.of(planta2));

        mockMvc.perform(get("/web/registros/usuario/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("reg-001"))
                .andExpect(jsonPath("$[0].apodo").value("Rosa"))
                .andExpect(jsonPath("$[0].estado").value("SALUDABLE"))
                .andExpect(jsonPath("$[0].planta.nombreComun").value("Rosa"))
                .andExpect(jsonPath("$[1].id").value("reg-002"))
                .andExpect(jsonPath("$[1].apodo").value("Tulipán"))
                .andExpect(jsonPath("$[1].estado").value("NECESITA_AGUA"));
    }

    @Test
    void listarPorUsuario_cuandoNoHayRegistros_retornaListaVacia() throws Exception {
        when(servicio.listarPorUsuario("user999")).thenReturn(List.of());

        mockMvc.perform(get("/web/registros/usuario/user999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void listarPorUsuario_cuandoHayError_retorna500() throws Exception {
        when(servicio.listarPorUsuario("user000"))
                .thenThrow(new RuntimeException("Error de BD"));

        mockMvc.perform(get("/web/registros/usuario/user000"))
                .andExpect(status().is(500));
    }

    @Test
    void obtenerEstadisticas_calculaCorrectamente() throws Exception {
        RegistroPlantaDocumento reg1 = new RegistroPlantaDocumento(
                "user123", "p1", "Rosa", "Jardín", EstadoPlanta.SALUDABLE
        );
        RegistroPlantaDocumento reg2 = new RegistroPlantaDocumento(
                "user123", "p2", "Tulipán", "Balcón", EstadoPlanta.SALUDABLE
        );
        RegistroPlantaDocumento reg3 = new RegistroPlantaDocumento(
                "user123", "p3", "Cactus", "Sala", EstadoPlanta.NECESITA_AGUA
        );
        RegistroPlantaDocumento reg4 = new RegistroPlantaDocumento(
                "user123", "p4", "Helecho", "Baño", EstadoPlanta.ENFERMA
        );

        when(servicio.listarPorUsuario("user123"))
                .thenReturn(List.of(reg1, reg2, reg3, reg4));

        mockMvc.perform(get("/web/registros/estadisticas/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlantas").value(4))
                .andExpect(jsonPath("$.plantasSaludables").value(2))
                .andExpect(jsonPath("$.plantasNecesitanAtencion").value(2));
    }

    @Test
    void obtenerEstadisticas_sinPlantas_retornaCeros() throws Exception {
        when(servicio.listarPorUsuario("user-sin-plantas")).thenReturn(List.of());

        mockMvc.perform(get("/web/registros/estadisticas/user-sin-plantas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlantas").value(0))
                .andExpect(jsonPath("$.plantasSaludables").value(0))
                .andExpect(jsonPath("$.plantasNecesitanAtencion").value(0));
    }

    @Test
    void obtenerEstadisticas_cuandoHayError_retorna500ConMensaje() throws Exception {
        when(servicio.listarPorUsuario("user-error"))
                .thenThrow(new RuntimeException("Error crítico"));

        mockMvc.perform(get("/web/registros/estadisticas/user-error"))
                .andExpect(status().is(500))
                .andExpect(jsonPath("$.error").value("Error crítico"));
    }

    @Test
    void actualizar_todosLosCampos_retorna200() throws Exception {
        RegistroPlantaDocumento existente = new RegistroPlantaDocumento(
                "user123", "plant456", "Rosa Original", "Jardín", EstadoPlanta.SALUDABLE
        );
        existente.setId("reg-100");

        RegistroPlantaDocumento actualizado = new RegistroPlantaDocumento(
                "user123", "plant456", "Rosa Actualizada", "Balcón", EstadoPlanta.NECESITA_AGUA
        );
        actualizado.setId("reg-100");
        actualizado.setFotoPersonal("nueva-foto.jpg");
        actualizado.setNotas("Notas actualizadas");

        when(servicio.obtenerPorId("reg-100")).thenReturn(existente);
        when(servicio.guardar(any(RegistroPlantaDocumento.class))).thenReturn(actualizado);

        Map<String, Object> datos = new HashMap<>();
        datos.put("apodo", "Rosa Actualizada");
        datos.put("ubicacion", "Balcón");
        datos.put("estado", "NECESITA_AGUA");
        datos.put("fotoPersonal", "nueva-foto.jpg");
        datos.put("notas", "Notas actualizadas");

        mockMvc.perform(put("/web/registros/reg-100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apodo").value("Rosa Actualizada"))
                .andExpect(jsonPath("$.ubicacion").value("Balcón"));
    }

    @Test
    void actualizar_soloAlgunosCampos_retorna200() throws Exception {
        RegistroPlantaDocumento existente = new RegistroPlantaDocumento(
                "user123", "plant456", "Rosa", "Jardín", EstadoPlanta.SALUDABLE
        );
        existente.setId("reg-100");

        when(servicio.obtenerPorId("reg-100")).thenReturn(existente);
        when(servicio.guardar(any(RegistroPlantaDocumento.class))).thenReturn(existente);

        Map<String, Object> datos = new HashMap<>();
        datos.put("apodo", "Rosa Modificada");

        mockMvc.perform(put("/web/registros/reg-100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isOk());
    }

    @Test
    void actualizar_cuandoNoExiste_retorna404() throws Exception {
        when(servicio.obtenerPorId("reg-999")).thenReturn(null);

        Map<String, Object> datos = new HashMap<>();
        datos.put("apodo", "Nuevo Nombre");

        mockMvc.perform(put("/web/registros/reg-999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizar_cuandoHayError_retorna500() throws Exception {
        RegistroPlantaDocumento existente = new RegistroPlantaDocumento(
                "user123", "plant456", "Rosa", "Jardín", EstadoPlanta.SALUDABLE
        );
        existente.setId("reg-100");

        when(servicio.obtenerPorId("reg-100")).thenReturn(existente);
        when(servicio.guardar(any(RegistroPlantaDocumento.class)))
                .thenThrow(new RuntimeException("Error al guardar"));

        Map<String, Object> datos = new HashMap<>();
        datos.put("apodo", "Error");

        mockMvc.perform(put("/web/registros/reg-100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().is(500));
    }

    @Test
    void eliminar_registroExistente_retorna200() throws Exception {
        doNothing().when(servicio).eliminar("reg-100");

        mockMvc.perform(delete("/web/registros/reg-100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Planta eliminada exitosamente"));
    }

    @Test
    void eliminar_cuandoHayError_retorna500() throws Exception {
        doThrow(new RuntimeException("Error al eliminar"))
                .when(servicio).eliminar("reg-error");

        mockMvc.perform(delete("/web/registros/reg-error"))
                .andExpect(status().is(500))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error al eliminar planta: Error al eliminar"));
    }

    // ========================================
    // Tests para helper obtenerPlantaCatalogo
    // (indirectos a través de obtenerPorId)
    // ========================================
    @Test
    void obtenerPlantaCatalogo_conEtiquetaExterior_retornaTipoExterior() throws Exception {
        RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                "user123", "plant-ext", "Planta Exterior", "Patio", EstadoPlanta.SALUDABLE
        );
        registro.setId("reg-ext");

        Planta planta = new Planta("Geranio", "Pelargonium", "Flor", "url");
        planta.setId("plant-ext");
        planta.agregarEtiqueta(new com.planta.plantapp.dominio.modelo.planta.Etiqueta("exterior", "morado"));

        when(servicio.obtenerPorId("reg-ext")).thenReturn(registro);
        when(servicioPlanta.obtenerPorId("plant-ext")).thenReturn(Optional.of(planta));

        mockMvc.perform(get("/web/registros/reg-ext"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planta.tipo").value("exterior"));
    }

    @Test
    void obtenerPlantaCatalogo_sinEtiquetas_retornaTipoInteriorPorDefecto() throws Exception {
        RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                "user123", "plant-sin-etiquetas", "Planta", "Casa", EstadoPlanta.SALUDABLE
        );
        registro.setId("reg-sin-etiquetas");

        Planta planta = new Planta("Helecho", "Nephrolepis", "Verde", "url");
        planta.setId("plant-sin-etiquetas");

        when(servicio.obtenerPorId("reg-sin-etiquetas")).thenReturn(registro);
        when(servicioPlanta.obtenerPorId("plant-sin-etiquetas")).thenReturn(Optional.of(planta));

        mockMvc.perform(get("/web/registros/reg-sin-etiquetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planta.tipo").value("interior"));
    }

    @Test
    void obtenerPlantaCatalogo_cuandoHayErrorAlObtenerPlanta_retornaMapaVacio() throws Exception {
        RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                "user123", "plant-error", "Planta", "Casa", EstadoPlanta.SALUDABLE
        );
        registro.setId("reg-error");

        when(servicio.obtenerPorId("reg-error")).thenReturn(registro);
        when(servicioPlanta.obtenerPorId("plant-error"))
                .thenThrow(new RuntimeException("Error al acceder al catálogo"));

        mockMvc.perform(get("/web/registros/reg-error"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planta").isEmpty());
    }
}