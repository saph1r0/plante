package com.planta.userplantsservice.aplicacion.interfaces;

import com.planta.userplantsservice.infraestructura.documento.RegistroPlantaDocumento;
import java.util.List;

/**
 * Interfaz de servicio para manejar las plantas personales de cada usuario.
 */
public interface IServicioRegistroPlanta {

    RegistroPlantaDocumento guardar(RegistroPlantaDocumento registro);

    List<RegistroPlantaDocumento> listarPorUsuario(String usuarioId);

    void eliminar(String id);
    
    RegistroPlantaDocumento obtenerPorId(String id);

}
