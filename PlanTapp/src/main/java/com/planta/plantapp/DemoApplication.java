package com.planta.plantapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.planta.plantapp.aplicacion.servicios.CatalogoService;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase principal de la aplicación PlantCare Backend
 */
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class DemoApplication implements CommandLineRunner {

    // ========================================
    // CONSTANTES
    // ========================================
    private static final String SEPARADOR_VISUAL = "========================================";
    private static final String APP_NAME = "PlantCare Backend API";
    private static final String APP_VERSION = "v1.0.0";
    private static final String BASE_URL = "http://localhost:8080/api";

    // Logger en lugar de System.out
    private static final Logger logger = Logger.getLogger(DemoApplication.class.getName());

    @Autowired
    private CatalogoService catalogoService;

    /**
     * Método principal de la aplicación
     */
    public static void main(String[] args) {
        try {
            logger.info("Iniciando aplicación PlantCare Backend...");
            ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
            logger.info("Aplicación iniciada exitosamente");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error iniciando la aplicación", e);
            System.exit(1);
        }
    }

    /**
     * Se ejecuta después del arranque de Spring Boot
     */
    @Override
    public void run(String... args) {
        try {
            mostrarBannerInicio();
            inicializarDatos();
            mostrarInformacionServicio();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error durante la inicialización", e);
        }
    }

    /**
     * Muestra el banner de inicio de la aplicación
     */
    private void mostrarBannerInicio() {
        logger.info(SEPARADOR_VISUAL);
        logger.log(Level.INFO, "🌱 {0} {1}", new Object[]{APP_NAME, APP_VERSION});
        logger.info("🚀 Sistema de gestión de plantas iniciado");
        logger.info(SEPARADOR_VISUAL);
    }

    /**
     * Inicializa los datos necesarios para la aplicación
     */
    private void inicializarDatos() {
        try {
            logger.info("📋 Inicializando catálogo de plantas...");

            if (catalogoService != null) {
                catalogoService.inicializarCatalogo();
                String estadisticas = catalogoService.getEstadisticasCatalogo();
                logger.log(Level.INFO, "✅ Catálogo inicializado: {0}", estadisticas);
            } else {
                logger.warning("⚠️ CatalogoService no disponible");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "Error inicializando catálogo", e);
        }
    }

    /**
     * Muestra información del servicio y endpoints disponibles
     */
    private void mostrarInformacionServicio() {
        logger.info(SEPARADOR_VISUAL);
        logger.info("API REST disponible en:");
        logger.log(Level.INFO, "   Base URL: {0}", BASE_URL);
        logger.info("Endpoints principales:");
        mostrarEndpoints();
        logger.info(SEPARADOR_VISUAL);
        logger.info("✅ Sistema listo para recibir peticiones");
    }

    /**
     * Muestra los endpoints principales de la API
     */
    private void mostrarEndpoints() {
        String[] endpoints = {
                "GET    /plantas/catalogo - Obtener catálogo completo",
                "GET    /plantas/catalogo/buscar?query=X - Buscar plantas",
                "POST   /plantas/registro - Registrar nueva planta de usuario",
                "GET    /plantas/usuario/{id} - Obtener plantas de usuario",
                "POST   /plantas/registro/{id}/cuidado - Registrar cuidado",
                "GET    /plantas/estadisticas/usuario/{id} - Estadísticas usuario"
        };

        for (String endpoint : endpoints) {
            logger.log(Level.INFO, "   {0}", endpoint);
        }
    }

    /**
     * Información de la aplicación para health checks
     */
    public static String getApplicationInfo() {
        return String.format("%s %s - Sistema de gestión de plantas", APP_NAME, APP_VERSION);
    }

    /**
     * URL base de la aplicación
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Método para mostrar estado de la aplicación
     */
    public void mostrarEstadoAplicacion() {
        try {
            logger.info("🔍 Estado actual de la aplicación:");
            logger.log(Level.INFO, "   Nombre: {0}", APP_NAME);
            logger.log(Level.INFO, "   Versión: {0}", APP_VERSION);
            logger.log(Level.INFO, "   URL Base: {0}", BASE_URL);

            if (catalogoService != null) {
                String stats = catalogoService.getEstadisticasCatalogo();
                logger.log(Level.INFO, "   {0}", stats);
            }

            // Información de memoria
            Runtime runtime = Runtime.getRuntime();
            long memoryUsed = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
            logger.log(Level.INFO, "   Memoria utilizada: {0} MB", memoryUsed);

        } catch (Exception e) {
            logger.log(Level.WARNING, "Error obteniendo estado de la aplicación", e);
        }
    }
}
