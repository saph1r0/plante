package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import com.planta.plantapp.aplicacion.excepciones.RegistroPlantaNoFoundException;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class RegistroPlantaFacade {

    private final IServicioRegistroPlanta servicioRegistro;

    public RegistroPlantaFacade(IServicioRegistroPlanta servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }

    public RegistroPlantaResponseDTO registrar(RegistroPlantaRequestDTO request, String usuarioId) {

        RegistroPlantaDocumento documento = new RegistroPlantaDocumento(
                usuarioId,
                request.getPlantaId(),
                request.getApodo(),
                request.getUbicacion(),
                parseEstado(request.getEstado())
        );

        documento.setFotoPersonal(request.getFotoPersonal());
        documento.setNotas(request.getNotas());

        return construirRespuesta(servicioRegistro.guardar(documento));
    }

    public RegistroPlantaResponseDTO actualizar(String id, RegistroPlantaRequestDTO request) {

        RegistroPlantaDocumento existente = servicioRegistro.obtenerPorId(id);

        if (existente == null) {
            throw new RegistroPlantaNoFoundException(id);
        }

        if (request.getApodo() != null) existente.setApodo(request.getApodo());
        if (request.getUbicacion() != null) existente.setUbicacion(request.getUbicacion());
        if (request.getEstado() != null) existente.setEstado(parseEstado(request.getEstado()));
        if (request.getFotoPersonal() != null) existente.setFotoPersonal(request.getFotoPersonal());
        if (request.getNotas() != null) existente.setNotas(request.getNotas());

        return construirRespuesta(servicioRegistro.guardar(existente));
    }

    public void eliminar(String id) {
        servicioRegistro.eliminar(id);
    }

    public RegistroPlantaResponseDTO obtenerPorId(String id) {
        RegistroPlantaDocumento r = servicioRegistro.obtenerPorId(id);
        return (r == null) ? null : construirRespuesta(r);
    }

    public List<RegistroPlantaResponseDTO> listarPorUsuario(String usuarioId) {
        return servicioRegistro.listarPorUsuario(usuarioId)
                .stream()
                .map(this::construirRespuesta)
                .toList();
    }

    public Map<String, Object> obtenerEstadisticas(String usuarioId) {
        List<RegistroPlantaResponseDTO> registros = listarPorUsuario(usuarioId);

        long total = registros.size();

        long saludables = registros.stream()
                .filter(r -> "SALUDABLE".equalsIgnoreCase(r.getEstado()) ||
                        "FLORECIENDO".equalsIgnoreCase(r.getEstado()))
                .count();

        return Map.of(
                "totalPlantas", total,
                "plantasSaludables", saludables,
                "plantasNecesitanAtencion", total - saludables
        );
    }

    private RegistroPlantaResponseDTO construirRespuesta(RegistroPlantaDocumento r) {

        RegistroPlantaResponseDTO dto = new RegistroPlantaResponseDTO();
        dto.setId(r.getId());
        dto.setUsuarioId(r.getUsuarioId());
        dto.setPlantaId(r.getPlantaId());
        dto.setApodo(r.getApodo());
        dto.setUbicacion(r.getUbicacion());
        dto.setEstado(r.getEstado() != null ? r.getEstado().name() : null);
        dto.setFechaRegistro(r.getFechaRegistro());
        dto.setFotoPersonal(r.getFotoPersonal());
        dto.setNotas(r.getNotas());

        return dto;
    }

    private EstadoPlanta parseEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return EstadoPlanta.SALUDABLE;
        }
        return EstadoPlanta.valueOf(estado.trim().toUpperCase());
    }
}