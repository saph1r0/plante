package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/web/registros")
@CrossOrigin(origins = "*")
public class RegistroPlantaController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroPlantaController.class);

    // 🔹 Claves de JSON / Map
    private static final String KEY_ID = "id";
    private static final String KEY_USUARIO_ID = "usuarioId";
    private static final String KEY_PLANTA_ID = "plantaId";
    private static final String KEY_APODO = "apodo";
    private static final String KEY_UBICACION = "ubicacion";
    private static final String KEY_ESTADO = "estado";
    private static final String KEY_FECHA_REGISTRO = "fechaRegistro";
    private static final String KEY_FOTO_PERSONAL = "fotoPersonal";
    private static final String KEY_NOTAS = "notas";
    private static final String KEY_PLANTA = "planta";
    private static final String KEY_ERROR = "error";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_MESSAGE = "message";

    // 🔹 Otros literales
    private static final String ESTADO_SALUDABLE = "SALUDABLE";
    private static final String TIPO_INTERIOR = "interior";

    private final IServicioRegistroPlanta servicio;
    private final IServicioPlanta servicioPlanta;

    public RegistroPlantaController(IServicioRegistroPlanta servicio, IServicioPlanta servicioPlanta) {
        this.servicio = servicio;
        this.servicioPlanta = servicioPlanta;
    }

    @PostMapping
    public ResponseEntity<RegistroPlantaDocumento> registrar(@RequestBody Map<String, Object> datos) {
        try {
            String usuarioId = (String) datos.get(KEY_USUARIO_ID);
            String plantaId = (String) datos.get(KEY_PLANTA_ID);
            String apodo = (String) datos.get(KEY_APODO);
            String ubicacion = (String) datos.get(KEY_UBICACION);
            String estado = (String) datos.get(KEY_ESTADO);
            String fotoPersonal = (String) datos.get(KEY_FOTO_PERSONAL);

            logger.info(" Registrando planta - Usuario: {}, PlantaId: {}, Apodo: {}", usuarioId, plantaId, apodo);

            RegistroPlantaDocumento registro = new RegistroPlantaDocumento(
                    usuarioId,
                    plantaId,
                    apodo,
                    ubicacion,
                    estado != null
                            ? Enum.valueOf(com.planta.plantapp.dominio.modelo.planta.EstadoPlanta.class, estado)
                            : null
            );

            if (fotoPersonal != null && !fotoPersonal.isBlank()) {
                registro.setFotoPersonal(fotoPersonal);
            }

            RegistroPlantaDocumento guardado = servicio.guardar(registro);
            logger.info(" Registro guardado con ID: {}", guardado.getId());
            return ResponseEntity.status(201).body(guardado);

        } catch (Exception e) {
            logger.error(" Error al registrar planta: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable String id) {
        RegistroPlantaDocumento registro = servicio.obtenerPorId(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> registroConPlanta = new HashMap<>();
        registroConPlanta.put(KEY_ID, registro.getId());
        registroConPlanta.put(KEY_USUARIO_ID, registro.getUsuarioId());
        registroConPlanta.put(KEY_PLANTA_ID, registro.getPlantaId());
        registroConPlanta.put(KEY_APODO, registro.getApodo());
        registroConPlanta.put(KEY_UBICACION, registro.getUbicacion());
        registroConPlanta.put(KEY_ESTADO, registro.getEstado());
        registroConPlanta.put(KEY_FECHA_REGISTRO, registro.getFechaRegistro());
        registroConPlanta.put(KEY_FOTO_PERSONAL, registro.getFotoPersonal());
        registroConPlanta.put(KEY_NOTAS, registro.getNotas());
        registroConPlanta.put(KEY_PLANTA, obtenerPlantaCatalogo(registro.getPlantaId()));

        return ResponseEntity.ok(registroConPlanta);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Map<String, Object>>> listarPorUsuario(@PathVariable String usuarioId) {
        try {
            logger.info("Listando registros del usuario: {}", usuarioId);
            List<RegistroPlantaDocumento> registros = servicio.listarPorUsuario(usuarioId);

            List<Map<String, Object>> registrosEnriquecidos = registros.stream()
                    .map(registro -> {
                        Map<String, Object> registroConPlanta = new HashMap<>();
                        registroConPlanta.put(KEY_ID, registro.getId());
                        registroConPlanta.put(KEY_USUARIO_ID, registro.getUsuarioId());
                        registroConPlanta.put(KEY_PLANTA_ID, registro.getPlantaId());
                        registroConPlanta.put(KEY_APODO, registro.getApodo());
                        registroConPlanta.put(KEY_UBICACION, registro.getUbicacion());
                        registroConPlanta.put(KEY_ESTADO, registro.getEstado().name());
                        registroConPlanta.put(KEY_FECHA_REGISTRO, registro.getFechaRegistro());
                        registroConPlanta.put(KEY_FOTO_PERSONAL, registro.getFotoPersonal());
                        registroConPlanta.put(KEY_PLANTA, obtenerPlantaCatalogo(registro.getPlantaId()));
                        return registroConPlanta;
                    })
                    .toList(); //  en vez de collect(Collectors.toList())

            logger.info(" Retornando {} registros enriquecidos", registrosEnriquecidos.size());
            return ResponseEntity.ok(registrosEnriquecidos);

        } catch (Exception e) {
            logger.error(" Error al listar registros: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // =======================
    //   Helper de catálogo
    // =======================
    private Map<String, Object> obtenerPlantaCatalogo(String plantaId) {
        try {
            Optional<Planta> plantaOpt = servicioPlanta.obtenerPorId(plantaId);

            if (plantaOpt.isEmpty()) {
                logger.warn("Planta no encontrada en catálogo: {}", plantaId);
                return Collections.emptyMap(); //  en vez de null
            }

            Planta planta = plantaOpt.get();
            Map<String, Object> plantaMap = new HashMap<>();
            plantaMap.put(KEY_ID, planta.getId());
            plantaMap.put("nombreComun", planta.getNombreComun());
            plantaMap.put("nombreCientifico", planta.getNombreCientifico());
            plantaMap.put("descripcion", planta.getDescripcion() != null ? planta.getDescripcion() : "");
            plantaMap.put("imagenUrl", planta.getImagenURL() != null ? planta.getImagenURL() : "");

            String tipo = TIPO_INTERIOR;

            if (planta.getEtiquetas() != null && !planta.getEtiquetas().isEmpty()) {
                tipo = planta.getEtiquetas().stream()
                        .filter(e -> e.getNombre().equalsIgnoreCase(TIPO_INTERIOR) ||
                                e.getNombre().equalsIgnoreCase("exterior"))
                        .map(e -> e.getNombre().toLowerCase())
                        .findFirst()
                        .orElse(TIPO_INTERIOR);
            }
            plantaMap.put("tipo", tipo);

            return plantaMap;

        } catch (Exception e) {
            logger.warn("Error al obtener planta del catálogo: {} - {}", plantaId, e.getMessage());
            return Collections.emptyMap(); //  también vacío aquí
        }
    }

    @GetMapping("/estadisticas/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@PathVariable String usuarioId) {
        try {
            logger.info("📊 Calculando estadísticas para el usuario {}", usuarioId);
            List<RegistroPlantaDocumento> registros = servicio.listarPorUsuario(usuarioId);

            long total = registros.size();
            long saludables = registros.stream()
                    .filter(r -> r.getEstado() != null
                            && ESTADO_SALUDABLE.equalsIgnoreCase(r.getEstado().name()))
                    .count();
            long necesitanAtencion = total - saludables;

            Map<String, Object> stats = Map.of(
                    "totalPlantas", total,
                    "plantasSaludables", saludables,
                    "plantasNecesitanAtencion", necesitanAtencion
            );

            logger.info("Estadísticas generadas: {}", stats);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Error al calcular estadísticas: {}", e.getMessage(), e);
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
                String estadoStr = (String) datos.get(KEY_ESTADO);
                existente.setEstado(Enum.valueOf(
                        com.planta.plantapp.dominio.modelo.planta.EstadoPlanta.class,
                        estadoStr
                ));
            }
            if (datos.containsKey(KEY_FOTO_PERSONAL)) {
                existente.setFotoPersonal((String) datos.get(KEY_FOTO_PERSONAL));
            }
            if (datos.containsKey(KEY_NOTAS)) {
                existente.setNotas((String) datos.get(KEY_NOTAS));
            }

            RegistroPlantaDocumento actualizado = servicio.guardar(existente);
            logger.info(" Planta actualizada correctamente");
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            logger.error(" Error al actualizar planta: {}", e.getMessage(), e);
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
            logger.error(" Error al eliminar planta: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put(KEY_SUCCESS, false);
            error.put(KEY_MESSAGE, "Error al eliminar planta: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
