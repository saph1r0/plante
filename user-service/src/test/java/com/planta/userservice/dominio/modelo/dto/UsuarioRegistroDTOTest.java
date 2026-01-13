package com.planta.userservice.dominio.modelo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioRegistroDTOTest {

    @Test
    void constructorConParametros_deberiaAsignarCamposCorrectamente() {
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO("Juan", "juan@gmail.com", "clave123");
        assertEquals("Juan", dto.getNombre());
        assertEquals("juan@gmail.com", dto.getCorreo());
        assertEquals("clave123", dto.getContrasena());
    }

    @Test
    void settersYGetters_deberianAsignarYObtenerValores() {
        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Ana");
        dto.setCorreo("ana@gmail.com");
        dto.setContrasena("pass456");

        assertEquals("Ana", dto.getNombre());
        assertEquals("ana@gmail.com", dto.getCorreo());
        assertEquals("pass456", dto.getContrasena());
    }
}
