package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import java.util.Date;
import java.util.List;

public interface IServicioBitacora {
    
    void registrarObservacion(String plantaId, String descripcion);
    
    List<Bitacora> obtenerPorPlanta(String plantaId);
    
    List<Bitacora> obtenerPorFechaRango(Date desde, Date hasta);
    
    void editarObservacion(String bitacoraId, String nuevaDescripcion);
    
    void eliminar(String bitacoraId);
    
    byte[] exportarHistorial(String plantaId);
    
    List<Bitacora> buscarPorTipo(String tipo);
    
    List<Bitacora> listarPendientesPorUsuario(String usuarioId);
}


