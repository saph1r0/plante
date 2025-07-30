package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioBitacora;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para manejar las operaciones relacionadas con la bitácora de las plantas.
 */
@RestController
@RequestMapping("/api/bitacoras")
@CrossOrigin(origins = "*")
public class BitacoraController {

    private final IServicioBitacora servicioBitacora;

    @Autowired
    public BitacoraController(IServicioBitacora servicioBitacora) {
        this.servicioBitacora = servicioBitacora;
    }

    /**
     * Registra una nueva entrada en la bitácora.
     */
    @PostMapping
    public ResponseEntity<Bitacora> registrarEntrada(@RequestBody RegistrarBitacoraRequest request) {
        try {
            Bitacora bitacora = servicioBitacora.registrarEntrada(
                request.getDescripcion(),
                request.getFoto(),
                request.getPlantaId(),
                request.getTipoCuidado(),
                request.getObservaciones()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(bitacora);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene una bitácora por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bitacora> obtenerBitacora(@PathVariable String id) {
        try {
            Bitacora bitacora = servicioBitacora.obtenerBitacora(id);
            return ResponseEntity.ok(bitacora);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lista todas las bitácoras del sistema.
     */
    @GetMapping
    public ResponseEntity<List<Bitacora>> listarTodasLasBitacoras() {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarTodasLasBitacoras();
            return ResponseEntity.ok(bitacoras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lista las bitácoras de una planta específica.
     */
    @GetMapping("/planta/{plantaId}")
    public ResponseEntity<List<Bitacora>> obtenerPorPlanta(@PathVariable String plantaId) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarBitacorasPorPlanta(plantaId);
            return ResponseEntity.ok(bitacoras);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lista las bitácoras por tipo de cuidado.
     */
    @GetMapping("/tipo/{tipoCuidado}")
    public ResponseEntity<List<Bitacora>> obtenerPorTipoCuidado(@PathVariable String tipoCuidado) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarBitacorasPorTipoCuidado(tipoCuidado);
            return ResponseEntity.ok(bitacoras);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lista las bitácoras en un rango de fechas.
     */
    @GetMapping("/fechas")
    public ResponseEntity<List<Bitacora>> obtenerPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarBitacorasPorFechas(fechaInicio, fechaFin);
            return ResponseEntity.ok(bitacoras);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lista las bitácoras de una planta en un rango de fechas.
     */
    @GetMapping("/planta/{plantaId}/fechas")
    public ResponseEntity<List<Bitacora>> obtenerPorPlantaYFechas(
            @PathVariable String plantaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarBitacorasPorPlantaYFechas(plantaId, fechaInicio, fechaFin);
            return ResponseEntity.ok(bitacoras);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza una bitácora existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bitacora> actualizarBitacora(@PathVariable String id, @RequestBody ActualizarBitacoraRequest request) {
        try {
            Bitacora bitacora = servicioBitacora.actualizarBitacora(
                id,
                request.getDescripcion(),
                request.getFoto(),
                request.getTipoCuidado(),
                request.getObservaciones()
            );
            return ResponseEntity.ok(bitacora);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Elimina una bitácora.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBitacora(@PathVariable String id) {
        try {
            servicioBitacora.eliminarBitacora(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene el historial reciente de una planta.
     */
    @GetMapping("/planta/{plantaId}/reciente")
    public ResponseEntity<List<Bitacora>> obtenerHistorialReciente(
            @PathVariable String plantaId,
            @RequestParam(defaultValue = "10") int limite) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.obtenerHistorialReciente(plantaId, limite);
            return ResponseEntity.ok(bitacoras);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene estadísticas de cuidados de una planta.
     */
    @GetMapping("/planta/{plantaId}/estadisticas")
    public ResponseEntity<EstadisticasBitacoraResponse> obtenerEstadisticas(@PathVariable String plantaId) {
        try {
            long totalCuidados = servicioBitacora.obtenerTotalCuidados(plantaId);
            return ResponseEntity.ok(new EstadisticasBitacoraResponse(totalCuidados));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Exporta las bitácoras de una planta.
     */
    @GetMapping("/planta/{plantaId}/exportar")
    public ResponseEntity<List<Bitacora>> exportarBitacorasPlanta(@PathVariable String plantaId) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.exportarBitacorasPlanta(plantaId);
            return ResponseEntity.ok(bitacoras);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DTOs internos
    public static class RegistrarBitacoraRequest {
        private String descripcion;
        private String foto;
        private String plantaId;
        private String tipoCuidado;
        private String observaciones;

        // Getters y setters
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getFoto() { return foto; }
        public void setFoto(String foto) { this.foto = foto; }
        public String getPlantaId() { return plantaId; }
        public void setPlantaId(String plantaId) { this.plantaId = plantaId; }
        public String getTipoCuidado() { return tipoCuidado; }
        public void setTipoCuidado(String tipoCuidado) { this.tipoCuidado = tipoCuidado; }
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

    public static class ActualizarBitacoraRequest {
        private String descripcion;
        private String foto;
        private String tipoCuidado;
        private String observaciones;

        // Getters y setters
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getFoto() { return foto; }
        public void setFoto(String foto) { this.foto = foto; }
        public String getTipoCuidado() { return tipoCuidado; }
        public void setTipoCuidado(String tipoCuidado) { this.tipoCuidado = tipoCuidado; }
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

    public static class EstadisticasBitacoraResponse {
        private long totalCuidados;

        public EstadisticasBitacoraResponse(long totalCuidados) {
            this.totalCuidados = totalCuidados;
        }

        public long getTotalCuidados() { return totalCuidados; }
        public void setTotalCuidados(long totalCuidados) { this.totalCuidados = totalCuidados; }
    }
}
