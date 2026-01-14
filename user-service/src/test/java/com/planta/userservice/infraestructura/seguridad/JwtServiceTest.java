package com.planta.userservice.infraestructura.seguridad;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();
        Field secretField = JwtService.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        // Secret de 32 bytes exactos en UTF-8
        secretField.set(jwtService, "12345678901234567890123456789012");
    }


    @Test
    void tokenInvalidoDevuelveFalse() {
        assertFalse(jwtService.esTokenValido("tokenInvalido"));
    }

    @Test
    void obtenerClaimsTokenInvalidoLanzaExcepcion() {
        assertThrows(Exception.class, () -> jwtService.obtenerClaims("tokenInvalido"));
    }
}
