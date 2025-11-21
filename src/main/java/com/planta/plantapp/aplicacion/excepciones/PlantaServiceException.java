package com.planta.plantapp.aplicacion.excepciones;

public class PlantaServiceException extends RuntimeException {
    public PlantaServiceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
