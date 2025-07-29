package com.planta.plantapp.infraestructura.mapper;

import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Etiqueta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaRequestDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.PlantaResponseDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.CuidadoDTO;
import com.planta.plantapp.dominio.modelo.planta.dto.EtiquetaDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper para convertir entre entidades Planta y DTOs
 */
@Component
public class PlantaMapper {

    private PlantaMapper() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Convierte PlantaRequestDTO a entidad Planta
     */
    public static Planta dtoADominio(PlantaRequestDTO dto) {
        if (dto == null) return null;

        Planta planta = new Planta(
                dto.getNombreComun(),
                dto.getNombreCientifico(),
                dto.getDescripcion(),
                dto.getImagenURL()
        );

        // Convertir etiquetas si existen
        if (dto.getEtiquetas() != null && !dto.getEtiquetas().isEmpty()) {
            List<Etiqueta> etiquetas = new ArrayList<>();
            for (EtiquetaDTO etiquetaDto : dto.getEtiquetas()) {
                Etiqueta etiqueta = new Etiqueta(etiquetaDto.getNombre(), etiquetaDto.getColor());
                etiquetas.add(etiqueta);
            }
            planta.setEtiquetas(etiquetas);
        }

        // Convertir cuidados si existen
        if (dto.getCuidados() != null && !dto.getCuidados().isEmpty()) {
            for (CuidadoDTO cuidadoDto : dto.getCuidados()) {
                Cuidado cuidado = new Cuidado(
                        TipoCuidado.valueOf(cuidadoDto.getTipo().toUpperCase()),
                        cuidadoDto.getDescripcion(),
                        Integer.valueOf(cuidadoDto.getFrecuenciaDias())
                );
                planta.agregarCuidado(cuidado);
            }
        }

        return planta;
    }

    /**
     * Convierte entidad Planta a PlantaResponseDTO
     */
    public static PlantaResponseDTO dominioADto(Planta planta) {
        if (planta == null) return null;

        PlantaResponseDTO dto = new PlantaResponseDTO();
        dto.setId(planta.getId());
        dto.setNombreComun(planta.getNombreComun());
        dto.setNombreCientifico(planta.getNombreCientifico());
        dto.setDescripcion(planta.getDescripcion());
        dto.setImagenURL(planta.getImagenURL());

        // Convertir etiquetas si existen
        if (planta.getEtiquetas() != null && !planta.getEtiquetas().isEmpty()) {
            List<EtiquetaDTO> etiquetasDto = new ArrayList<>();
            for (Etiqueta etiqueta : planta.getEtiquetas()) {
                EtiquetaDTO etiquetaDto = new EtiquetaDTO();
                etiquetaDto.setNombre(etiqueta.getNombre());
                etiquetaDto.setColor(etiqueta.getColor());
                etiquetasDto.add(etiquetaDto);
            }
            dto.setEtiquetas(etiquetasDto);
        }

        // Convertir cuidados si existen
        if (planta.getCuidados() != null && !planta.getCuidados().isEmpty()) {
            List<CuidadoDTO> cuidadosDto = new ArrayList<>();
            for (Cuidado cuidado : planta.getCuidados()) {
                CuidadoDTO cuidadoDto = new CuidadoDTO();
                cuidadoDto.setTipo(cuidado.getTipo().name());
                cuidadoDto.setDescripcion(cuidado.getDescripcion());
                cuidadoDto.setFrecuencia(cuidado.getFrecuenciaDias());
                cuidadosDto.add(cuidadoDto);
            }
            dto.setCuidados(cuidadosDto);
        }

        return dto;
    }

    /**
     * Convierte lista de entidades a lista de DTOs
     */
    public List<PlantaResponseDTO> dominioADtoList(List<Planta> plantas) {
        if (plantas == null || plantas.isEmpty()) return new ArrayList<>();

        List<PlantaResponseDTO> resultado = new ArrayList<>();
        for (Planta planta : plantas) {
            resultado.add(dominioADto(planta));
        }
        return resultado;
    }
}
