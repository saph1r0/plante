package com.planta.plantapp.infraestructura.mapper;

import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.infraestructura.documento.CuidadoDocumento;
import com.planta.plantapp.infraestructura.documento.PlantaDocumento;

import java.util.ArrayList;
import java.util.List;

public class PlantaMapper {

    private PlantaMapper() {
        // Constructor privado para evitar instanciación
    }

    public static PlantaDocumento aDocumento(Planta planta) {
        List<CuidadoDocumento> cuidadosDoc = new ArrayList<>();
        for (Cuidado cuidado : planta.getCuidados()) {
            cuidadosDoc.add(new CuidadoDocumento(
                    cuidado.getTipo().name(),
                    cuidado.getDescripcion(),
                    cuidado.getFrecuenciaDias()
            ));
        }

        PlantaDocumento doc = new PlantaDocumento();
        doc.setId(planta.getId());
        doc.setNombreComun(planta.getNombreComun());
        doc.setNombreCientifico(planta.getNombreCientifico());
        doc.setDescripcion(planta.getDescripcion());
        doc.setImagenURL(planta.getImagenURL());
        doc.setCuidados(cuidadosDoc);

        return doc;
    }

    public static Planta aDominio(PlantaDocumento doc) {
        Planta planta = new Planta(doc.getNombreComun(), doc.getNombreCientifico(), doc.getDescripcion(), doc.getImagenURL());

        if (doc.getId() != null) {
            planta = new Planta(doc.getId()); // si manejas ID manualmente
        }

        List<CuidadoDocumento> cuidadosDoc = doc.getCuidados();
        if (cuidadosDoc != null) {
            for (CuidadoDocumento cuidadoDoc : cuidadosDoc) {
                Cuidado cuidado = new Cuidado(
                        TipoCuidado.valueOf(cuidadoDoc.getTipo().toUpperCase()),
                        cuidadoDoc.getDescripcion(),
                        cuidadoDoc.getFrecuenciaDias()
                );
                planta.agregarCuidado(cuidado); // si tienes una lista de cuidados en Planta, agrégala
            }
        }

        return planta;
    }
}
