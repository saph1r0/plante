package com.planta.plantapp.dominio.modelo.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Observer: Publicador de eventos del dominio.
 */
public class PublicadorEventos<T> {

    private final List<ObservadorEvento<T>> observadores = new ArrayList<>();

    /**
     * Suscribe un observador a los eventos.
     */
    public void suscribir(ObservadorEvento<T> observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Cancela la suscripción de un observador.
     */
    public void desuscribir(ObservadorEvento<T> observador) {
        observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores sobre un evento.
     */
    public void notificar(T evento) {
        for (ObservadorEvento<T> observador : observadores) {
            observador.actualizar(evento);
        }
    }

    /**
     * Obtiene el número de observadores suscritos.
     */
    public int cantidadObservadores() {
        return observadores.size();
    }
}

