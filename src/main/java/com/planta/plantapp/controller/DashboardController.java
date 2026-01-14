package com.planta.plantapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> dashboard(Authentication authentication) {

        String correo = authentication.getName();

        Map<String, String> response = Map.of("mensaje", "Bienvenido " + correo);

        return ResponseEntity.ok(response);
    }
}
