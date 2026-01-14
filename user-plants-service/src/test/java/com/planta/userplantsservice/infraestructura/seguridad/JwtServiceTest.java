package com.planta.userplantsservice.infraestructura.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private JwtService jwtService;
    private final String secret = "claveSecretaParaPruebasUnitariasDebeSerLarga123456";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Inyectamos el secreto manualmente para el entorno de test
        ReflectionTestUtils.setField(jwtService, "secret", secret);
    }

    @Test
    void isTokenValid_DeberiaValidarTokenCorrecto() {
        String token = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void obtenerClaims_DeberiaRetornarDatosDelUsuario() {
        String token = Jwts.builder()
                .claim("id", "user123")
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        Claims claims = jwtService.obtenerClaims(token);
        assertEquals("user123", claims.get("id"));
    }
}