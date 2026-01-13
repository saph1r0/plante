package com.planta.userservice.infraestructura.seguridad;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.filter.CorsFilter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CorsConfigTest {

    @Test
    public void corsFilter_deberiaCrearFiltro() {
        // Given
        CorsConfig corsConfig = new CorsConfig();

        // When
        CorsFilter corsFilter = corsConfig.corsFilter();

        // Then
        assertNotNull(corsFilter);
        assertEquals(CorsFilter.class, corsFilter.getClass());
    }

    @Test
    public void constructor_deberiaExistir() {
        // When
        CorsConfig corsConfig = new CorsConfig();

        // Then
        assertNotNull(corsConfig);
    }

    @Test
    public void corsFilter_bean_deberiaEstarAnotado() throws NoSuchMethodException {
        // Verificar que el método está anotado con @Bean
        var method = CorsConfig.class.getMethod("corsFilter");
        assertNotNull(method.getAnnotation(org.springframework.context.annotation.Bean.class));
    }
}