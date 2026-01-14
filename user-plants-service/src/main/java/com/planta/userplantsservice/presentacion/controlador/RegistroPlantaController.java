package com.planta.userplantsservice.presentacion.controlador;

import com.planta.userplantsservice.aplicacion.servicios.RegistroPlantaFacade;
import com.planta.userplantsservice.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.userplantsservice.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registros")
public class RegistroPlantaController {

    private final RegistroPlantaFacade facade;

    public RegistroPlantaController(RegistroPlantaFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<RegistroPlantaResponseDTO> registrar(
            @RequestBody RegistroPlantaRequestDTO datos
    ) {
        String usuarioId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (datos.getPlantaId() == null || datos.getPlantaId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201)
                .body(facade.registrar(datos, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroPlantaResponseDTO> actualizar(
            @PathVariable String id,
            @RequestBody RegistroPlantaRequestDTO datos
    ) {
        RegistroPlantaResponseDTO actualizado = facade.actualizar(id, datos);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String id) {
        facade.eliminar(id);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Planta eliminada exitosamente"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPlantaResponseDTO> obtenerPorId(@PathVariable String id) {
        RegistroPlantaResponseDTO dto = facade.obtenerPorId(id);
        return (dto == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(dto);
    }

    @GetMapping("/mis-plantas")
    public ResponseEntity<List<RegistroPlantaResponseDTO>> listarMisPlantas() {
        String usuarioId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(facade.listarPorUsuario(usuarioId));
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        String usuarioId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(facade.obtenerEstadisticas(usuarioId));
    }
}