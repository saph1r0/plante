package com.planta.userservice.infraestructura.seguridad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {  // Cambiado a public

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private SecurityFilterChain securityFilterChain;

    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {  // Cambiado a public
        securityConfig = new SecurityConfig(jwtService);
    }

    @Test
    public void constructor_deberiaInicializarConJwtService() {  // Cambiado a public
        // Then
        assertNotNull(securityConfig);
        // Verificar que el JwtService fue inyectado
        // Nota: necesitarías ReflectionTestUtils para acceder al campo privado
        // O hacer el campo package-private para testing
    }

    @Test
    public void filterChain_deberiaConfigurarSeguridadCorrectamente() throws Exception {  // Cambiado a public
        // Given
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(), eq(UsernamePasswordAuthenticationFilter.class))).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn((DefaultSecurityFilterChain) securityFilterChain);

        // When
        SecurityFilterChain result = securityConfig.filterChain(httpSecurity);

        // Then
        assertNotNull(result);
        assertEquals(securityFilterChain, result);

        // Verificar todas las configuraciones
        verify(httpSecurity).cors(any());
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).sessionManagement(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).addFilterBefore(any(JwtFilter.class), eq(UsernamePasswordAuthenticationFilter.class));
        verify(httpSecurity).build();
    }

    @Test
    public void passwordEncoder_deberiaRetornarBCryptPasswordEncoder() {  // Cambiado a public
        // When
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        // Then
        assertNotNull(passwordEncoder);
        assertInstanceOf(org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.class, passwordEncoder);
    }

    @Test
    public void userDetailsService_deberiaLanzarUsernameNotFoundException() {  // Cambiado a public
        // When
        var userDetailsService = securityConfig.userDetailsService();

        // Then
        assertNotNull(userDetailsService);
        assertThrows(
                org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("anyuser")
        );
    }

    @Test
    public void userDetailsService_deberiaTenerMensajeCorrecto() {  // Cambiado a public
        // Given
        var userDetailsService = securityConfig.userDetailsService();
        String username = "testuser";

        // When & Then
        var exception = assertThrows(
                org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );

        assertEquals("No users here", exception.getMessage());
    }

    @Test
    public void authenticationManager_deberiaRetornarAuthenticationManager() throws Exception {  // Cambiado a public
        // Given
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        // When
        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);

        // Then
        assertNotNull(result);
        assertEquals(authenticationManager, result);
        verify(authenticationConfiguration).getAuthenticationManager();
    }
}