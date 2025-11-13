package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaResponseDTO;
import com.planta.plantapp.infraestructura.mapper.PlantaMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestionar las operaciones relacionadas con las plantas.
 */
@Controller
@RequestMapping("/web/plantas")
public class PlantaController {

    private static final Logger logger = LoggerFactory.getLogger(PlantaController.class);

    // Constantes para evitar duplicación de literales
    private static final String SUCCESS = "success";
    private static final String MESSAGE = "message";
    private static final String UBICACION = "ubicacion";
    private static final String APODO = "apodo";
    private static final String TOTAL_PLANTAS = "totalPlantas";
    private static final String STATUS = "status";
    private static final String ESTADO_SALUDABLE = "SALUDABLE";

    private final IServicioPlanta servicioPlanta;

    public PlantaController(IServicioPlanta servicioPlanta) {
        this.servicioPlanta = servicioPlanta;
    }

    /**
     * Obtiene la lista de todas las plantas.
     * 🌱 ¡Aquí es donde verás tus plantitas de MongoDB!
     */
    @GetMapping
    public ResponseEntity<List<Planta>> obtenerTodas(){
        try {
            logger.info("🌍 Controlador: Obteniendo todas las plantas...");
            List<Planta> plantas = servicioPlanta.obtenerTodas();
            logger.info("✅ Controlador: {} plantas obtenidas exitosamente", plantas.size());
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            logger.error("❌ Error en controlador: {}", e.getMessage(), e);
            // Devolver error 500 en lugar de 200 con lista vacía
            return ResponseEntity.status(500).body(List.of());
        }
    }

    /**
     * Debug específico para verificar cuidados
     */
    @GetMapping("/debug-cuidados/{id}")
    public ResponseEntity<Object> debugCuidados(@PathVariable String id) {
        try {
            logger.info("🔍 DEBUG CUIDADOS: Buscando planta con ID: {}", id);
            Optional<Planta> plantaOpt = servicioPlanta.obtenerPorId(id);

            if (plantaOpt.isPresent()) {
                Planta planta = plantaOpt.get();

                logger.info("✅ Planta encontrada: {}", planta.getNombreComun());
                logger.info("🌱 ID de la planta: {}", planta.getId());

                // DEBUG ESPECÍFICO DE CUIDADOS
                if (planta.getCuidados() != null) {
                    logger.info("📋 Número de cuidados: {}", planta.getCuidados().size());

                    if (!planta.getCuidados().isEmpty()) {
                        for (int i = 0; i < planta.getCuidados().size(); i++) {
                            var cuidado = planta.getCuidados().get(i);
                            logger.info("🌿 Cuidado {}: {} - {}", (i+1), cuidado.getTipo(), cuidado.getDescripcion());
                        }
                    } else {
                        logger.warn("⚠️ La lista de cuidados está vacía");
                    }
                } else {
                    logger.warn("❌ La lista de cuidados es NULL");
                }

                return ResponseEntity.ok(planta);
            } else {
                logger.warn("🚫 Planta no encontrada ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("❌ Error en debug cuidados: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    /**
     * Obtiene una planta por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerPorId(@PathVariable String id) {
        try {
            logger.info("🔍 Controlador: Buscando planta con ID: {}", id);
            Optional<Planta> planta = servicioPlanta.obtenerPorId(id);

            if (planta.isPresent()) {
                Planta p = planta.get();

                // AGREGAR ESTOS LOGS PARA DEBUG
                logger.info("🌱 Planta encontrada: {}", p.getNombreComun());
                if (p.getCuidados() != null) {
                    logger.info("📋 Cuidados cargados: {}", p.getCuidados().size());
                } else {
                    logger.warn("❌ Cuidados es NULL");
                }

                return ResponseEntity.ok(p);
            } else {
                logger.warn("🚫 Planta no encontrada con ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("❌ Error al buscar planta: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error al buscar planta: " + e.getMessage());
        }
    }

    /**
     * Guarda una planta en el sistema.
     */
    @PostMapping
    public ResponseEntity<PlantaResponseDTO> guardar(@RequestBody PlantaRequestDTO plantaDto) {
        try {
            logger.info("💾 Controlador: Guardando planta: {}", plantaDto.getNombreComun());
            Planta planta = PlantaMapper.dtoADominio(plantaDto);
            Planta plantaGuardada = servicioPlanta.guardar(planta);
            PlantaResponseDTO response = PlantaMapper.dominioADto(plantaGuardada);

            logger.info("✅ Planta guardada exitosamente con ID: {}", plantaGuardada.getId());
            return ResponseEntity.status(201).body(response); // 201 CREATED
        } catch (Exception e) {
            logger.error("❌ Error al guardar planta: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Elimina una planta por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            logger.info("🗑️ Controlador: Eliminando planta con ID: {}", id);
            servicioPlanta.eliminar(id);
            logger.info("✅ Planta eliminada exitosamente: {}", id);
            return ResponseEntity.ok("Planta eliminada exitosamente");
        } catch (Exception e) {
            logger.error("❌ Error al eliminar planta: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error al eliminar planta: " + e.getMessage());
        }
    }

    /**
     * Busca plantas por tipo.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Planta>> buscarPorTipo(@RequestParam String tipo) {
        try {
            logger.info("🔍 Controlador: Buscando plantas de tipo: {}", tipo);
            List<Planta> plantas = servicioPlanta.buscarPorNombre(tipo, "global");
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            logger.error("❌ Error al buscar por tipo: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(List.of());
        }
    }


    /**
     * Endpoint de prueba para verificar conectividad con MongoDB
     * 🌱 ¡Usa este endpoint para probar tu conexión!
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> response = new HashMap<>();

        try {
            logger.info("🧪 Test: Probando conexión a MongoDB...");

            List<Planta> plantas = servicioPlanta.obtenerTodas();

            response.put(STATUS, "✅ Conexión MongoDB exitosa");
            response.put(TOTAL_PLANTAS, plantas.size());
            response.put(MESSAGE, "¡El controlador de plantas funciona! 🌱");

            if (!plantas.isEmpty()) {
                response.put("primeraPlanta", plantas.get(0));
                response.put("plantasEncontradas", plantas.stream()
                        .map(p -> p.getNombreComun() + " (ID: " + p.getId() + ")")
                        .toList());
            } else {
                response.put("info", "No hay plantas en la base de datos");
            }

            logger.info("✅ Test exitoso: {} plantas encontradas", plantas.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Test fallido: {}", e.getMessage(), e);

            response.put(STATUS, "❌ Error de conexión");
            response.put(MESSAGE, "Problema al conectar con MongoDB");
            response.put("ayuda", "Verifica que MongoDB esté corriendo y la configuración sea correcta");

            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/test2")
    public String test2() {
        return "index.html";
    }

    /**
     * Endpoint adicional para debugging - muestra información detallada
     */
    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> debug() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Planta> plantas = servicioPlanta.obtenerTodas();

            response.put(TOTAL_PLANTAS, plantas.size());
            response.put("plantas", plantas);
            response.put("configuracion", "MongoDB activo");
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("stackTrace", e.getStackTrace());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Muestra la vista del catálogo web
     * 🌱 ¡Esta es la ruta que necesitas para mostrar la página!
     */
    @GetMapping("/catalogo")
    public String mostrarCatalogo() {
        try {
            logger.info("🌱 Mostrando página del catálogo");
            // Opcional: precargar el total de plantas para mostrar en la vista
            List<Planta> plantas = servicioPlanta.obtenerTodas();
            logger.info("✅ Catálogo cargado con {} plantas disponibles", plantas.size());
            // Retorna el template catalogo.html desde templates/
            return "login/catalogo";

        } catch (Exception e) {
            logger.error("❌ Error al cargar catálogo: {}", e.getMessage(), e);
            return "error"; // o redirect a una página de error
        }
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard() {
        logger.info("🌱 Mostrando dashboard");
        return "login/dashboard";  // Sin .html
    }

    @GetMapping("/dashboard2")
    public String mostrarDashboard2() {
        logger.info("🌱 Mostrando segundo dashboard (dashboard2)");
        // Asumiendo que dashboard2.html está en src/main/resources/templates/login/
        return "login/dashboard2";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        logger.info("🌱 Mostrando formulario de registro de planta");
        // Asegúrate de que registro-planta.html esté en templates/login/
        return "login/registro-planta";
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put(STATUS, "UP");
        response.put("service", "PlantaController/Catalogo");
        logger.info("✅ Health check solicitado por el frontend: UP");
        return ResponseEntity.ok(response);
    }

    /**
     * Registra una planta personal para un usuario específico
     */
    @PostMapping("/registro-personal")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrarPlantaPersonal(@RequestBody Map<String, Object> datos) {
        Map<String, Object> response = new HashMap<>();

        try {
            logger.info("🌱 Registrando planta personal: {}", datos);

            // Extraer datos del request
            String plantaId = (String) datos.get("plantaId");
            String apodo = (String) datos.get(APODO);
            String ubicacion = (String) datos.get(UBICACION);

            // Validaciones
            if (plantaId == null || plantaId.isBlank()) {
                response.put(SUCCESS, false);
                response.put(MESSAGE, "El ID de la planta es requerido");
                return ResponseEntity.badRequest().body(response);
            }

            if (apodo == null || apodo.isBlank()) {
                response.put(SUCCESS, false);
                response.put(MESSAGE, "El apodo es requerido");
                return ResponseEntity.badRequest().body(response);
            }

            // Buscar la planta del catálogo
            Optional<Planta> plantaCatalogoOpt = servicioPlanta.obtenerPorId(plantaId);

            if (!plantaCatalogoOpt.isPresent()) {
                response.put(SUCCESS, false);
                response.put(MESSAGE, "Planta no encontrada en el catálogo");
                return ResponseEntity.notFound().build();
            }

            Planta plantaCatalogo = plantaCatalogoOpt.get();

            // Crear copia personalizada
            Planta plantaPersonal = new Planta(
                    plantaCatalogo.getNombreComun(),
                    plantaCatalogo.getNombreCientifico(),
                    plantaCatalogo.getDescripcion(),
                    plantaCatalogo.getImagenURL()
            );

            // Copiar etiquetas y cuidados
            if (plantaCatalogo.getEtiquetas() != null) {
                plantaCatalogo.getEtiquetas().forEach(plantaPersonal::agregarEtiqueta);
            }

            if (plantaCatalogo.getCuidados() != null) {
                plantaCatalogo.getCuidados().forEach(plantaPersonal::agregarCuidado);
            }

            // Guardar
            Planta plantaGuardada = servicioPlanta.guardar(plantaPersonal);

            response.put(SUCCESS, true);
            response.put(MESSAGE, "Planta registrada exitosamente");
            response.put("data", Map.of(
                    "id", plantaGuardada.getId(),
                    "nombreComun", plantaGuardada.getNombreComun(),
                    APODO, apodo,
                    UBICACION, ubicacion
            ));

            logger.info("✅ Planta personal registrada con ID: {}", plantaGuardada.getId());
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            logger.error("❌ Error: {}", e.getMessage(), e);
            response.put(SUCCESS, false);
            response.put(MESSAGE, "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Obtiene plantas del usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerPlantasUsuario(@PathVariable String usuarioId) {
        try {
            logger.info("👤 Obteniendo plantas del usuario: {}", usuarioId);

            // Por ahora, todas las plantas (luego filtrarás por usuario)
            List<Planta> plantas = servicioPlanta.obtenerTodas();

            // Convertir a formato del frontend
            List<Map<String, Object>> plantasUsuario = plantas.stream()
                    .map(p -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("id", p.getId());
                        m.put("nombreComun", p.getNombreComun());
                        m.put("nombreCientifico", p.getNombreCientifico());
                        m.put("descripcion", p.getDescripcion());
                        m.put("imagenURL", p.getImagenURL());
                        m.put(APODO, p.getNombreComun());
                        m.put(UBICACION, "Casa");
                        m.put("estado", ESTADO_SALUDABLE);
                        m.put("fechaRegistro", new java.util.Date());
                        return m;
                    })
                    .toList();

            return ResponseEntity.ok(plantasUsuario);

        } catch (Exception e) {
            logger.error("❌ Error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(List.of());
        }
    }

    @GetMapping("/mis-plantas")
    public String mostrarMisPlantas() {
        logger.info("🌱 Mostrando página 'Mis Plantas' del usuario");
        // Retorna el template mis-plantas.html desde templates/login/
        return "login/mis-plantas";
    }

    @GetMapping("/usuario/{usuarioId}/mis-plantas")
    @ResponseBody
    public ResponseEntity<List<Planta>> obtenerMisPlantas(@PathVariable String usuarioId) {
        logger.info("👤 Obteniendo plantas personales del usuario {}", usuarioId);
        List<Planta> plantasUsuario = servicioPlanta.buscarPorUsuario(Long.valueOf(usuarioId));
        return ResponseEntity.ok(plantasUsuario);
    }

    @GetMapping("/estadisticas/{usuarioId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@PathVariable String usuarioId) {
        logger.info("📊 Calculando estadísticas para el usuario {}", usuarioId);
        List<Planta> plantas = servicioPlanta.buscarPorUsuario(Long.valueOf(usuarioId));

        long total = plantas.size();
        long saludables = plantas.stream().filter(p -> ESTADO_SALUDABLE.equalsIgnoreCase(p.getEstado())).count();
        long necesitanAtencion = plantas.stream()
                .filter(p -> !ESTADO_SALUDABLE.equalsIgnoreCase(p.getEstado()))
                .count();

        Map<String, Object> stats = Map.of(
                TOTAL_PLANTAS, total,
                "plantasSaludables", saludables,
                "plantasNecesitanAtencion", necesitanAtencion
        );

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/editar")
    public String mostrarEditarPlanta() {
        logger.info("✏️ Mostrando página 'Editar Planta'");
        return "login/editar-planta"; // ruta dentro de templates/login/
    }

    @GetMapping("/vista")
    public String mostrarVistaPlanta() {
        logger.info("🌿 Mostrando página 'Vista de Planta'");
        return "login/vista-planta";
    }
}