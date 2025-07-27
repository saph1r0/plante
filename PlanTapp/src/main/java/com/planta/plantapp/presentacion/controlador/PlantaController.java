package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestionar las operaciones relacionadas con las plantas.
 */
@RestController
@RequestMapping("/api/plantas")
public class PlantaController {

    @Autowired
    private IServicioPlanta servicioPlanta;

    public PlantaController() {
        // Constructor por defecto
    }

    /**
     * Obtiene la lista de todas las plantas.
     * 🌱 ¡Aquí es donde verás tus plantitas de MongoDB!
     */
    @GetMapping
    public ResponseEntity<List<Planta>> obtenerTodas() {
        try {
            System.out.println("🌍 Controlador: Obteniendo todas las plantas...");
            List<Planta> plantas = servicioPlanta.obtenerTodas();
            System.out.println("✅ Controlador: " + plantas.size() + " plantas obtenidas exitosamente");
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            System.err.println("❌ Error en controlador: " + e.getMessage());
            e.printStackTrace();
            // Devolver lista vacía en caso de error para no romper la app
            return ResponseEntity.ok(List.of());
        }
    }

    /**
     * Debug específico para verificar cuidados
     */
    @GetMapping("/debug-cuidados/{id}")
    public ResponseEntity<?> debugCuidados(@PathVariable String id) {
        try {
            System.out.println("🔍 DEBUG CUIDADOS: Buscando planta con ID: " + id);
            Optional<Planta> plantaOpt = servicioPlanta.obtenerPorId(id);

            if (plantaOpt.isPresent()) {
                Planta planta = plantaOpt.get();

                System.out.println("✅ Planta encontrada: " + planta.getNombreComun());
                System.out.println("🌱 ID de la planta: " + planta.getId());

                // DEBUG ESPECÍFICO DE CUIDADOS
                if (planta.getCuidados() != null) {
                    System.out.println("📋 Número de cuidados: " + planta.getCuidados().size());

                    if (!planta.getCuidados().isEmpty()) {
                        for (int i = 0; i < planta.getCuidados().size(); i++) {
                            var cuidado = planta.getCuidados().get(i);
                            System.out.println("🌿 Cuidado " + (i+1) + ": " + cuidado.getTipo() + " - " + cuidado.getDescripcion());
                        }
                    } else {
                        System.out.println("⚠️ La lista de cuidados está vacía");
                    }
                } else {
                    System.out.println("❌ La lista de cuidados es NULL");
                }

                return ResponseEntity.ok(planta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ Error en debug cuidados: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    /**
     * Obtiene una planta por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {
        try {
            System.out.println("🔍 Controlador: Buscando planta con ID: " + id);
            Optional<Planta> planta = servicioPlanta.obtenerPorId(id);

            if (planta.isPresent()) {
                Planta p = planta.get();

                // AGREGAR ESTOS LOGS PARA DEBUG
                System.out.println("🌱 Planta encontrada: " + p.getNombreComun());
                if (p.getCuidados() != null) {
                    System.out.println("📋 Cuidados cargados: " + p.getCuidados().size());
                } else {
                    System.out.println("❌ Cuidados es NULL");
                }

                return ResponseEntity.ok(p);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ Error al buscar planta: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al buscar planta: " + e.getMessage());
        }
    }

    /**
     * Guarda una planta en el sistema.
     */
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Planta planta) {
        try {
            System.out.println("💾 Controlador: Guardando planta: " + planta.getNombreComun());
            Planta plantaGuardada = servicioPlanta.guardar(planta);
            return ResponseEntity.ok(plantaGuardada);
        } catch (Exception e) {
            System.err.println("❌ Error al guardar planta: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al guardar planta: " + e.getMessage());
        }
    }

    /**
     * Elimina una planta por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            System.out.println("🗑️ Controlador: Eliminando planta con ID: " + id);
            servicioPlanta.eliminar(id);
            return ResponseEntity.ok("Planta eliminada exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar planta: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al eliminar planta: " + e.getMessage());
        }
    }

    /**
     * Busca plantas por tipo.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Planta>> buscarPorTipo(@RequestParam String tipo) {
        try {
            System.out.println("🔍 Controlador: Buscando plantas de tipo: " + tipo);
            List<Planta> plantas = servicioPlanta.buscarPorTipo(tipo);
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            System.err.println("❌ Error al buscar por tipo: " + e.getMessage());
            return ResponseEntity.ok(List.of());
        }
    }

    /**
     * Lista las plantas asociadas a un usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Planta>> listarPorUsuario(@PathVariable Long usuarioId) {
        try {
            System.out.println("👤 Controlador: Buscando plantas del usuario: " + usuarioId);
            List<Planta> plantas = servicioPlanta.buscarPorUsuario(usuarioId);
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            System.err.println("❌ Error al buscar por usuario: " + e.getMessage());
            return ResponseEntity.ok(List.of());
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
            System.out.println("🧪 Test: Probando conexión a MongoDB...");

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

            System.out.println("✅ Test exitoso: " + plantas.size() + " plantas encontradas");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Test fallido: " + e.getMessage());
            e.printStackTrace();

            response.put("status", "❌ Error de conexión");
            response.put("error", e.getMessage());
            response.put("mensaje", "Problema al conectar con MongoDB");
            response.put("ayuda", "Verifica que MongoDB esté corriendo y la configuración sea correcta");

            return ResponseEntity.status(500).body(response);
        }
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