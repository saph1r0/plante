package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.dominio.modelo.planta.EstadoPlanta;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaRequestDTO;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RegistroPlantaFacade {
    private final IServicioRegistroPlanta servicioRegistro;
    private final IServicioPlanta servicioPlanta;

    public RegistroPlantaFacade(IServicioRegistroPlanta servicioRegistro, IServicioPlanta servicioPlanta) {
        this.servicioRegistro = servicioRegistro;
        this.servicioPlanta = servicioPlanta;
    }

    public RegistroPlantaResponseDTO registrar(RegistroPlantaRequestDTO request, String usuarioId) {
        RegistroPlantaDocumento documento = new RegistroPlantaDocumento(
                usuarioId,
                request.getPlantaId(),
                request.getApodo(),
                request.getUbicacion(),
                request.getEstado() != null ? EstadoPlanta.valueOf(request.getEstado()) : EstadoPlanta.SALUDABLE
        );
        documento.setFotoPersonal(request.getFotoPersonal());
        documento.setNotas(request.getNotas());
        return construirRespuesta(servicioRegistro.guardar(documento));
    }

    public RegistroPlantaResponseDTO actualizar(String id, RegistroPlantaRequestDTO request) {
        RegistroPlantaDocumento existente = servicioRegistro.obtenerPorId(id);

        if (existente == null) {
            throw new RuntimeException("Registro de planta no encontrado");
        }

        if (request.getApodo() != null) existente.setApodo(request.getApodo());
        if (request.getUbicacion() != null) existente.setUbicacion(request.getUbicacion());
        if (request.getEstado() != null)
            existente.setEstado(EstadoPlanta.valueOf(request.getEstado()));
        if (request.getNotas() != null) existente.setNotas(request.getNotas());

        return construirRespuesta(servicioRegistro.guardar(existente));
    }


    public void eliminar(String id) { servicioRegistro.eliminar(id); }

    public RegistroPlantaResponseDTO obtenerPorId(String id) {
        RegistroPlantaDocumento r = servicioRegistro.obtenerPorId(id);
        return (r == null) ? null : construirRespuesta(r);
    }

    public List<RegistroPlantaResponseDTO> listarPorUsuario(String usuarioId) {
        return servicioRegistro.listarPorUsuario(usuarioId).stream().map(this::construirRespuesta).toList();
    }

    public Map<String, Object> obtenerEstadisticas(String usuarioId) {
        List<RegistroPlantaResponseDTO> registros = listarPorUsuario(usuarioId);
        long total = registros.size();
        long saludables = registros.stream().filter(r -> "SALUDABLE".equalsIgnoreCase(r.getEstado())).count();
        return Map.of("totalPlantas", total, "plantasSaludables", saludables, "plantasNecesitanAtencion", total - saludables);
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
        dto.setPlanta(obtenerPlantaCatalogo(r.getPlantaId()));
        return dto;
    }

    private Map<String, Object> obtenerPlantaCatalogo(String plantaId) {
        return servicioPlanta.obtenerPorId(plantaId).map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("nombreComun", p.getNombreComun());
            map.put("nombreCientifico", p.getNombreCientifico());
            return map;
        }).orElse(Collections.emptyMap());
    }
}