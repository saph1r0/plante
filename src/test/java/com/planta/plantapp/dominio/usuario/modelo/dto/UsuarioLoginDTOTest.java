package com.planta.plantapp.dominio.usuario.modelo.dto;

import com.planta.userservice.dominio.modelo.dto.UsuarioLoginDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioLoginDTOTest {

    @Test
    void deberiaAsignarYObtenerValoresCorrectamente() {
        UsuarioLoginDTO dto = new UsuarioLoginDTO();
        dto.setCorreo(" test@gmail.com ");
        dto.setContrasena(" 12345678 ");

        assertEquals("test@gmail.com", dto.getCorreo());
        assertEquals("12345678", dto.getContrasena());
    }

    @Test
    void constructorConParametros_deberiaAsignarCampos() {
        UsuarioLoginDTO dto = new UsuarioLoginDTO("user@gmail.com", "clave123");
        assertEquals("user@gmail.com", dto.getCorreo());
        assertEquals("clave123", dto.getContrasena());
    }
}
