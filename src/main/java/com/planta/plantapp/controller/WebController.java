package com.planta.plantapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/web")
public class WebController {

    // Constantes para los atributos del Model
    private static final String ATTR_USUARIO_NOMBRE = "usuarioNombre";
    private static final String ATTR_USUARIO_CORREO = "usuarioCorreo";

    // Constantes para los valores por defecto
    private static final String DEFAULT_NOMBRE_USUARIO = "Usuario";
    private static final String DEFAULT_CORREO_USUARIO = "correo@ejemplo.com";

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String token, Model model) {

        if (token == null || token.isBlank()) {
            token = "";
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Parametrizamos el tipo del ResponseEntity y del Map
            ResponseEntity<java.util.Map<String, Object>> response = restTemplate.exchange(
                    "http://localhost:8082/api/users/me",
                    HttpMethod.GET,
                    entity,
                    new org.springframework.core.ParameterizedTypeReference<java.util.Map<String, Object>>() {}
            );

            java.util.Map<String, Object> usuarioData = response.getBody();

            if (usuarioData != null) {
                model.addAttribute(ATTR_USUARIO_NOMBRE,
                        usuarioData.getOrDefault("nombre", DEFAULT_NOMBRE_USUARIO));
                model.addAttribute(ATTR_USUARIO_CORREO,
                        usuarioData.getOrDefault("correo", DEFAULT_CORREO_USUARIO));
            } else {
                model.addAttribute(ATTR_USUARIO_NOMBRE, DEFAULT_NOMBRE_USUARIO);
                model.addAttribute(ATTR_USUARIO_CORREO, DEFAULT_CORREO_USUARIO);
            }

        } catch (Exception e) {
            model.addAttribute(ATTR_USUARIO_NOMBRE, DEFAULT_NOMBRE_USUARIO);
            model.addAttribute(ATTR_USUARIO_CORREO, DEFAULT_CORREO_USUARIO);
        }

        return "login/dashboard"; // Thymeleaf template
    }
}
