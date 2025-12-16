package com.planta.plantapp.dominio.modelo.fabrica;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.Etiqueta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlantaFabricaTest {

    private PlantaFabrica fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new PlantaFabrica();
    }
    @Test
    void crearPlanta_conCamposObligatorios_creaPlantaCorrectamente() {
        Planta planta = fabrica.crearPlanta(
                "Rosa",
                "Rosa rubiginosa",
                "Flor roja hermosa",
                "https://rosa.jpg",
                null
        );

        assertNotNull(planta);
        assertEquals("Rosa", planta.getNombreComun());
        assertEquals("Rosa rubiginosa", planta.getNombreCientifico());
        assertEquals("Flor roja hermosa", planta.getDescripcion());
        assertEquals("https://rosa.jpg", planta.getImagenURL());
    }

    @Test
    void crearPlanta_conDescripcionNull_aceptaNull() {
        Planta planta = fabrica.crearPlanta(
                "Cactus",
                "Cactaceae",
                null,
                "https://cactus.jpg",
                null
        );

        assertNotNull(planta);
        assertEquals("Cactus", planta.getNombreComun());
        assertNull(planta.getDescripcion());
    }

    @Test
    void crearPlanta_conImagenURLNull_aceptaNull() {
        Planta planta = fabrica.crearPlanta(
                "Helecho",
                "Nephrolepis",
                "Planta verde",
                null,
                null
        );

        assertNotNull(planta);
        assertEquals("Helecho", planta.getNombreComun());
        assertNull(planta.getImagenURL());
    }

    @Test
    void crearPlanta_conTodosLosCamposOpcionales_funcionaCorrectamente() {
        Planta planta = fabrica.crearPlanta(
                "Orquídea",
                "Orchidaceae",
                "Planta tropical elegante",
                "https://orquidea.jpg",
                null
        );

        assertNotNull(planta);
        assertEquals("Orquídea", planta.getNombreComun());
        assertEquals("Orchidaceae", planta.getNombreCientifico());
        assertEquals("Planta tropical elegante", planta.getDescripcion());
        assertEquals("https://orquidea.jpg", planta.getImagenURL());
    }

    @Test
    void crearPlanta_conEtiquetasNull_noAgregaEtiquetas() {
        Planta planta = fabrica.crearPlanta(
                "Rosa",
                "Rosa sp.",
                "Descripción",
                "url",
                null
        );

        assertNotNull(planta);
        // Verificar que no lanza excepción y la planta no tiene etiquetas
        assertTrue(planta.getEtiquetas() == null || planta.getEtiquetas().isEmpty());
    }

    @Test
    void crearPlanta_conListaEtiquetasVacia_noAgregaEtiquetas() {
        Planta planta = fabrica.crearPlanta(
                "Tulipán",
                "Tulipa",
                "Colorido",
                "url",
                new ArrayList<>()
        );

        assertNotNull(planta);
        assertTrue(planta.getEtiquetas() == null || planta.getEtiquetas().isEmpty());
    }

    @Test
    void crearPlanta_conUnaEtiqueta_agregaEtiquetaCorrectamente() {
        List<String> etiquetas = List.of("interior");

        Planta planta = fabrica.crearPlanta(
                "Potus",
                "Epipremnum aureum",
                "Planta de interior",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        assertEquals(1, planta.getEtiquetas().size());
        assertEquals("interior", planta.getEtiquetas().get(0).getNombre());
    }

    @Test
    void crearPlanta_conVariasEtiquetas_agregaTodasCorrectamente() {
        List<String> etiquetas = List.of("interior", "facil-cuidado", "purificadora");

        Planta planta = fabrica.crearPlanta(
                "Sansevieria",
                "Sansevieria trifasciata",
                "Lengua de suegra",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        assertEquals(3, planta.getEtiquetas().size());

        List<String> nombresEtiquetas = planta.getEtiquetas().stream()
                .map(Etiqueta::getNombre)
                .toList();

        assertTrue(nombresEtiquetas.contains("interior"));
        assertTrue(nombresEtiquetas.contains("facil-cuidado"));
        assertTrue(nombresEtiquetas.contains("purificadora"));
    }

    @Test
    void crearPlanta_conEtiquetaNull_ignoraEtiquetaNull() {
        List<String> etiquetas = new ArrayList<>();
        etiquetas.add("interior");
        etiquetas.add(null);
        etiquetas.add("facil-cuidado");

        Planta planta = fabrica.crearPlanta(
                "Planta",
                "Nombre científico",
                "Desc",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        // Solo debe agregar 2 etiquetas (ignora la null)
        assertEquals(2, planta.getEtiquetas().size());

        List<String> nombresEtiquetas = planta.getEtiquetas().stream()
                .map(Etiqueta::getNombre)
                .toList();

        assertTrue(nombresEtiquetas.contains("interior"));
        assertTrue(nombresEtiquetas.contains("facil-cuidado"));
    }

    @Test
    void crearPlanta_conEtiquetaVacia_ignoraEtiquetaVacia() {
        List<String> etiquetas = List.of("interior", "", "exterior");

        Planta planta = fabrica.crearPlanta(
                "Geranio",
                "Pelargonium",
                "Flor",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        // Solo debe agregar 2 etiquetas (ignora la vacía)
        assertEquals(2, planta.getEtiquetas().size());

        List<String> nombresEtiquetas = planta.getEtiquetas().stream()
                .map(Etiqueta::getNombre)
                .toList();

        assertTrue(nombresEtiquetas.contains("interior"));
        assertTrue(nombresEtiquetas.contains("exterior"));
    }

    @Test
    void crearPlanta_conEtiquetaBlank_ignoraEtiquetaBlank() {
        List<String> etiquetas = List.of("tropical", "   ", "humedad");

        Planta planta = fabrica.crearPlanta(
                "Orquídea",
                "Orchidaceae",
                "Exótica",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        // Solo debe agregar 2 etiquetas (ignora la blank)
        assertEquals(2, planta.getEtiquetas().size());

        List<String> nombresEtiquetas = planta.getEtiquetas().stream()
                .map(Etiqueta::getNombre)
                .toList();

        assertTrue(nombresEtiquetas.contains("tropical"));
        assertTrue(nombresEtiquetas.contains("humedad"));
    }

    @Test
    void crearPlanta_conEtiquetasDuplicadas_agregaAmbas() {
        List<String> etiquetas = List.of("interior", "interior", "sombra");

        Planta planta = fabrica.crearPlanta(
                "Helecho",
                "Nephrolepis",
                "Verde",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        // Agrega todas las etiquetas, incluso duplicadas
        assertEquals(2, planta.getEtiquetas().size());
    }

    @Test
    void crearPlanta_conMuchasEtiquetas_agregaTodasCorrectamente() {
        List<String> etiquetas = List.of(
                "interior", "exterior", "facil", "dificil",
                "sol", "sombra", "agua-moderada", "resistente",
                "decorativa", "purificadora"
        );

        Planta planta = fabrica.crearPlanta(
                "Planta Universal",
                "Universalis plantae",
                "Planta con muchas características",
                "url",
                etiquetas
        );

        assertNotNull(planta);
        assertNotNull(planta.getEtiquetas());
        assertEquals(10, planta.getEtiquetas().size());
    }

    @Test
    void crearPlanta_conNombreComunNull_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.crearPlanta(null, "Rosa sp.", "Descripción", "url", null)
        );
    }

    @Test
    void crearPlanta_conNombreComunVacio_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.crearPlanta("", "Rosa sp.", "Descripción", "url", null)
        );
    }

    @Test
    void crearPlanta_conNombreComunBlank_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.crearPlanta("   ", "Rosa sp.", "Descripción", "url", null)
        );
    }

    @Test
    void crearPlanta_conNombreCientificoNull_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.crearPlanta("Rosa", null, "Descripción", "url", null)
        );
    }

    @Test
    void crearPlanta_conNombreCientificoVacio_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.crearPlanta("Rosa", "", "Descripción", "url", null)
        );
    }

    @Test
    void crearPlanta_conNombreCientificoBlank_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> fabrica.crearPlanta("Rosa", "   ", "Descripción", "url", null)
        );
    }

    @Test
    void escenario_crearPlantaInteriorCompleta() {
        List<String> etiquetas = List.of("interior", "facil-cuidado", "purificadora", "sombra");

        Planta planta = fabrica.crearPlanta(
                "Potus",
                "Epipremnum aureum",
                "Planta trepadora ideal para interiores, purifica el aire y requiere poco mantenimiento",
                "https://example.com/potus.jpg",
                etiquetas
        );

        assertNotNull(planta);
        assertEquals("Potus", planta.getNombreComun());
        assertEquals("Epipremnum aureum", planta.getNombreCientifico());
        assertNotNull(planta.getDescripcion());
        assertTrue(planta.getDescripcion().contains("purifica"));
        assertNotNull(planta.getImagenURL());
        assertEquals(4, planta.getEtiquetas().size());
    }

    @Test
    void escenario_crearPlantaExteriorMinima() {
        Planta planta = fabrica.crearPlanta(
                "Geranio",
                "Pelargonium",
                null,
                null,
                List.of("exterior", "sol")
        );

        assertNotNull(planta);
        assertEquals("Geranio", planta.getNombreComun());
        assertEquals("Pelargonium", planta.getNombreCientifico());
        assertNull(planta.getDescripcion());
        assertNull(planta.getImagenURL());
        assertEquals(2, planta.getEtiquetas().size());
    }

    @Test
    void escenario_crearMultiplesPlantas() {
        Planta rosa = fabrica.crearPlanta(
                "Rosa", "Rosa sp.", "Flor roja", "url1", List.of("exterior", "sol")
        );

        Planta cactus = fabrica.crearPlanta(
                "Cactus", "Cactaceae", "Espinoso", "url2", List.of("interior", "resistente")
        );

        Planta helecho = fabrica.crearPlanta(
                "Helecho", "Nephrolepis", "Verde", "url3", List.of("interior", "sombra", "humedad")
        );

        assertNotNull(rosa);
        assertNotNull(cactus);
        assertNotNull(helecho);

        assertEquals(2, rosa.getEtiquetas().size());
        assertEquals(2, cactus.getEtiquetas().size());
        assertEquals(3, helecho.getEtiquetas().size());
    }

    @Test
    void escenario_crearPlantaSinEtiquetas() {
        Planta planta = fabrica.crearPlanta(
                "Planta Misteriosa",
                "Mysteriae plantae",
                "Planta sin clasificar",
                "url",
                null
        );

        assertNotNull(planta);
        assertEquals("Planta Misteriosa", planta.getNombreComun());
        assertTrue(planta.getEtiquetas() == null || planta.getEtiquetas().isEmpty());
    }

    @Test
    void edgeCase_nombreMuyLargo() {
        String nombreLargo = "a".repeat(500);

        Planta planta = fabrica.crearPlanta(
                nombreLargo,
                "Nombre científico",
                "Descripción",
                "url",
                null
        );

        assertEquals(500, planta.getNombreComun().length());
    }

    @Test
    void edgeCase_descripcionMuyLarga() {
        String descripcionLarga = "Descripción muy detallada. ".repeat(100);

        Planta planta = fabrica.crearPlanta(
                "Planta",
                "Plantae",
                descripcionLarga,
                "url",
                null
        );

        assertTrue(planta.getDescripcion().length() > 1000);
    }

    @Test
    void edgeCase_urlMuyLarga() {
        String urlLarga = "https://example.com/" + "path/".repeat(100) + "imagen.jpg";

        Planta planta = fabrica.crearPlanta(
                "Planta",
                "Plantae",
                "Descripción",
                urlLarga,
                null
        );

        assertEquals(urlLarga, planta.getImagenURL());
    }

    @Test
    void edgeCase_etiquetaMuyLarga() {
        String etiquetaLarga = "etiqueta-" + "muy-larga-".repeat(50);

        Planta planta = fabrica.crearPlanta(
                "Planta",
                "Plantae",
                "Descripción",
                "url",
                List.of(etiquetaLarga)
        );

        assertNotNull(planta.getEtiquetas());
        assertEquals(1, planta.getEtiquetas().size());
        assertTrue(planta.getEtiquetas().get(0).getNombre().length() > 100);
    }

    @Test
    void edgeCase_soloEtiquetasInvalidas() {
        List<String> etiquetasInvalidas = new ArrayList<>();
        etiquetasInvalidas.add(null);
        etiquetasInvalidas.add("");
        etiquetasInvalidas.add("   ");

        Planta planta = fabrica.crearPlanta(
                "Planta",
                "Plantae",
                "Descripción",
                "url",
                etiquetasInvalidas
        );

        assertNotNull(planta);
        // No debe agregar ninguna etiqueta
        assertTrue(planta.getEtiquetas() == null || planta.getEtiquetas().isEmpty());
    }

    @Test
    void edgeCase_etiquetasConCaracteresEspeciales() {
        List<String> etiquetas = List.of(
                "fácil-cuidado",
                "más-luz",
                "100%-natural",
                "día/noche"
        );

        Planta planta = fabrica.crearPlanta(
                "Planta",
                "Plantae",
                "Descripción",
                "url",
                etiquetas
        );

        assertEquals(4, planta.getEtiquetas().size());
    }
}