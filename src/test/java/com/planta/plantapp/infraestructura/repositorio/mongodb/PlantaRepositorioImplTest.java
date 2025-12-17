package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.infraestructura.documento.PlantaDocumento;
import com.planta.plantapp.infraestructura.mapper.PlantaDocumentoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlantaRepositorioImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PlantaDocumentoMapper mapper;

    private PlantaRepositorioImpl repositorio;
    private Planta plantaDominio;
    private PlantaDocumento plantaDocumento;

    @BeforeEach
    void setUp() {
        repositorio = new PlantaRepositorioImpl(mongoTemplate, mapper);

        // Planta de dominio de prueba
        plantaDominio = new Planta(
                "Rosa",
                "Rosa rubiginosa",
                "Flor roja hermosa",
                "https://rosa.jpg"
        );
        plantaDominio.setId("plant-123");

        // Documento de MongoDB de prueba
        plantaDocumento = new PlantaDocumento();
        plantaDocumento.setId("plant-123");
        plantaDocumento.setNombreComun("Rosa");
        plantaDocumento.setNombreCientifico("Rosa rubiginosa");
    }

    // ========================================
    // Tests para obtenerPorId()
    // ========================================

    @Test
    void obtenerPorId_conIdValido_retornaPlanta() {
        when(mongoTemplate.findById("plant-123", PlantaDocumento.class))
                .thenReturn(plantaDocumento);
        when(mapper.documentoADominio(plantaDocumento))
                .thenReturn(plantaDominio);

        Planta resultado = repositorio.obtenerPorId("plant-123");

        assertNotNull(resultado);
        assertEquals("Rosa", resultado.getNombreComun());
        verify(mongoTemplate).findById("plant-123", PlantaDocumento.class);
        verify(mapper).documentoADominio(plantaDocumento);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "   " })
    void obtenerPorId_conIdInvalido_retornaNull(String id) {
        Planta resultado = repositorio.obtenerPorId(id);

        assertNull(resultado);
        verify(mongoTemplate, never()).findById(anyString(), any());
    }

    @Test
    void obtenerPorId_conIdNull_retornaNull() {
        Planta resultado = repositorio.obtenerPorId(null);

        assertNull(resultado);
        verify(mongoTemplate, never()).findById(anyString(), any());
    }


    @Test
    void obtenerPorId_documentoNoExiste_retornaNull() {
        when(mongoTemplate.findById("plant-999", PlantaDocumento.class))
                .thenReturn(null);

        Planta resultado = repositorio.obtenerPorId("plant-999");

        assertNull(resultado);
        verify(mongoTemplate).findById("plant-999", PlantaDocumento.class);
        verify(mapper, never()).documentoADominio(any());
    }

    @Test
    void obtenerPorId_errorEnMongo_retornaNull() {
        when(mongoTemplate.findById(anyString(), eq(PlantaDocumento.class)))
                .thenThrow(new RuntimeException("Error de MongoDB"));

        Planta resultado = repositorio.obtenerPorId("plant-123");

        assertNull(resultado);
    }

    // ========================================
    // Tests para listarPorUsuario()
    // ========================================

    @Test
    void listarPorUsuario_conUsuarioValido_retornaLista() {
        List<PlantaDocumento> documentos = List.of(plantaDocumento);
        when(mongoTemplate.find(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(documentos);
        when(mapper.documentoADominio(plantaDocumento))
                .thenReturn(plantaDominio);

        List<Planta> resultado = repositorio.listarPorUsuario("user-123");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Rosa", resultado.get(0).getNombreComun());
        verify(mongoTemplate).find(any(Query.class), eq(PlantaDocumento.class));
    }

    @Test
    void listarPorUsuario_usuarioSinPlantas_retornaListaVacia() {
        when(mongoTemplate.find(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(List.of());

        List<Planta> resultado = repositorio.listarPorUsuario("user-999");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarPorUsuario_usuarioIdNull_retornaListaVacia() {
        List<Planta> resultado = repositorio.listarPorUsuario(null);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(mongoTemplate, never()).find(any(Query.class), any());
    }

    @Test
    void listarPorUsuario_usuarioIdVacio_retornaListaVacia() {
        List<Planta> resultado = repositorio.listarPorUsuario("");

        assertTrue(resultado.isEmpty());
        verify(mongoTemplate, never()).find(any(Query.class), any());
    }

    // ========================================
    // Tests para guardar()
    // ========================================

    @Test
    void guardar_plantaValida_guardaExitosamente() {
        when(mapper.dominioADocumento(plantaDominio))
                .thenReturn(plantaDocumento);
        when(mongoTemplate.save(plantaDocumento))
                .thenReturn(plantaDocumento);

        assertDoesNotThrow(() -> repositorio.guardar(plantaDominio));

        verify(mapper).dominioADocumento(plantaDominio);
        verify(mongoTemplate).save(plantaDocumento);
    }

    @Test
    void guardar_plantaNull_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            repositorio.guardar(null));

        verify(mongoTemplate, never()).save(any());
    }

    @Test
    void guardar_errorEnMongo_lanzaRuntimeException() {
        when(mapper.dominioADocumento(plantaDominio))
                .thenReturn(plantaDocumento);
        when(mongoTemplate.save(plantaDocumento))
                .thenThrow(new RuntimeException("Error de MongoDB"));

        assertThrows(RuntimeException.class, () ->
            repositorio.guardar(plantaDominio));
    }

    @Test
    void eliminar_conIdValido_eliminaExitosamente() {
        assertDoesNotThrow(() -> repositorio.eliminar("plant-123"));

        verify(mongoTemplate).remove(any(Query.class), eq(PlantaDocumento.class));
    }

    @Test
    void eliminar_conIdNull_noHaceNada() {
        assertDoesNotThrow(() -> repositorio.eliminar(null));

        verify(mongoTemplate, never()).remove(any(Query.class), eq(Planta.class));
    }

    @Test
    void eliminar_conIdVacio_noHaceNada() {
        assertDoesNotThrow(() -> repositorio.eliminar(""));

        verify(mongoTemplate, never()).remove(any(Query.class), eq(Planta.class));
    }

    @Test
    void eliminar_errorEnMongo_lanzaRuntimeException() {
        when(mongoTemplate.remove(any(Query.class), eq(PlantaDocumento.class)))
                .thenThrow(new RuntimeException("Error de MongoDB"));

        assertThrows(RuntimeException.class, () ->
            repositorio.eliminar("plant-123"));
    }

    @Test
    void buscarPorNombre_conNombreValido_retornaLista() {
        List<PlantaDocumento> documentos = List.of(plantaDocumento);
        when(mongoTemplate.find(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(documentos);
        when(mapper.documentoADominio(plantaDocumento))
                .thenReturn(plantaDominio);

        List<Planta> resultado = repositorio.buscarPorNombre("Rosa", "user-123");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mongoTemplate).find(any(Query.class), eq(PlantaDocumento.class));
    }

    @Test
    void buscarPorNombre_nombreNull_retornaListaVacia() {
        List<Planta> resultado = repositorio.buscarPorNombre(null, "user-123");

        assertTrue(resultado.isEmpty());
        verify(mongoTemplate, never()).find(any(Query.class), any());
    }

    @Test
    void buscarPorNombre_nombreVacio_retornaListaVacia() {
        List<Planta> resultado = repositorio.buscarPorNombre("", "user-123");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorNombre_sinUsuarioId_buscaEnTodas() {
        List<PlantaDocumento> documentos = List.of(plantaDocumento);
        when(mongoTemplate.find(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(documentos);
        when(mapper.documentoADominio(plantaDocumento))
                .thenReturn(plantaDominio);

        List<Planta> resultado = repositorio.buscarPorNombre("Rosa", null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void actualizarEstado_conParametrosValidos_actualizaExitosamente() {
        assertDoesNotThrow(() ->
            repositorio.actualizarEstado("plant-123", "SALUDABLE"));

        verify(mongoTemplate).updateFirst(
                any(Query.class),
                any(Update.class),
                eq(PlantaDocumento.class)
        );
    }

    @Test
    void actualizarEstado_plantaIdNull_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            repositorio.actualizarEstado(null, "SALUDABLE"));

        verify(mongoTemplate, never()).updateFirst(any(Query.class), any(UpdateDefinition.class), eq(Planta.class));
    }

    @Test
    void actualizarEstado_estadoNull_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            repositorio.actualizarEstado("plant-123", null));
    }

    @Test
    void actualizarEstado_errorEnMongo_lanzaRuntimeException() {
        when(mongoTemplate.updateFirst(any(Query.class), any(Update.class), eq(PlantaDocumento.class)))
                .thenThrow(new RuntimeException("Error de MongoDB"));

        assertThrows(RuntimeException.class, () ->
            repositorio.actualizarEstado("plant-123", "SALUDABLE"));
    }

    @Test
    void buscarPorTipo_conTipoValido_retornaLista() {
        List<PlantaDocumento> documentos = List.of(plantaDocumento);
        when(mongoTemplate.find(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(documentos);
        when(mapper.documentoADominio(plantaDocumento))
                .thenReturn(plantaDominio);

        List<Planta> resultado = repositorio.buscarPorTipo("interior");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mongoTemplate).find(any(Query.class), eq(PlantaDocumento.class));
    }

    @Test
    void buscarPorTipo_tipoNull_retornaListaVacia() {
        List<Planta> resultado = repositorio.buscarPorTipo(null);

        assertTrue(resultado.isEmpty());
        verify(mongoTemplate, never()).find(any(Query.class), any());
    }

    @Test
    void buscarPorTipo_tipoVacio_retornaListaVacia() {
        List<Planta> resultado = repositorio.buscarPorTipo("");

        assertTrue(resultado.isEmpty());
    }

    // ========================================
    // Tests para contarPorUsuario()
    // ========================================

    @Test
    void contarPorUsuario_conUsuarioValido_retornaCantidad() {
        when(mongoTemplate.count(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(5L);

        Long resultado = repositorio.contarPorUsuario("user-123");

        assertEquals(5L, resultado);
        verify(mongoTemplate).count(any(Query.class), eq(PlantaDocumento.class));
    }

    @Test
    void contarPorUsuario_usuarioSinPlantas_retornaCero() {
        when(mongoTemplate.count(any(Query.class), eq(PlantaDocumento.class)))
                .thenReturn(0L);

        Long resultado = repositorio.contarPorUsuario("user-999");

        assertEquals(0L, resultado);
    }

    @Test
    void contarPorUsuario_usuarioIdNull_retornaCero() {
        Long resultado = repositorio.contarPorUsuario(null);

        assertEquals(0L, resultado);
        verify(mongoTemplate, never()).count(any(Query.class), eq(Planta.class));
    }

    @Test
    void contarPorUsuario_usuarioIdVacio_retornaCero() {
        Long resultado = repositorio.contarPorUsuario("");

        assertEquals(0L, resultado);
    }

    @Test
    void contarPorUsuario_errorEnMongo_retornaCero() {
        when(mongoTemplate.count(any(Query.class), eq(PlantaDocumento.class)))
                .thenThrow(new RuntimeException("Error de MongoDB"));

        Long resultado = repositorio.contarPorUsuario("user-123");

        assertEquals(0L, resultado);
    }
}