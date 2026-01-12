package com.planta.plantapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map; // 👈 ESTE FALTABA

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(Authentication authentication) {

        String correo = authentication.getName();

        return ResponseEntity.ok(
                Map.of("mensaje", "Bienvenido " + correo)
        );
    }
}
