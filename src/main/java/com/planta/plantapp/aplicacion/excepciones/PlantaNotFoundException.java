package com.planta.plantapp.aplicacion.excepciones;

public class PlantaNotFoundException extends RuntimeException {
    public PlantaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
