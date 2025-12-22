package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.recordatorio.Recordatorio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioRecordatorioDominio;
import com.planta.plantapp.dominio.modelo.recordatorio.EstadoRecordatorio;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de recordatorios de aplicación.
 * Aplica el patrón Facade para simplificar el acceso a la lógica de dominio.
 */
@Service
public class ServicioRecordatorioImpl {

    private final ServicioRecordatorioDominio servicioDominio;
    private final IRecordatorioRepositorio repositorioRecordatorio;

    @Autowired
    public ServicioRecordatorioImpl(ServicioRecordatorioDominio servicioDominio,
                                   IRecordatorioRepositorio repositorioRecordatorio) {
        this.servicioDominio = servicioDominio;
        this.repositorioRecordatorio = repositorioRecordatorio;
    }

    public List<Recordatorio> consultarTodos() {
        return repositorioRecordatorio.listarTodos();
    }

    public List<Recordatorio> consultarPorPlanta(String plantaId) {
        return repositorioRecordatorio.listarPorPlanta(plantaId);
    }

    public List<Recordatorio> consultarPorUsuario(String usuarioId) {
        return servicioDominio.consultarRecordatoriosPendientes(usuarioId);
    }

    public Recordatorio crearRecordatorio(Planta planta, TipoCuidado tipoCuidado, String mensaje, LocalDateTime fechaEnvio) {
        return servicioDominio.crearRecordatorio(planta, tipoCuidado, mensaje, fechaEnvio);
    }

    public void marcarComoRealizado(String id) {
        Recordatorio recordatorio = repositorioRecordatorio.obtenerPorId(id);
        if (recordatorio != null) {
            recordatorio.marcarComoCompletado();
            repositorioRecordatorio.guardar(recordatorio);
        }
    }

    public void eliminarRecordatorio(String id) {
        repositorioRecordatorio.eliminar(id);
    }

    public List<Recordatorio> consultarPorEstado(EstadoRecordatorio estado) {
        return repositorioRecordatorio.listarPorEstado(estado);
    }
}
