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
@RequestMapping("/api/plantas")
public class PlantaController {

    private static final Logger logger = LoggerFactory.getLogger(PlantaController.class);
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
            List<Planta> plantas = servicioPlanta.buscarPorTipo(tipo);
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            logger.error("❌ Error al buscar por tipo: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(List.of());
        }
    }

    /**
     * Lista las plantas asociadas a un usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Planta>> listarPorUsuario(@PathVariable Long usuarioId) {
        try {
            logger.info("👤 Controlador: Buscando plantas del usuario: {}", usuarioId);
            List<Planta> plantas = servicioPlanta.buscarPorUsuario(usuarioId);
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            logger.error("❌ Error al buscar por usuario: {}", e.getMessage(), e);
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

            response.put("status", "✅ Conexión MongoDB exitosa");
            response.put("totalPlantas", plantas.size());
            response.put("mensaje", "¡El controlador de plantas funciona! 🌱");

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
            e.printStackTrace();

            response.put("status", "❌ Error de conexión");
            response.put("error", e.getMessage());
            response.put("mensaje", "Problema al conectar con MongoDB");
            response.put("ayuda", "Verifica que MongoDB esté corriendo y la configuración sea correcta");

            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/test2")
    public String test2() {
        return "redirect:/index.html";
    }

    /**
     * Endpoint adicional para debugging - muestra información detallada
     */
    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> debug() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Planta> plantas = servicioPlanta.obtenerTodas();

            response.put("totalPlantas", plantas.size());
            response.put("plantas", plantas);
            response.put("configuracion", "MongoDB activo");
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(response);

        } catch (Exception e) {response.put("error", e.getMessage());
            response.put("stackTrace", e.getStackTrace());
            return ResponseEntity.status(500).body(response);
        }
    }
}