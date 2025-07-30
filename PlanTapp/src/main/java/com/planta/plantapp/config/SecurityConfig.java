package com.planta.plantapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // Rutas existentes
                                "/web/login", "/web/registro","/web/index", "/web/dashboard",
                                "/usuarios/**", "/web/dashboard#eventos", "/web/plantas/catalogo", "/web/test2", "/web/plantas",
                                "/login/**", "/css/**", "/js/**", "/images/**","/static/**",
                                // Nuevas rutas para bitácora
                                "/", "/home", "/dashboard",
                                "/bitacora/**", "/api/bitacoras/**",
                                "/plantas/**", "/recordatorios/**",
                                // Consola H2 para desarrollo
                                "/h2-console/**",
                                // Recursos estáticos adicionales
                                "/templates/**", "/vista/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                // Configuración especial para H2 Console - Method reference
                .headers(h -> h.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }
}