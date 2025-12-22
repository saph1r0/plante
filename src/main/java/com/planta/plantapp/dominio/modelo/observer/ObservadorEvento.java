package com.planta.plantapp.dominio.modelo.observer;

/**
 * Patrón Observer: Interfaz para observadores de eventos del dominio.
 */
public interface ObservadorEvento<T> {

    /**
     * Método llamado cuando ocurre un evento.
     * @param evento El evento que ocurrió
     */
    void actualizar(T evento);
}

