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
                                "/plantcare/**", "/api/plantas/**", "/css/**", "/js/**",
                                "/web/login", "/web/registro", "/web/index", "/web/plantas/dashboard",
                                "/web/plantas/dashboard2", "/web/plantas/registro", "/web/plantas/health",
                                "/web/plantas/buscar", "/web/plantas/vista", "/web/plantas/mis-plantas",
                                "/usuarios/**", "/web/dashboard#eventos", "/web/plantas/catalogo",
                                "/web/test2", "/web/plantas/**", "/login/**", "/images/**", "/static/**",
                                "/web/registros/**", "/logout",
                                "/web/usuario-actual"
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/web/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
