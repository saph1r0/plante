package com.planta.plantapp.dominio.usuario.modelo.dto;

import com.planta.userservice.dominio.modelo.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioDTOTest {

    @Test
    void constructorConParametros_deberiaAsignarCamposCorrectamente() {
        UsuarioDTO dto = new UsuarioDTO(1L, "user@gmail.com");
        assertEquals(1L, dto.getId());
        assertEquals("user@gmail.com", dto.getCorreo());
    }
}
