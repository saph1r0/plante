package com.planta.plantapp.dominio.modelo.bitacora;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BitacoraTest {

    private Bitacora bitacora;
    private Planta planta;

    @BeforeEach
    void setUp() {
        planta = new Planta(
                "Rosa",
                "Rosa rubiginosa",
                "Flor roja hermosa",
                "https://rosa.jpg"
        );
        planta.setId("plant-123");
    }

    @Test
    void constructor_vacio_inicializaFechaActual() {
        Date antes = new Date();
        bitacora = new Bitacora();
        Date despues = new Date();

        assertNotNull(bitacora);
        assertNotNull(bitacora.getFecha());
        assertTrue(bitacora.getFecha().getTime() >= antes.getTime());
        assertTrue(bitacora.getFecha().getTime() <= despues.getTime());
        assertNull(bitacora.getId());
        assertNull(bitacora.getDescripcion());
        assertNull(bitacora.getFoto());
        assertNull(bitacora.getPlanta());
    }

    @Test
    void constructor_conParametros_inicializaCorrectamente() {
        bitacora = new Bitacora(
                "Primera poda de la temporada",
                "https://foto-poda.jpg",
                planta
        );

        assertNotNull(bitacora);
        assertNotNull(bitacora.getFecha());
        assertEquals("Primera poda de la temporada", bitacora.getDescripcion());
        assertEquals("https://foto-poda.jpg", bitacora.getFoto());
        assertEquals(planta, bitacora.getPlanta());
    }

    @Test
    void constructor_conFotoNull_aceptaNull() {
        bitacora = new Bitacora(
                "Observación sin foto",
                null,
                planta
        );

        assertEquals("Observación sin foto", bitacora.getDescripcion());
        assertNull(bitacora.getFoto());
        assertEquals(planta, bitacora.getPlanta());
    }

    @Test
    void constructor_conPlantaNull_aceptaNull() {
        bitacora = new Bitacora(
                "Entrada genérica",
                "https://foto.jpg",
                null
        );

        assertEquals("Entrada genérica", bitacora.getDescripcion());
        assertNull(bitacora.getPlanta());
    }

    @Test
    void constructor_inicializaFechaAutomaticamente() {
        Date antes = new Date();
        bitacora = new Bitacora("Descripción", "foto.jpg", planta);
        Date despues = new Date();

        assertNotNull(bitacora.getFecha());
        assertTrue(bitacora.getFecha().getTime() >= antes.getTime());
        assertTrue(bitacora.getFecha().getTime() <= despues.getTime());
    }

    @Test
    void setId_estableceIdCorrectamente() {
        bitacora = new Bitacora();

        bitacora.setId("bit-001");

        assertEquals("bit-001", bitacora.getId());
    }

    @Test
    void setId_aceptaNull() {
        bitacora = new Bitacora();
        bitacora.setId("bit-001");

        bitacora.setId(null);

        assertNull(bitacora.getId());
    }

    @Test
    void setFecha_estableceFechaCorrectamente() {
        bitacora = new Bitacora();
        Date nuevaFecha = new Date(System.currentTimeMillis() - 86400000); // ayer

        bitacora.setFecha(nuevaFecha);

        assertEquals(nuevaFecha, bitacora.getFecha());
    }

    @Test
    void setFecha_aceptaNull() {
        bitacora = new Bitacora();

        bitacora.setFecha(null);

        assertNull(bitacora.getFecha());
    }

    @Test
    void setDescripcion_estableceDescripcionCorrectamente() {
        bitacora = new Bitacora();

        bitacora.setDescripcion("Nueva descripción detallada");

        assertEquals("Nueva descripción detallada", bitacora.getDescripcion());
    }

    @Test
    void setDescripcion_aceptaNull() {
        bitacora = new Bitacora("Descripción inicial", null, planta);

        bitacora.setDescripcion(null);

        assertNull(bitacora.getDescripcion());
    }

    @Test
    void setDescripcion_aceptaStringVacio() {
        bitacora = new Bitacora();

        bitacora.setDescripcion("");

        assertEquals("", bitacora.getDescripcion());
    }

    @Test
    void setDescripcion_aceptaDescripcionesLargas() {
        bitacora = new Bitacora();
        String descripcionLarga = "Esta es una descripción muy larga y detallada sobre el estado de la planta. " +
                "Se observó un crecimiento significativo en las últimas dos semanas. " +
                "Las hojas tienen un color verde intenso y no se detectan plagas.";

        bitacora.setDescripcion(descripcionLarga);

        assertEquals(descripcionLarga, bitacora.getDescripcion());
    }

    @Test
    void setFoto_estableceFotoCorrectamente() {
        bitacora = new Bitacora();

        bitacora.setFoto("https://example.com/foto.jpg");

        assertEquals("https://example.com/foto.jpg", bitacora.getFoto());
    }

    @Test
    void setFoto_aceptaNull() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);

        bitacora.setFoto(null);

        assertNull(bitacora.getFoto());
    }

    @Test
    void setFoto_aceptaURLsCompletas() {
        bitacora = new Bitacora();
        String urlCompleta = "https://storage.example.com/plantas/usuario123/foto-2024-01-15.jpg";

        bitacora.setFoto(urlCompleta);

        assertEquals(urlCompleta, bitacora.getFoto());
    }

    @Test
    void setPlanta_establecePlantaCorrectamente() {
        bitacora = new Bitacora();

        bitacora.setPlanta(planta);

        assertEquals(planta, bitacora.getPlanta());
        assertEquals("Rosa", bitacora.getPlanta().getNombreComun());
    }

    @Test
    void setPlanta_aceptaNull() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);

        bitacora.setPlanta(null);

        assertNull(bitacora.getPlanta());
    }

    @Test
    void setPlanta_puedeCambiarPlanta() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);

        Planta otraPlanta = new Planta("Tulipán", "Tulipa", "Colorido", "url");
        otraPlanta.setId("plant-456");
        bitacora.setPlanta(otraPlanta);

        assertEquals(otraPlanta, bitacora.getPlanta());
        assertEquals("Tulipán", bitacora.getPlanta().getNombreComun());
    }

    @Test
    void equals_mismaBitacora_retornaTrue() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);
        bitacora.setId("bit-001");

        assertEquals(bitacora, bitacora);
    }

    @Test
    void equals_bitacorasConMismoId_retornaTrue() {
        Bitacora bit1 = new Bitacora("Descripción 1", "foto1.jpg", planta);
        bit1.setId("bit-001");

        Bitacora bit2 = new Bitacora("Descripción 2", "foto2.jpg", null);
        bit2.setId("bit-001");

        assertEquals(bit1, bit2);
    }

    @Test
    void equals_bitacorasConDiferenteId_retornaFalse() {
        Bitacora bit1 = new Bitacora("Descripción", "foto.jpg", planta);
        bit1.setId("bit-001");

        Bitacora bit2 = new Bitacora("Descripción", "foto.jpg", planta);
        bit2.setId("bit-002");

        assertNotEquals(bit1, bit2);
    }

    @Test
    void equals_bitacorasConIdNull_retornaFalse() {
        Bitacora bit1 = new Bitacora("Descripción", "foto.jpg", planta);
        bit1.setId(null);

        Bitacora bit2 = new Bitacora("Descripción", "foto.jpg", planta);
        bit2.setId(null);

        assertNotEquals(bit1, bit2);
    }

    @Test
    void equals_comparacionConNull_retornaFalse() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);
        bitacora.setId("bit-001");

        assertNotEquals(null, bitacora);
    }

    @Test
    void equals_comparacionConOtraClase_retornaFalse() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);
        bitacora.setId("bit-001");
        String otraClase = "No es una bitácora";

        assertNotEquals(otraClase, bitacora);
    }

    @Test
    void hashCode_bitacorasConMismoId_mismoHashCode() {
        Bitacora bit1 = new Bitacora("Desc 1", "foto1.jpg", planta);
        bit1.setId("bit-001");

        Bitacora bit2 = new Bitacora("Desc 2", "foto2.jpg", null);
        bit2.setId("bit-001");

        assertEquals(bit1.hashCode(), bit2.hashCode());
    }

    @Test
    void hashCode_bitacorasConDiferenteId_diferenteHashCode() {
        Bitacora bit1 = new Bitacora("Desc", "foto.jpg", planta);
        bit1.setId("bit-001");

        Bitacora bit2 = new Bitacora("Desc", "foto.jpg", planta);
        bit2.setId("bit-002");

        assertNotEquals(bit1.hashCode(), bit2.hashCode());
    }

    @Test
    void hashCode_mismaBitacora_consistente() {
        bitacora = new Bitacora("Desc", "foto.jpg", planta);
        bitacora.setId("bit-001");

        int hash1 = bitacora.hashCode();
        int hash2 = bitacora.hashCode();

        assertEquals(hash1, hash2);
    }

    @Test
    void toString_conPlanta_incluyeNombrePlanta() {
        bitacora = new Bitacora("Observación de crecimiento", "foto.jpg", planta);

        String resultado = bitacora.toString();

        assertTrue(resultado.contains("Observación de crecimiento"));
        assertTrue(resultado.contains("Rosa"));
    }

    @Test
    void toString_sinPlanta_muestraNa() {
        bitacora = new Bitacora("Entrada sin planta", "foto.jpg", null);

        String resultado = bitacora.toString();

        assertTrue(resultado.contains("Entrada sin planta"));
        assertTrue(resultado.contains("N/A"));
    }

    @Test
    void toString_incluyeFecha() {
        bitacora = new Bitacora("Descripción", "foto.jpg", planta);

        String resultado = bitacora.toString();

        assertTrue(resultado.contains("fecha="));
        assertNotNull(bitacora.getFecha());
    }

    @Test
    void toString_noLanzaExcepcion() {
        bitacora = new Bitacora("Test", "foto.jpg", planta);

        assertDoesNotThrow(() -> bitacora.toString());
    }

    @Test
    void toString_conTodosLosCamposNull_noLanzaExcepcion() {
        bitacora = new Bitacora();
        bitacora.setFecha(null);
        bitacora.setDescripcion(null);
        bitacora.setPlanta(null);

        assertDoesNotThrow(() -> bitacora.toString());
    }

    @Test
    void escenario_crearBitacoraCompleta() {
        Date fechaEspecifica = new Date();
        bitacora = new Bitacora(
                "Primera poda del año",
                "https://storage.example.com/poda-2024.jpg",
                planta
        );
        bitacora.setId("bit-2024-001");
        bitacora.setFecha(fechaEspecifica);

        assertEquals("bit-2024-001", bitacora.getId());
        assertEquals(fechaEspecifica, bitacora.getFecha());
        assertEquals("Primera poda del año", bitacora.getDescripcion());
        assertEquals("https://storage.example.com/poda-2024.jpg", bitacora.getFoto());
        assertEquals(planta, bitacora.getPlanta());
        assertEquals("Rosa", bitacora.getPlanta().getNombreComun());
    }

    @Test
    void escenario_modificarBitacoraDespuesDeCreacion() {
        bitacora = new Bitacora("Descripción inicial", "foto-inicial.jpg", planta);
        bitacora.setId("bit-001");

        // Modificar todos los campos
        Date nuevaFecha = new Date(System.currentTimeMillis() - 3600000); // hace 1 hora
        Planta nuevaPlanta = new Planta("Cactus", "Cactaceae", "Espinoso", "url");

        bitacora.setFecha(nuevaFecha);
        bitacora.setDescripcion("Descripción actualizada");
        bitacora.setFoto("foto-nueva.jpg");
        bitacora.setPlanta(nuevaPlanta);

        assertEquals(nuevaFecha, bitacora.getFecha());
        assertEquals("Descripción actualizada", bitacora.getDescripcion());
        assertEquals("foto-nueva.jpg", bitacora.getFoto());
        assertEquals(nuevaPlanta, bitacora.getPlanta());
    }

    @Test
    void escenario_bitacoraSinFoto() {
        bitacora = new Bitacora(
                "Observación rápida sin foto",
                null,
                planta
        );

        assertNotNull(bitacora.getDescripcion());
        assertNull(bitacora.getFoto());
        assertNotNull(bitacora.getPlanta());
        assertNotNull(bitacora.getFecha());
    }

    @Test
    void escenario_multipleBitacorasParaMismaPlanta() {
        Bitacora bit1 = new Bitacora("Primera observación", "foto1.jpg", planta);
        bit1.setId("bit-001");

        Bitacora bit2 = new Bitacora("Segunda observación", "foto2.jpg", planta);
        bit2.setId("bit-002");

        Bitacora bit3 = new Bitacora("Tercera observación", "foto3.jpg", planta);
        bit3.setId("bit-003");

        // Todas apuntan a la misma planta
        assertEquals(planta, bit1.getPlanta());
        assertEquals(planta, bit2.getPlanta());
        assertEquals(planta, bit3.getPlanta());

        // Pero son bitácoras diferentes
        assertNotEquals(bit1, bit2);
        assertNotEquals(bit2, bit3);
        assertNotEquals(bit1, bit3);
    }

    @Test
    void escenario_crearBitacoraYComparar() {
        Bitacora bit1 = new Bitacora("Riego abundante", "foto.jpg", planta);
        bit1.setId("bit-100");

        Bitacora bit2 = new Bitacora("Fertilización", "foto2.jpg", planta);
        bit2.setId("bit-100"); // mismo ID

        // Son iguales porque tienen el mismo ID
        assertEquals(bit1, bit2);
        assertEquals(bit1.hashCode(), bit2.hashCode());
    }

    @Test
    void edgeCase_descripcionMuyLarga() {
        String descripcionLarga = "a".repeat(10000);
        bitacora = new Bitacora(descripcionLarga, "foto.jpg", planta);

        assertEquals(10000, bitacora.getDescripcion().length());
    }

    @Test
    void edgeCase_urlFotoMuyLarga() {
        String urlLarga = "https://example.com/" + "path/".repeat(100) + "foto.jpg";
        bitacora = new Bitacora("Desc", urlLarga, planta);

        assertEquals(urlLarga, bitacora.getFoto());
    }

    @Test
    void edgeCase_fechaMuyAntigua() {
        bitacora = new Bitacora();
        Date fechaAntigua = new Date(0); // 1 enero 1970

        bitacora.setFecha(fechaAntigua);

        assertEquals(fechaAntigua, bitacora.getFecha());
        assertEquals(0, bitacora.getFecha().getTime());
    }

    @Test
    void edgeCase_fechaFutura() {
        bitacora = new Bitacora();
        Date fechaFutura = new Date(System.currentTimeMillis() + 31536000000L); // +1 año

        bitacora.setFecha(fechaFutura);

        assertTrue(bitacora.getFecha().after(new Date()));
    }

    @Test
    void edgeCase_todosLosCamposNulos() {
        bitacora = new Bitacora();
        bitacora.setId(null);
        bitacora.setFecha(null);
        bitacora.setDescripcion(null);
        bitacora.setFoto(null);
        bitacora.setPlanta(null);

        assertNull(bitacora.getId());
        assertNull(bitacora.getFecha());
        assertNull(bitacora.getDescripcion());
        assertNull(bitacora.getFoto());
        assertNull(bitacora.getPlanta());
    }
}