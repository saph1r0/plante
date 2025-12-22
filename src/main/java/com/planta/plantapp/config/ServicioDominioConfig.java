package com.planta.plantapp.config;

import com.planta.plantapp.dominio.modelo.INotificacionRepositorio;
import com.planta.plantapp.dominio.modelo.IRecordatorioRepositorio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioNotificacionDominio;
import com.planta.plantapp.dominio.modelo.servicios.ServicioRecordatorioDominio;
import com.planta.plantapp.dominio.modelo.notificacion.estrategia.EstrategiaEnvioNotificacion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de beans de Spring para servicios de dominio.
 */
@Configuration
public class ServicioDominioConfig {

    @Bean
    public ServicioRecordatorioDominio servicioRecordatorioDominio(IRecordatorioRepositorio repositorio) {
        return new ServicioRecordatorioDominio(repositorio);
    }

    @Bean
    public ServicioNotificacionDominio servicioNotificacionDominio(
            INotificacionRepositorio repositorio,
            List<EstrategiaEnvioNotificacion> estrategias) {
        return new ServicioNotificacionDominio(repositorio, estrategias);
    }
}

