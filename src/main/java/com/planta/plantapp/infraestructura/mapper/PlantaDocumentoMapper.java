package com.planta.plantapp.infraestructura.mapper;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.infraestructura.documento.PlantaDocumento;
import com.planta.plantapp.infraestructura.documento.CuidadoDocumento;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper para convertir entre entidad de dominio Planta y documento MongoDB PlantaDocumento.
 * Aplica principios de Clean Code y Single Responsibility Principle.
 */
@Component
public class PlantaDocumentoMapper {

    /**
     * Convierte una entidad de dominio Planta a documento MongoDB.
     *
     * @param planta entidad de dominio
     * @return documento de MongoDB
     */
    public PlantaDocumento dominioADocumento(Planta planta) {
        if (planta == null) {
            return null;
        }

        PlantaDocumento documento = new PlantaDocumento();
        documento.setId(planta.getId());
        documento.setNombreComun(planta.getNombreComun());
        documento.setNombreCientifico(planta.getNombreCientifico());
        documento.setDescripcion(planta.getDescripcion());
        documento.setImagenURL(planta.getImagenURL());

        // Convertir cuidados si existen
        if (planta.getCuidados() != null && !planta.getCuidados().isEmpty()) {
            documento.setCuidados(convertirCuidadosADocumento(planta.getCuidados()));
        }

        return documento;
    }

    /**
     * Convierte un documento MongoDB a entidad de dominio Planta.
     *
     * @param documento documento de MongoDB
     * @return entidad de dominio
     */
    public Planta documentoADominio(PlantaDocumento documento) {
        if (documento == null) {
            return null;
        }

        Planta planta = new Planta(
                documento.getNombreComun(),
                documento.getNombreCientifico(),
                documento.getDescripcion(),
                documento.getImagenURL()
        );
        planta.setId(documento.getId());

        // Convertir cuidados si existen
        if (documento.getCuidados() != null && !documento.getCuidados().isEmpty()) {
            List<Cuidado> cuidados = convertirCuidadosADominio(documento.getCuidados());
            cuidados.forEach(planta::agregarCuidado);
        }

        return planta;
    }

    /**
     * Convierte lista de cuidados de dominio a documentos.
     * Refactoring: Extract Method para evitar duplicación
     */
    private List<CuidadoDocumento> convertirCuidadosADocumento(List<Cuidado> cuidados) {
        return cuidados.stream()
                .map(this::cuidadoADocumento)
                .toList();
    }

    /**
     * Convierte lista de documentos de cuidados a dominio.
     * Refactoring: Extract Method para evitar duplicación
     */
    private List<Cuidado> convertirCuidadosADominio(List<CuidadoDocumento> documentos) {
        return documentos.stream()
                .map(this::documentoACuidado).toList();
    }

    /**
     * Convierte un cuidado de dominio a documento.
     */
    private CuidadoDocumento cuidadoADocumento(Cuidado cuidado) {
        if (cuidado == null) {
            return null;
        }

        CuidadoDocumento documento = new CuidadoDocumento();
        documento.setTipo(cuidado.getTipo().name());
        documento.setDescripcion(cuidado.getDescripcion());
        documento.setFrecuenciaDias(cuidado.getFrecuenciaDias());

        if (cuidado.getFechaAplicacion() != null) {
            documento.setFechaAplicacion(cuidado.getFechaAplicacion());
        }
        if (cuidado.getFechaProxima() != null) {
            documento.setFechaProxima(cuidado.getFechaProxima());
        }

        return documento;
    }

    /**
     * Convierte un documento de cuidado a dominio.
     */
    private Cuidado documentoACuidado(CuidadoDocumento documento) {
        if (documento == null) {
            return null;
        }

        Cuidado cuidado = new Cuidado(
                TipoCuidado.valueOf(documento.getTipo()),
                documento.getDescripcion(),
                documento.getFrecuenciaDias()
        );

        if (documento.getFechaAplicacion() != null) {
            cuidado.setFechaAplicacion(documento.getFechaAplicacion());
        }
        if (documento.getFechaProxima() != null) {
            cuidado.setFechaProxima(documento.getFechaProxima());
        }

        return cuidado;
    }
}