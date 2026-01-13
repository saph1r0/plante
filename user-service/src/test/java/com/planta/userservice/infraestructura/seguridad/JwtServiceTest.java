package com.planta.userservice.infraestructura.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "miClaveSecretaMuyLargaParaQueFuncioneCorrectamente123";
    private final String correo = "test@example.com";
    private final Long userId = 123L;
    private final String rol = "USER";

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secret);
    }

    @Test
    public void generarToken_deberiaCrearTokenValido() {
        // When
        String token = jwtService.generarToken(userId, correo, rol);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // Verificar que el token puede ser parseado
        Claims claims = jwtService.obtenerClaims(token);
        assertNotNull(claims);
        assertEquals(correo, claims.getSubject());
        assertEquals(userId, claims.get("id", Long.class));
        assertEquals(rol, claims.get("rol", String.class));
    }

    @Test
    public void generarToken_deberiaIncluirFechaExpiracion() {
        // When
        String token = jwtService.generarToken(userId, correo, rol);
        Claims claims = jwtService.obtenerClaims(token);

        // Then
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
    }

    @Test
    public void obtenerClaims_deberiaRetornarClaimsParaTokenValido() {
        // Given
        String token = jwtService.generarToken(userId, correo, rol);

        // When
        Claims claims = jwtService.obtenerClaims(token);

        // Then
        assertNotNull(claims);
        assertEquals(correo, claims.getSubject());
        assertEquals(userId, claims.get("id", Long.class));
        assertEquals(rol, claims.get("rol", String.class));
    }

    @Test
    public void obtenerClaims_deberiaLanzarExcepcionParaTokenInvalido() {
        // Given
        String tokenInvalido = "token.invalido.falso";

        // When & Then
        assertThrows(Exception.class, () -> jwtService.obtenerClaims(tokenInvalido));
    }

    @Test
    public void obtenerClaims_deberiaLanzarExcepcionParaTokenExpirado() throws Exception {
        // Given - Crear token expirado manualmente
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Date fechaPasada = new Date(System.currentTimeMillis() - 100000);

        String tokenExpirado = Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(fechaPasada)
                .setExpiration(fechaPasada)
                .signWith(key)
                .compact();

        // When & Then
        assertThrows(ExpiredJwtException.class, () -> jwtService.obtenerClaims(tokenExpirado));
    }

    @Test
    public void obtenerClaims_deberiaLanzarExcepcionParaTokenConFirmaInvalida() {
        // Given - Crear token con diferente secreto
        JwtService otroJwtService = new JwtService();
        ReflectionTestUtils.setField(otroJwtService, "secret", "otroSecretoDiferente");
        String tokenConOtroSecreto = otroJwtService.generarToken(userId, correo, rol);

        // When & Then
        assertThrows(SignatureException.class, () -> jwtService.obtenerClaims(tokenConOtroSecreto));
    }

    @Test
    public void esTokenValido_deberiaRetornarTrueParaTokenValido() {
        // Given
        String token = jwtService.generarToken(userId, correo, rol);

        // When
        boolean resultado = jwtService.esTokenValido(token);

        // Then
        assertTrue(resultado);
    }

    @Test
    public void esTokenValido_deberiaRetornarFalseParaTokenInvalido() {
        // Given
        String tokenInvalido = "token.invalido.falso";

        // When
        boolean resultado = jwtService.esTokenValido(tokenInvalido);

        // Then
        assertFalse(resultado);
    }

    @Test
    public void esTokenValido_deberiaRetornarFalseParaTokenExpirado() throws Exception {
        // Given - Crear token expirado
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Date fechaPasada = new Date(System.currentTimeMillis() - 100000);

        String tokenExpirado = Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(fechaPasada)
                .setExpiration(fechaPasada)
                .signWith(key)
                .compact();

        // When
        boolean resultado = jwtService.esTokenValido(tokenExpirado);

        // Then
        assertFalse(resultado);
    }

    @Test
    public void esTokenValido_deberiaRetornarFalseParaTokenNull() {
        // When
        boolean resultado = jwtService.esTokenValido(null);

        // Then
        assertFalse(resultado);
    }

    @Test
    public void esTokenValido_deberiaRetornarFalseParaTokenVacio() {
        // When
        boolean resultado = jwtService.esTokenValido("");

        // Then
        assertFalse(resultado);
    }

    @Test
    public void obtenerCorreo_deberiaRetornarCorreoDelToken() {
        // Given
        String token = jwtService.generarToken(userId, correo, rol);

        // When
        String correoObtenido = jwtService.obtenerCorreo(token);

        // Then
        assertEquals(correo, correoObtenido);
    }

    @Test
    void obtenerCorreo_deberiaLanzarExcepcionParaTokenInvalido() {
        // Given
        String tokenInvalido = "token.invalido.falso";

        // When & Then
        assertThrows(Exception.class, () -> jwtService.obtenerCorreo(tokenInvalido));
    }

    @Test
    public void obtenerUserId_deberiaRetornarUserIdDelToken() {
        // Given
        String token = jwtService.generarToken(userId, correo, rol);

        // When
        Long userIdObtenido = jwtService.obtenerUserId(token);

        // Then
        assertEquals(userId, userIdObtenido);
    }

    @Test
    public void obtenerUserId_deberiaLanzarExcepcionParaTokenInvalido() {
        // Given
        String tokenInvalido = "token.invalido.falso";

        // When & Then
        assertThrows(Exception.class, () -> jwtService.obtenerUserId(tokenInvalido));
    }

    @Test
    public void obtenerRol_deberiaRetornarRolDelToken() {
        // Given
        String token = jwtService.generarToken(userId, correo, rol);

        // When
        String rolObtenido = jwtService.obtenerRol(token);

        // Then
        assertEquals(rol, rolObtenido);
    }

    @Test
    public void obtenerRol_deberiaLanzarExcepcionParaTokenInvalido() {
        // Given
        String tokenInvalido = "token.invalido.falso";

        // When & Then
        assertThrows(Exception.class, () -> jwtService.obtenerRol(tokenInvalido));
    }

    @Test
    public void generarToken_conDiferentesRoles() {
        // Test con diferentes roles
        String[] roles = {"USER", "ADMIN", "MODERATOR"};

        for (String rolTest : roles) {
            String token = jwtService.generarToken(userId, correo, rolTest);
            String rolObtenido = jwtService.obtenerRol(token);
            assertEquals(rolTest, rolObtenido);
        }
    }

    @Test
    public void generarToken_conDiferentesEmails() {
        // Test con diferentes emails
        String[] emails = {"user1@test.com", "admin@test.com", "test@domain.com"};

        for (String emailTest : emails) {
            String token = jwtService.generarToken(userId, emailTest, rol);
            String emailObtenido = jwtService.obtenerCorreo(token);
            assertEquals(emailTest, emailObtenido);
        }
    }

    @Test
    public void generarToken_conDiferentesUserIds() {
        // Test con diferentes IDs
        Long[] ids = {1L, 999L, 123456L};

        for (Long idTest : ids) {
            String token = jwtService.generarToken(idTest, correo, rol);
            Long idObtenido = jwtService.obtenerUserId(token);
            assertEquals(idTest, idObtenido);
        }
    }

    @Test
    public void constructorPorDefecto_deberiaExistir() {
        // Verificar que el constructor por defecto existe y funciona
        JwtService service = new JwtService();
        assertNotNull(service);
    }

    @Test
    public void expirationTime_deberiaSerUnaHora() throws Exception {
        // Verificar que el tiempo de expiración es de 1 hora
        Method method = JwtService.class.getDeclaredMethod("generarToken", Long.class, String.class, String.class);

        // Crear token y verificar expiración
        String token = jwtService.generarToken(userId, correo, rol);
        Claims claims = jwtService.obtenerClaims(token);

        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();

        long diferencia = expiration.getTime() - issuedAt.getTime();
        assertEquals(1000 * 60 * 60, diferencia, 1000); // 1 hora ± 1 segundo
    }
}
