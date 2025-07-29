package com.planta.demo.presentacion.controlador;

import com.planta.demo.aplicacion.servicios.ServicioPlantaImpl;
import com.planta.demo.aplicacion.servicios.CatalogoService;
import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.planta.RegistroPlanta;
import com.planta.demo.dominio.modelo.planta.EstadoPlanta;
import com.planta.demo.infraestructura.repositorio.mongodb.PlantaRepositorioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.time.LocalDateTime;

/**
 * Controlador REST completo para gestión de plantas
 * Versión final con MongoDB - PlantCare Backend
 *
 * @author PlantCare Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/plantas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlantaController {

    @Autowired
    private ServicioPlantaImpl servicioPlanta;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private PlantaRepositorioImpl plantaRepositorio;

    // ========================================
    // ENDPOINTS PARA CATÁLOGO DE PLANTAS
    // ========================================

    @GetMapping("/catalogo")
    public ResponseEntity<List<Planta>> obtenerCatalogoCompleto() {
        try {
            List<Planta> catalogo = servicioPlanta.obtenerTodas();
            System.out.println("Catálogo solicitado: " + catalogo.size() + " plantas disponibles");
            catalogo.forEach(p -> System.out.println("  - " + p.getNombreComun() + " (" + p.getTipo() + ")"));
            return ResponseEntity.ok(catalogo);
        } catch (Exception e) {
            System.err.println("Error obteniendo catálogo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", "Error interno del servidor")
                    .build();
        }
    }

    @GetMapping("/catalogo/buscar")
    public ResponseEntity<List<Planta>> buscarEnCatalogo(@RequestParam String query) {
        try {
            System.out.println("Búsqueda en catálogo: '" + query + "'");
            if (query == null || query.trim().length() < 2) {
                List<Planta> catalogo = servicioPlanta.obtenerTodas();
                return ResponseEntity.ok(catalogo);
            }
            List<Planta> resultados = catalogoService.buscarPlantas(query.trim());
            System.out.println("Resultados encontrados: " + resultados.size());
            resultados.forEach(p -> 
                System.out.println("  " + p.getNombreComun() + " - " + p.getNombreCientifico())
            );
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            System.err.println("Error en búsqueda de catálogo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .header("Error-Message", "Error en búsqueda: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/catalogo/{plantaId}")
    public ResponseEntity<Planta> obtenerPlantaCatalogo(@PathVariable String plantaId) {
        try {
            System.out.println("Buscando planta del catálogo: " + plantaId);
            Planta planta = plantaRepositorio.obtenerPorId(plantaId);
            if (planta != null) {
                System.out.println("Planta encontrada: " + planta.getNombreComun());
                return ResponseEntity.ok(planta);
            } else {
                System.out.println("Planta no encontrada: " + plantaId);
                return ResponseEntity.notFound()
                        .header("Error-Message", "Planta no encontrada")
                        .build();
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo planta del catálogo: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .header("Error-Message", "Error obteniendo planta: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/catalogo/tipo/{tipo}")
    public ResponseEntity<List<Planta>> obtenerPorTipo(@PathVariable String tipo) {
        try {
            System.out.println("Buscando plantas por tipo: " + tipo);
            List<Planta> plantas = servicioPlanta.buscarPorTipo(tipo);
            System.out.println("Plantas encontradas del tipo '" + tipo + "': " + plantas.size());
            return ResponseEntity.ok(plantas);
        } catch (Exception e) {
            System.err.println("Error obteniendo plantas por tipo: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .header("Error-Message", "Error obteniendo por tipo: " + e.getMessage())
                    .build();
        }
    }

    // ========================================
    // ENDPOINTS PARA REGISTROS PERSONALES
    // ========================================

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarPlanta(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Iniciando registro de planta personal...");
            System.out.println("Datos recibidos: " + request);

            String plantaId = (String) request.get("plantaId");
            String apodo = (String) request.get("apodo");
            String ubicacion = (String) request.get("ubicacion");
            String usuarioId = request.get("usuarioId") != null ? request.get("usuarioId").toString() : null;
            String estado = (String) request.getOrDefault("estado", "SALUDABLE");
            String notas = (String) request.get("notas");
            String fotoPersonal = (String) request.get("fotoPersonal");

            System.out.println("Datos procesados:");
            System.out.println("   - Apodo: '" + apodo + "'");
            System.out.println("   - PlantaID: '" + plantaId + "'");
            System.out.println("   - Usuario: '" + usuarioId + "'");
            System.out.println("   - Ubicación: '" + ubicacion + "'");
            System.out.println("   - Estado: '" + estado + "'");

            if (plantaId == null || plantaId.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Debe seleccionar una planta del catálogo");
                response.put("field", "plantaId");
                return ResponseEntity.badRequest().body(response);
            }
            if (apodo == null || apodo.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "El apodo es obligatorio");
                response.put("field", "apodo");
                return ResponseEntity.badRequest().body(response);
            }
            if (apodo.trim().length() < 2) {
                response.put("success", false);
                response.put("message", "El apodo debe tener al menos 2 caracteres");
                response.put("field", "apodo");
                return ResponseEntity.badRequest().body(response);
            }
            if (ubicacion == null || ubicacion.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "La ubicación es obligatoria");
                response.put("field", "ubicacion");
                return ResponseEntity.badRequest().body(response);
            }
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Usuario ID es requerido");
                response.put("field", "usuarioId");
                return ResponseEntity.badRequest().body(response);
            }
            if (fotoPersonal != null && !fotoPersonal.trim().isEmpty()) {
                if (!fotoPersonal.startsWith("http://") && !fotoPersonal.startsWith("https://")) {
                    response.put("success", false);
                    response.put("message", "La URL de la foto debe ser válida (comenzar con http:// o https://)");
                    response.put("field", "fotoPersonal");
                    return ResponseEntity.badRequest().body(response);
                }
            }

            Planta plantaCatalogo = plantaRepositorio.obtenerPorId(plantaId);
            if (plantaCatalogo == null) {
                System.err.println("Planta no encontrada en catálogo: " + plantaId);
                response.put("success", false);
                response.put("message", "Planta no encontrada en catálogo");
                response.put("field", "plantaId");
                return ResponseEntity.badRequest().body(response);
            }
            System.out.println("Planta encontrada: " + plantaCatalogo.getNombreComun());

            RegistroPlanta registro = new RegistroPlanta(
                apodo.trim(), 
                usuarioId.trim(), 
                plantaCatalogo, 
                ubicacion.trim()
            );
            registro.setPlanta(plantaCatalogo);

            try {
                EstadoPlanta estadoEnum = EstadoPlanta.valueOf(estado.toUpperCase());
                registro.setEstado(estadoEnum);
                System.out.println("Estado establecido: " + estadoEnum);
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido '" + estado + "', usando SALUDABLE por defecto");
                registro.setEstado(EstadoPlanta.SALUDABLE);
            }

            if (notas != null && !notas.trim().isEmpty()) {
                registro.setNotas(notas.trim());
                System.out.println("Notas agregadas");
            }
            if (fotoPersonal != null && !fotoPersonal.trim().isEmpty()) {
                registro.setFotoPersonal(fotoPersonal.trim());
                System.out.println("Foto personal agregada");
            }

            plantaRepositorio.guardarRegistro(registro);
            System.out.println("Planta registrada exitosamente:");
            System.out.println("   - ID de registro: " + registro.getId());
            System.out.println("   - Apodo: " + registro.getApodo());
            System.out.println("   - Planta: " + registro.getNombrePlanta());

            response.put("success", true);
            response.put("message", "Planta registrada correctamente");
            response.put("registroId", registro.getId());
            response.put("apodo", registro.getApodo());
            response.put("nombrePlanta", registro.getNombrePlanta());
            response.put("nombreCientifico", registro.getNombreCientifico());
            response.put("estado", registro.getEstado().toString());
            response.put("ubicacion", registro.getUbicacion());
            response.put("fechaRegistro", registro.getFechaRegistro().toString());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error crítico al registrar planta:");
            System.err.println("   - Mensaje: " + e.getMessage());
            System.err.println("   - Tipo: " + e.getClass().getSimpleName());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error interno al registrar planta: " + e.getMessage());
            response.put("error_type", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroPlanta>> obtenerMisPlantas(@PathVariable String usuarioId) {
        try {
            System.out.println("Obteniendo plantas del usuario: " + usuarioId);
            List<RegistroPlanta> registros = plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
            System.out.println("Plantas encontradas: " + registros.size());

            if (registros.isEmpty()) {
                System.out.println("No se encontraron plantas para el usuario: " + usuarioId);
            } else {
                System.out.println("Detalle de plantas encontradas:");
                registros.forEach(r -> {
                    System.out.println("  - ID: " + r.getId() + 
                                     " | Apodo: '" + r.getApodo() + "'" +
                                     " | Estado: " + r.getEstado());
                    if (r.getPlanta() != null) {
                        System.out.println("    Planta: " + r.getPlanta().getNombreComun() + 
                                         " (" + r.getPlanta().getId() + ")");
                    } else {
                        System.out.println("    Planta es NULL - Problema detectado");
                    }
                });
            }
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            System.err.println("Error obteniendo plantas del usuario: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", "Error obteniendo plantas: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/registro/{registroId}")
    public ResponseEntity<RegistroPlanta> obtenerRegistro(@PathVariable String registroId) {
        try {
            System.out.println("Buscando registro: " + registroId);
            RegistroPlanta registro = plantaRepositorio.obtenerRegistroPorId(registroId);
            if (registro != null) {
                System.out.println("Registro encontrado: " + registro.getApodo());
                return ResponseEntity.ok(registro);
            } else {
                System.out.println("Registro no encontrado: " + registroId);
                return ResponseEntity.notFound()
                        .header("Error-Message", "Registro no encontrado")
                        .build();
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo registro: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .header("Error-Message", "Error obteniendo registro: " + e.getMessage())
                    .build();
        }
    }

    @PutMapping("/registro/{registroId}")
    public ResponseEntity<Map<String, Object>> editarRegistro(
            @PathVariable String registroId,
            @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Editando registro: " + registroId);
            System.out.println("Datos de actualización: " + request);

            RegistroPlanta registro = plantaRepositorio.obtenerRegistroPorId(registroId);
            if (registro == null) {
                response.put("success", false);
                response.put("message", "Registro no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            System.out.println("Registro encontrado: " + registro.getApodo());

            String apodo = (String) request.get("apodo");
            String ubicacion = (String) request.get("ubicacion");
            String estado = (String) request.get("estado");
            String notas = (String) request.get("notas");
            String fotoPersonal = (String) request.get("fotoPersonal");
            boolean cambiosRealizados = false;

            if (apodo != null && !apodo.trim().isEmpty() && !apodo.equals(registro.getApodo())) {
                registro.setApodo(apodo.trim());
                cambiosRealizados = true;
                System.out.println("Apodo actualizado: " + apodo.trim());
            }
            if (ubicacion != null && !ubicacion.equals(registro.getUbicacion())) {
                registro.setUbicacion(ubicacion.trim());
                cambiosRealizados = true;
                System.out.println("Ubicación actualizada: " + ubicacion.trim());
            }
            if (estado != null && !estado.trim().isEmpty()) {
                try {
                    EstadoPlanta nuevoEstado = EstadoPlanta.valueOf(estado.toUpperCase());
                    if (nuevoEstado != registro.getEstado()) {
                        registro.setEstado(nuevoEstado);
                        cambiosRealizados = true;
                        System.out.println("Estado actualizado: " + nuevoEstado);
                    }
                } catch (IllegalArgumentException e) {
                    response.put("success", false);
                    response.put("message", "Estado inválido: " + estado);
                    return ResponseEntity.badRequest().body(response);
                }
            }
            if (notas != null) {
                String notasLimpias = notas.trim().isEmpty() ? null : notas.trim();
                if (!Objects.equals(notasLimpias, registro.getNotas())) {
                    registro.setNotas(notasLimpias);
                    cambiosRealizados = true;
                    System.out.println("Notas actualizadas");
                }
            }
            if (fotoPersonal != null) {
                String fotoLimpia = fotoPersonal.trim().isEmpty() ? null : fotoPersonal.trim();
                if (!Objects.equals(fotoLimpia, registro.getFotoPersonal())) {
                    registro.setFotoPersonal(fotoLimpia);
                    cambiosRealizados = true;
                    System.out.println("Foto personal actualizada");
                }
            }

            if (cambiosRealizados) {
                plantaRepositorio.actualizarRegistro(registro);
                System.out.println("Cambios guardados en MongoDB");
                response.put("success", true);
                response.put("message", "Planta actualizada correctamente");
                response.put("cambios_realizados", true);
            } else {
                response.put("success", true);
                response.put("message", "No se realizaron cambios");
                response.put("cambios_realizados", false);
            }
            response.put("registro", registro);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error actualizando registro: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error al actualizar planta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/registro/{registroId}")
    public ResponseEntity<Map<String, Object>> eliminarRegistro(@PathVariable String registroId) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Eliminando registro: " + registroId);
            RegistroPlanta registro = plantaRepositorio.obtenerRegistroPorId(registroId);
            if (registro == null) {
                response.put("success", false);
                response.put("message", "Registro no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            String nombreEliminado = registro.getApodo();
            plantaRepositorio.eliminarRegistro(registroId);
            System.out.println("Registro eliminado: " + nombreEliminado);
            response.put("success", true);
            response.put("message", "Planta '" + nombreEliminado + "' eliminada correctamente");
            response.put("registro_eliminado", nombreEliminado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error eliminando registro: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error al eliminar planta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ========================================
    // ENDPOINTS DE ESTADÍSTICAS
    // ========================================

    @GetMapping("/estadisticas/{usuarioId}")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@PathVariable String usuarioId) {
        try {
            System.out.println("Calculando estadísticas para usuario: " + usuarioId);
            List<RegistroPlanta> plantas = plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("totalPlantas", plantas.size());

            long saludables = plantas.stream().filter(p -> p.getEstado() == EstadoPlanta.SALUDABLE).count();
            long necesitanAtencion = plantas.stream().filter(RegistroPlanta::necesitaAtencion).count();
            long floreciendo = plantas.stream().filter(p -> p.getEstado() == EstadoPlanta.FLORECIENDO).count();
            long enfermas = plantas.stream().filter(p -> p.getEstado() == EstadoPlanta.ENFERMA).count();

            estadisticas.put("plantasSaludables", saludables);
            estadisticas.put("plantasNecesitanAtencion", necesitanAtencion);
            estadisticas.put("plantasFloreciendo", floreciendo);
            estadisticas.put("plantasEnfermas", enfermas);

            long diasPromedio = plantas.stream()
                    .mapToLong(RegistroPlanta::getDiasDesdeRegistro)
                    .sum() / Math.max(plantas.size(), 1);
            estadisticas.put("diasPromedioRegistro", diasPromedio);
            estadisticas.put("timestamp", LocalDateTime.now().toString());

            System.out.println("Estadísticas calculadas: " + estadisticas);
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", "Error calculando estadísticas")
                    .build();
        }
    }

    @GetMapping("/estadisticas/catalogo")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasCatalogo() {
        try {
            System.out.println("Calculando estadísticas del catálogo...");
            List<Planta> todasPlantas = servicioPlanta.obtenerTodas();
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalPlantas", todasPlantas.size());

            long interior = todasPlantas.stream().filter(p -> "interior".equalsIgnoreCase(p.getTipo())).count();
            long exterior = todasPlantas.stream().filter(p -> "exterior".equalsIgnoreCase(p.getTipo())).count();

            stats.put("plantasInterior", interior);
            stats.put("plantasExterior", exterior);
            stats.put("timestamp", LocalDateTime.now().toString());

            System.out.println("Estadísticas del catálogo: " + stats);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas del catálogo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", "Error en estadísticas de catálogo")
                    .build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/necesitan-atencion")
    public ResponseEntity<List<RegistroPlanta>> obtenerPlantasQueNecesitanAtencion(@PathVariable String usuarioId) {
        try {
            System.out.println("Buscando plantas que necesitan atención para usuario: " + usuarioId);
            List<RegistroPlanta> plantas = plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
            List<RegistroPlanta> necesitanAtencion = plantas.stream()
                    .filter(RegistroPlanta::necesitaAtencion)
                    .toList();
            System.out.println("Plantas que necesitan atención: " + necesitanAtencion.size());
            return ResponseEntity.ok(necesitanAtencion);
        } catch (Exception e) {
            System.err.println("Error obteniendo plantas que necesitan atención: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", "Error obteniendo plantas críticas")
                    .build();
        }
    }

    // ========================================
    // ENDPOINTS ADICIONALES
    // ========================================

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        try {
            Map<String, Object> health = new HashMap<>();
            health.put("status", "UP");
            health.put("service", "PlantCare Backend");
            health.put("version", "1.0.0");
            health.put("timestamp", LocalDateTime.now().toString());

            List<Planta> testCatalogo = servicioPlanta.obtenerTodas();
            health.put("mongodb_connection", "OK");
            health.put("catalogo_plantas", testCatalogo.size());
            System.out.println("Health check OK - MongoDB conectado, " + testCatalogo.size() + " plantas en catálogo");
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            Map<String, Object> health = new HashMap<>();
            health.put("status", "DOWN");
            health.put("error", e.getMessage());
            health.put("timestamp", LocalDateTime.now().toString());
            System.err.println("Health check FAILED: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> informacionAPI() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "PlantCare Backend API");
        info.put("version", "1.0.0");
        info.put("description", "Sistema completo de gestión de plantas con MongoDB");
        info.put("author", "PlantCare Team");
        info.put("timestamp", LocalDateTime.now().toString());

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("GET /catalogo", "Obtener catálogo completo");
        endpoints.put("GET /catalogo/buscar", "Buscar plantas en catálogo");
        endpoints.put("POST /registrar", "Registrar nueva planta personal");
        endpoints.put("GET /usuario/{id}", "Obtener plantas de usuario");
        endpoints.put("PUT /registro/{id}", "Actualizar registro");
        endpoints.put("DELETE /registro/{id}", "Eliminar registro");
        endpoints.put("GET /estadisticas/{userId}", "Estadísticas de usuario");
        endpoints.put("GET /health", "Estado del servicio");

        info.put("endpoints", endpoints);
        return ResponseEntity.ok(info);
    }

    // ========================================
    // MANEJO DE ERRORES GLOBALES
    // ========================================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(IllegalArgumentException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "Error de validación: " + e.getMessage());
        error.put("error_type", "VALIDATION_ERROR");
        error.put("timestamp", LocalDateTime.now().toString());
        System.err.println("Error de validación: " + e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "Error interno del servidor");
        error.put("error_type", e.getClass().getSimpleName());
        error.put("timestamp", LocalDateTime.now().toString());
        if (isDevEnvironment()) {
            error.put("details", e.getMessage());
            error.put("stack_trace", getStackTraceAsString(e));
        }
        System.err.println("Excepción no manejada en PlantaController:");
        System.err.println("   - Tipo: " + e.getClass().getSimpleName());
        System.err.println("   - Mensaje: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameter(MissingServletRequestParameterException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "Parámetro requerido faltante: " + e.getParameterName());
        error.put("parameter", e.getParameterName());
        error.put("parameter_type", e.getParameterType());
        error.put("error_type", "MISSING_PARAMETER");
        error.put("timestamp", LocalDateTime.now().toString());
        System.err.println("Parámetro faltante: " + e.getParameterName());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "Error en formato JSON del request");
        error.put("error_type", "JSON_PARSE_ERROR");
        error.put("timestamp", LocalDateTime.now().toString());
        System.err.println("Error parseando JSON: " + e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // ========================================
    // MÉTODOS AUXILIARES PRIVADOS
    // ========================================

    private boolean isDevEnvironment() {
        return true;
    }

    private String getStackTraceAsString(Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    private void validarId(String id, String nombreCampo) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
    }

    private void validarTexto(String texto, String nombreCampo, int longitudMinima) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio");
        }
        if (texto.trim().length() < longitudMinima) {
            throw new IllegalArgumentException(nombreCampo + " debe tener al menos " + longitudMinima + " caracteres");
        }
    }

    private String validarYLimpiarUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }
        String urlLimpia = url.trim();
        if (!urlLimpia.startsWith("http://") && !urlLimpia.startsWith("https://")) {
            throw new IllegalArgumentException("La URL debe comenzar con http:// o https://");
        }
        return urlLimpia;
    }

    // ========================================
    // ENDPOINTS DE TESTING (SOLO DESARROLLO)
    // ========================================

    @PostMapping("/test/crear-datos-prueba")
    public ResponseEntity<Map<String, Object>> crearDatosPrueba(@RequestParam(defaultValue = "user_test") String usuarioId) {
        if (!isDevEnvironment()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            System.out.println("Creando datos de prueba para usuario: " + usuarioId);
            List<Planta> catalogo = servicioPlanta.obtenerTodas();
            if (catalogo.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "No hay plantas en el catálogo para crear datos de prueba");
                return ResponseEntity.badRequest().body(error);
            }
            int plantasCreadas = 0;
            String[] apodos = {"Mi Rosa Favorita", "Cactus del Escritorio", "Helecho del Baño"};
            String[] ubicaciones = {"Jardín frontal", "Oficina", "Baño principal"};
            EstadoPlanta[] estados = {EstadoPlanta.SALUDABLE, EstadoPlanta.NECESITA_AGUA, EstadoPlanta.FLORECIENDO};
            for (int i = 0; i < Math.min(3, catalogo.size()); i++) {
                Planta planta = catalogo.get(i);
                RegistroPlanta registro = new RegistroPlanta(apodos[i], usuarioId, planta, ubicaciones[i]);
                registro.setEstado(estados[i]);
                plantaRepositorio.guardarRegistro(registro);
                plantasCreadas++;
            }
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("success", true);
            resultado.put("message", "Datos de prueba creados");
            resultado.put("plantas_creadas", plantasCreadas);
            resultado.put("usuario_id", usuarioId);
            System.out.println("Datos de prueba creados: " + plantasCreadas + " plantas");
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.err.println("Error creando datos de prueba: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error creando datos de prueba: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/test/limpiar-usuario/{usuarioId}")
    public ResponseEntity<Map<String, Object>> limpiarDatosUsuario(@PathVariable String usuarioId) {
        if (!isDevEnvironment()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            System.out.println("Limpiando datos del usuario: " + usuarioId);
            List<RegistroPlanta> plantas = plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
            int plantasEliminadas = plantas.size();
            for (RegistroPlanta planta : plantas) {
                plantaRepositorio.eliminarRegistro(planta.getId());
            }
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("success", true);
            resultado.put("message", "Datos del usuario eliminados");
            resultado.put("plantas_eliminadas", plantasEliminadas);
            resultado.put("usuario_id", usuarioId);
            System.out.println("Datos eliminados: " + plantasEliminadas + " plantas del usuario " + usuarioId);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.err.println("Error limpiando datos: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error limpiando datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/debug/status")
    public ResponseEntity<Map<String, Object>> debugStatus() {
        if (!isDevEnvironment()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            Map<String, Object> status = new HashMap<>();
            status.put("service_name", "PlantCare Backend");
            status.put("version", "1.0.0");
            status.put("timestamp", LocalDateTime.now().toString());
            status.put("environment", "development");

            try {
                List<Planta> catalogo = servicioPlanta.obtenerTodas();
                status.put("mongodb_status", "CONNECTED");
                status.put("catalogo_count", catalogo.size());
            } catch (Exception e) {
                status.put("mongodb_status", "ERROR");
                status.put("mongodb_error", e.getMessage());
            }

            Runtime runtime = Runtime.getRuntime();
            status.put("memory_used_mb", (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);
            status.put("memory_free_mb", runtime.freeMemory() / 1024 / 1024);
            status.put("memory_total_mb", runtime.totalMemory() / 1024 / 1024);

            return ResponseEntity.ok(status);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error obteniendo status de debug");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ========================================
    // HISTORIAL DE CUIDADOS
    // ========================================

    @PostMapping("/registro/{registroId}/cuidado")
    public ResponseEntity<Map<String, Object>> registrarCuidado(
            @PathVariable String registroId,
            @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Registrando cuidado para planta: " + registroId);
            System.out.println("Datos del cuidado: " + request);

            RegistroPlanta registro = plantaRepositorio.obtenerRegistroPorId(registroId);
            if (registro == null) {
                response.put("success", false);
                response.put("message", "Planta no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            String tipo = (String) request.get("tipo");
            String notas = (String) request.get("notas");

            if (tipo == null || tipo.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Tipo de cuidado es obligatorio");
                return ResponseEntity.badRequest().body(response);
            }

            registro.agregarCuidado(tipo.trim(), notas != null ? notas.trim() : null);
            plantaRepositorio.actualizarRegistro(registro);

            System.out.println("Cuidado registrado: " + tipo + " para " + registro.getApodo());
            response.put("success", true);
            response.put("message", "Cuidado registrado exitosamente");
            response.put("tipo", tipo);
            response.put("fecha", LocalDateTime.now().toString());
            response.put("totalCuidados", registro.getTotalCuidados());
            response.put("nuevoEstado", registro.getEstado().toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error registrando cuidado: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error al registrar cuidado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/registro/{registroId}/cuidados")
    public ResponseEntity<List<RegistroPlanta.CuidadoRegistro>> obtenerHistorialCuidados(@PathVariable String registroId) {
        try {
            System.out.println("Obteniendo historial de cuidados para: " + registroId);
            RegistroPlanta registro = plantaRepositorio.obtenerRegistroPorId(registroId);
            if (registro == null) {
                return ResponseEntity.notFound().build();
            }
            List<RegistroPlanta.CuidadoRegistro> cuidados = registro.getCuidados();
            System.out.println("Cuidados encontrados: " + cuidados.size());
            return ResponseEntity.ok(cuidados);
        } catch (Exception e) {
            System.err.println("Error obteniendo historial: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/test/crear-datos-con-cuidados/{usuarioId}")
    public ResponseEntity<Map<String, Object>> crearDatosConCuidados(@PathVariable String usuarioId) {
        if (!isDevEnvironment()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            System.out.println("Creando datos con cuidados para usuario: " + usuarioId);
            List<RegistroPlanta> existentes = plantaRepositorio.listarRegistrosPorUsuario(usuarioId);
            for (RegistroPlanta planta : existentes) {
                plantaRepositorio.eliminarRegistro(planta.getId());
            }
            List<Planta> catalogo = servicioPlanta.obtenerTodas();
            if (catalogo.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "No hay plantas en el catálogo");
                return ResponseEntity.badRequest().body(error);
            }
            int plantasCreadas = 0;
            String[] apodos = {"Mi Rosa del Jardín", "Cactus de la Ventana", "Helecho del Baño", "Albahaca de la Cocina"};
            String[] ubicaciones = {"Jardín frontal", "Ventana de la sala", "Baño principal", "Cocina"};
            EstadoPlanta[] estados = {EstadoPlanta.SALUDABLE, EstadoPlanta.NECESITA_AGUA, EstadoPlanta.FLORECIENDO, EstadoPlanta.SALUDABLE};

            for (int i = 0; i < Math.min(4, catalogo.size()); i++) {
                Planta planta = catalogo.get(i);
                RegistroPlanta registro = new RegistroPlanta(apodos[i], usuarioId, planta, ubicaciones[i]);
                registro.setEstado(estados[i]);

                LocalDateTime fechaBase = LocalDateTime.now().minusDays(30);
                for (int j = 0; j < 8; j++) {
                    String[] tiposCuidado = {"riego", "fertilizacion", "limpieza", "poda"};
                    String tipo = tiposCuidado[j % tiposCuidado.length];
                    String nota = "Cuidado automático #" + (j + 1);
                    RegistroPlanta.CuidadoRegistro cuidado = new RegistroPlanta.CuidadoRegistro(tipo, nota);
                    cuidado.setFecha(fechaBase.plusDays(j * 3));
                    registro.getCuidados().add(cuidado);
                }
                registro.setUltimoCuidado(LocalDateTime.now().minusDays(2));
                plantaRepositorio.guardarRegistro(registro);
                plantasCreadas++;
            }
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("success", true);
            resultado.put("message", "Datos con cuidados creados");
            resultado.put("plantas_creadas", plantasCreadas);
            resultado.put("usuario_id", usuarioId);
            resultado.put("total_cuidados_por_planta", 8);
            System.out.println("Datos de prueba con cuidados creados exitosamente");
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.err.println("Error creando datos con cuidados: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error creando datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
