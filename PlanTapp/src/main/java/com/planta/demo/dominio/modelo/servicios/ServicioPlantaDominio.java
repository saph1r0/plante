package com.planta.plantapp.dominio.modelo.servicios;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;

import java.time.LocalDateTime;
//import java.util.List;
import java.util.Objects;

/**
 * Servicio de dominio del problema para lógica relacionada a plantas.
 */
public class ServicioPlantaDominio {

    /**
     * Agrega un cuidado a la planta, sin depender de infraestructura.
     *
     * @param planta Planta objetivo
     * @param tipo Tipo de cuidado (riego, poda, etc.)
     * @param frecuenciaDias Cada cuántos días debe repetirse
     * @param notas Notas adicionales
     */
    public void agregarCuidado(Planta planta, TipoCuidado tipo, Integer frecuenciaDias, String notas) {
        Objects.requireNonNull(planta, "Planta no puede ser null");
        Objects.requireNonNull(tipo, "Tipo de cuidado no puede ser null");

        Cuidado nuevoCuidado = new Cuidado(tipo, tipo.name(), frecuenciaDias);
        nuevoCuidado.setFechaAplicacion(LocalDateTime.now());
        nuevoCuidado.setNotas(notas);
/* 
        // Agregar directamente a la lista, si no hay método en Planta
        List<Cuidado> lista = planta.getCuidados();
        if (lista != null) {
            lista.add(nuevoCuidado);
        }*/
    }

    /**
     * Marca un cuidado como realizado y reprograma su próxima aplicación.
     *
     * @param cuidado Cuidado -><< actualizar
     */
    public void marcarCuidadoComoRealizado(Cuidado cuidado) {
        Objects.requireNonNull(cuidado, "El cuidado no puede ser null");
        cuidado.setFechaAplicacion(LocalDateTime.now());
        cuidado.programarProximo();
    }
}
