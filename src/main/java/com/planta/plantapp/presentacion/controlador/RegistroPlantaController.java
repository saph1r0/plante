package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.servicios.RegistroPlantaFacade;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaRequestDTO; // 👈 Importación vital
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/registros")
@CrossOrigin(origins = "*")
public class RegistroPlantaController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroPlantaController.class);
    private final RegistroPlantaFacade facade;

    public RegistroPlantaController(RegistroPlantaFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<RegistroPlantaResponseDTO> registrar(@RequestBody RegistroPlantaRequestDTO datos) {
        // Obtenemos el ID del usuario del contexto de seguridad (JWT)
        String usuarioId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (datos.getPlantaId() == null || datos.getPlantaId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(facade.registrar(datos, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroPlantaResponseDTO> actualizar(@PathVariable String id, @RequestBody RegistroPlantaRequestDTO datos) {
        // Delegamos TODA la lógica al facade usando el DTO
        RegistroPlantaResponseDTO actualizado = facade.actualizar(id, datos);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String id) {
        try {
            facade.eliminar(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Planta eliminada exitosamente"
            ));
        } catch (Exception e) {
            logger.error("Error al eliminar", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Error: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPlantaResponseDTO> obtenerPorId(@PathVariable String id) {
        RegistroPlantaResponseDTO dto = facade.obtenerPorId(id);
        return (dto == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroPlantaResponseDTO>> listarPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(facade.listarPorUsuario(usuarioId));
    }

    @GetMapping("/estadisticas/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@PathVariable String usuarioId) {
        return ResponseEntity.ok(facade.obtenerEstadisticas(usuarioId));
    }
}