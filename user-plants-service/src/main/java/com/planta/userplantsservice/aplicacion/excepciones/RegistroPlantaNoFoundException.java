package com.planta.userplantsservice.aplicacion.excepciones;

public class RegistroPlantaNoFoundException extends RuntimeException {

    public RegistroPlantaNoFoundException(String id) {
        super("Registro de planta no encontrado con id: " + id);
    }
}
