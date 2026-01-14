package com.planta.userplantsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // Esto inicia el microservicio en el puerto 8081
        SpringApplication.run(Main.class, args);
    }
}