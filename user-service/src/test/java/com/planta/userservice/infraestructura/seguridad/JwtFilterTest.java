package com.planta.userservice.infraestructura.seguridad;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtFilter jwtFilter;
    private final String validToken = "valid.jwt.token";
    private final String bearerToken = "Bearer valid.jwt.token";
    private final String correo = "test@example.com";

    @BeforeEach
    public void setUp() {
        jwtFilter = new JwtFilter(jwtService);
        SecurityContextHolder.clearContext(); // Limpiar contexto de seguridad
    }

    @Test
    public void constructor_deberiaInicializarConJwtService() {
        // Then
        assertNotNull(jwtFilter);
    }

    @Test
    public void doFilterInternal_conTokenValido_deberiaEstablecerAutenticacion() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(correo);
        when(jwtService.obtenerClaims(validToken)).thenReturn(claims);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(correo, SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void doFilterInternal_sinHeaderAuthorization_deberiaContinuarFiltro() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void doFilterInternal_conHeaderVacio_deberiaContinuarFiltro() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("");

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void doFilterInternal_conTokenSinBearer_deberiaContinuarFiltro() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(validToken);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void doFilterInternal_conBearerPeroSinEspacio_deberiaContinuarFiltro() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer");

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void doFilterInternal_conTokenInvalido_deberiaRetornar401() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtService.obtenerClaims(validToken)).thenThrow(new RuntimeException("Token inválido"));

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_conTokenExpirado_deberiaRetornar401() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtService.obtenerClaims(validToken)).thenThrow(new io.jsonwebtoken.ExpiredJwtException(null, null, "Token expirado"));

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_conTokenConFirmaInvalida_deberiaRetornar401() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtService.obtenerClaims(validToken)).thenThrow(new io.jsonwebtoken.security.SignatureException("Firma inválida"));

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_conClaimsNull_deberiaRetornar401() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtService.obtenerClaims(validToken)).thenReturn(null);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_conSubjectNull_deberiaEstablecerAutenticacion() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(null);
        when(jwtService.obtenerClaims(validToken)).thenReturn(claims);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertNull(SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_conSubjectVacio_deberiaEstablecerAutenticacion() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("");
        when(jwtService.obtenerClaims(validToken)).thenReturn(claims);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_conExceptionEnFilterChain_deberiaPropagarExcepcion() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);
        doThrow(new IOException("Error en filter chain")).when(filterChain).doFilter(request, response);

        // When & Then
        assertThrows(IOException.class, () ->
                jwtFilter.doFilterInternal(request, response, filterChain));
    }

    @Test
     public void doFilterInternal_conBearerEnMinusculas_deberiaFuncionar() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("bearer " + validToken);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(correo);
        when(jwtService.obtenerClaims(validToken)).thenReturn(claims);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
     public void doFilterInternal_conMultiplesEspaciosEnBearer_deberiaFuncionar() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer   " + validToken);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(correo);
        when(jwtService.obtenerClaims(validToken)).thenReturn(claims);

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_soloBearerSinToken_deberiaContinuarFiltro() throws Exception {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer ");

        // When
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void doFilterInternal_limpiarContextoAnterior() throws Exception {
        // Given - Primero establecer un contexto
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(correo);
        when(jwtService.obtenerClaims(validToken)).thenReturn(claims);

        // When - Primera llamada
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then - Verificar que se estableció
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // When - Segunda llamada sin token
        SecurityContextHolder.clearContext();
        when(request.getHeader("Authorization")).thenReturn(null);
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Then - Verificar que no hay autenticación
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}