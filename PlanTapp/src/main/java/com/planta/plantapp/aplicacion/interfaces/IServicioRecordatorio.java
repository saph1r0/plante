package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.util.Date;
import java.util.List;

public interface IServicioRecordatorio {
    
    List<Recordatorio> consultarTodos();
    
    List<Recordatorio> consultarPorPlanta(Long plantaId);
    
    List<Recordatorio> consultarPorUsuario(Long usuarioId);
    
    void crearRecordatorio(Planta planta, EstadoRecordatorio estado, String mensaje);
    
    void editarRecordatorio(Long id, String nuevoMensaje);
    
    void marcarComoRealizado(Long id);
    
    void eliminarRecordatorio(Long id);
    
    List<Recordatorio> consultarPorFecha(Date fecha);
    
    List<Recordatorio> consultarPorEstado(EstadoRecordatorio estado);
}


