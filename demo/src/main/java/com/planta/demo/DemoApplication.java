package com.planta.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Clase principal de la aplicación PlantCare System.
 * 
 */
@SpringBootApplication
@EnableMongoAuditing
public class DemoApplication {

    /**
     * Punto de entrada principal de la aplicación.
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Banner personalizado para identificar la aplicación
        System.setProperty("spring.application.name", "PlantCare System");
        
        // Inicializar aplicación Spring Boot
        SpringApplication.run(DemoApplication.class, args);
        
        // Log de confirmación de arranque
        mostrarMensajeInicializacion();
    }
    
    /**
     * Muestra mensaje de confirmación al usuario sobre el arranque exitoso.
     */
    private static void mostrarMensajeInicializacion() {
        System.out.println();
        System.out.println("🌱 ===============================================");
        System.out.println("🌱 PLANTCARE SYSTEM - INICIADO CORRECTAMENTE");
        System.out.println("🌱 ===============================================");
        System.out.println("🌱 API REST disponible en: http://localhost:8080/api");
        System.out.println("🌱 Base de datos: MongoDB (plantcare_db)");
        System.out.println("🌱 Frontend estático: http://localhost:8080");
        System.out.println("🌱 Estado del sistema: OPERATIVO");
        System.out.println("🌱 ===============================================");
        System.out.println();
    }
}
