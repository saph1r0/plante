package com.planta.userplantsservice.aplicacion.servicios;

import com.planta.userplantsservice.aplicacion.interfaces.IServicioRegistroPlanta;
import com.planta.userplantsservice.infraestructura.documento.RegistroPlantaDocumento;
import com.planta.userplantsservice.infraestructura.repositorio.mongodb.mongo.RegistroPlantaRepositorioMongoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioRegistroPlantaImpl implements IServicioRegistroPlanta {

    private static final Logger logger = LoggerFactory.getLogger(ServicioRegistroPlantaImpl.class);
    private final RegistroPlantaRepositorioMongoDB repositorio;

    public ServicioRegistroPlantaImpl(RegistroPlantaRepositorioMongoDB repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public RegistroPlantaDocumento guardar(RegistroPlantaDocumento registro) {
        logger.info("Servicio: Guardando registro de planta personal...");
        return repositorio.guardar(registro);
    }

    @Override
    public List<RegistroPlantaDocumento> listarPorUsuario(String usuarioId) {
        logger.info("Servicio: Listando plantas del usuario {}", usuarioId);
        return repositorio.listarPorUsuario(usuarioId);
    }

    @Override
    public void eliminar(String id) {
        logger.info("Servicio: Eliminando registro {}", id);
        repositorio.eliminar(id);
    }

    public RegistroPlantaDocumento obtenerPorId(String id) {
    return repositorio.obtenerPorId(id);
}

}
