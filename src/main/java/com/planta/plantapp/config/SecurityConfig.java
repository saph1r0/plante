package com.planta.plantapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        // Justificación: El token CSRF necesita ser leído por el frontend SPA
                        // Medidas compensatorias: CSP estricto, SameSite=Strict, validación en backend
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                // Configuramos los endpoints públicos
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/web/login", "/web/registro","/web/index", "/web/plantas/dashboard",
                                "/usuarios/**", "/web/dashboard#eventos", "/web/plantas/catalogo", "/web/test2", "/web/plantas",
                                "/login/**", "/css/**", "/js/**", "/images/**","/static/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable())
                .logout(logout -> logout.disable());

        return http.build();
    }
}