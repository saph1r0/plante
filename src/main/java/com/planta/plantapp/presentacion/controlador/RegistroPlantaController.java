package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.aplicacion.servicios.RegistroPlantaFacade;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/registros")
@CrossOrigin(origins = "*")
public class RegistroPlantaController {

    private static final Logger logger =
            LoggerFactory.getLogger(RegistroPlantaController.class);

    private static final String KEY_USUARIO_ID = "usuarioId";
    private static final String KEY_PLANTA_ID = "plantaId";
    private static final String KEY_APODO = "apodo";
    private static final String KEY_UBICACION = "ubicacion";
    private static final String KEY_ESTADO = "estado";
    private static final String KEY_FOTO_PERSONAL = "fotoPersonal";
    private static final String KEY_NOTAS = "notas";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ERROR = "error";

    private static final String ESTADO_SALUDABLE = "SALUDABLE";

    private final IServicioRegistroPlanta servicio;
    private final RegistroPlantaFacade facade;

    public RegistroPlantaController(IServicioRegistroPlanta servicio,
                                    RegistroPlantaFacade facade) {
        this.servicio = servicio;
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<RegistroPlantaDocumento> registrar(
            @RequestBody Map<String, Object> datos) {

        try {
            String usuarioId = (String) datos.get(KEY_USUARIO_ID);
            String plantaId  = (String) datos.get(KEY_PLANTA_ID);
            String apodo     = (String) datos.get(KEY_APODO);
            String ubicacion = (String) datos.get(KEY_UBICACION);
            String estado    = (String) datos.get(KEY_ESTADO);

            RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                    usuarioId,
                    plantaId,
                    apodo,
                    ubicacion,
                    estado != null
                            ? Enum.valueOf(
                            com.planta.plantapp.dominio.modelo.planta.EstadoPlanta.class,
                            estado)
                            : null
            );

            if (datos.containsKey(KEY_FOTO_PERSONAL)) {
                registro.setFotoPersonal((String) datos.get(KEY_FOTO_PERSONAL));
            }
            if (datos.containsKey(KEY_NOTAS)) {
                registro.setNotas((String) datos.get(KEY_NOTAS));
            }

            RegistroPlantaDocumento guardado = servicio.guardar(registro);
            return ResponseEntity.status(201).body(guardado);

        } catch (Exception e) {
            logger.error("Error al registrar planta", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPlantaResponseDTO> obtenerPorId(
            @PathVariable String id) {

        RegistroPlantaResponseDTO dto = facade.obtenerPorId(id);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroPlantaResponseDTO>> listarPorUsuario(
            @PathVariable String usuarioId) {

        return ResponseEntity.ok(facade.listarPorUsuario(usuarioId));
    }

    @GetMapping("/estadisticas/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(
            @PathVariable String usuarioId) {
        try {
            logger.info("📊 Calculando estadísticas para el usuario {}", usuarioId);
            List<RegistroPlantaDocumento> registros = servicio.listarPorUsuario(usuarioId);

            long total = registros.size();
            long saludables = registros.stream()
                    .filter(r -> r.getEstado() != null
                            && ESTADO_SALUDABLE.equalsIgnoreCase(r.getEstado().name()))
                    .count();

            Map<String, Object> stats = Map.of(
                    "totalPlantas", total,
                    "plantasSaludables", saludables,
                    "plantasNecesitanAtencion", total - saludables
            );

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("Error al calcular estadísticas", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of(KEY_ERROR, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroPlantaDocumento> actualizar(
            @PathVariable String id,
            @RequestBody Map<String, Object> datos) {
        try {
            logger.info("Actualizando planta con ID: {}", id);
            RegistroPlantaDocumento existente = servicio.obtenerPorId(id);
            if (existente == null) {
                logger.warn("No se encontró la planta con ID: {}", id);
                return ResponseEntity.notFound().build();
            }

            if (datos.containsKey(KEY_APODO)) {
                existente.setApodo((String) datos.get(KEY_APODO));
            }
            if (datos.containsKey(KEY_UBICACION)) {
                existente.setUbicacion((String) datos.get(KEY_UBICACION));
            }
            if (datos.containsKey(KEY_ESTADO)) {
                existente.setEstado(Enum.valueOf(
                        com.planta.plantapp.dominio.modelo.planta.EstadoPlanta.class,
                        (String) datos.get(KEY_ESTADO)));
            }
            if (datos.containsKey(KEY_FOTO_PERSONAL)) {
                existente.setFotoPersonal((String) datos.get(KEY_FOTO_PERSONAL));
            }
            if (datos.containsKey(KEY_NOTAS)) {
                existente.setNotas((String) datos.get(KEY_NOTAS));
            }

            return ResponseEntity.ok(servicio.guardar(existente));

        } catch (Exception e) {
            logger.error("Error al actualizar planta", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable String id) {
        try {
            servicio.eliminar(id);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put(KEY_SUCCESS, true);
            respuesta.put(KEY_MESSAGE, "Planta eliminada exitosamente");
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            logger.error("Error al eliminar planta", e);
            Map<String, Object> error = new HashMap<>();
            error.put(KEY_SUCCESS, false);
            error.put(KEY_MESSAGE, "Error al eliminar planta: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
