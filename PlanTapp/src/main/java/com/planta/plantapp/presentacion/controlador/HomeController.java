package com.planta.plantapp.presentacion.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal para manejar las rutas de la página de inicio.
 */
@Controller
public class HomeController {

    /**
     * Maneja la ruta raíz y redirige a la página de índice.
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/web/index";
    }

    /**
     * Maneja rutas alternativas para la página principal.
     */
    @GetMapping("/home")
    public String homeAlternative() {
        return "redirect:/web/index";
    }

    /**
     * Maneja la página de índice directamente si es necesario.
     */
    @GetMapping("/index")
    public String index() {
        return "redirect:/web/index";
    }
}
