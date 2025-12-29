package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioPlanta;
import com.planta.plantapp.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.dto.RegistroPlantaResponseDTO;
import com.planta.plantapp.infraestructura.documento.RegistroPlantaDocumento;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegistroPlantaFacade {

    private final IServicioRegistroPlanta servicioRegistro;
    private final IServicioPlanta servicioPlanta;

    public RegistroPlantaFacade(IServicioRegistroPlanta servicioRegistro,
                                IServicioPlanta servicioPlanta) {
        this.servicioRegistro = servicioRegistro;
        this.servicioPlanta = servicioPlanta;
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
        try {
            Optional<Planta> plantaOpt = servicioPlanta.obtenerPorId(plantaId);
            if (plantaOpt.isEmpty()) return Collections.emptyMap();

            Planta planta = plantaOpt.get();
            Map<String, Object> map = new HashMap<>();
            map.put("id", planta.getId());
            map.put("nombreComun", planta.getNombreComun());
            map.put("nombreCientifico", planta.getNombreCientifico());
            map.put("descripcion", planta.getDescripcion());
            map.put("imagenUrl", planta.getImagenURL());
            return map;

        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }
}

