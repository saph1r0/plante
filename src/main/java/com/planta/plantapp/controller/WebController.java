package com.planta.plantapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login/login";   // nombre del HTML
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "login/dashboard";
    }
}

