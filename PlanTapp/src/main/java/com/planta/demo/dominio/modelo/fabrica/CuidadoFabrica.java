package com.planta.plantapp.dominio.modelo.fabrica;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;


/**
 * Fábrica de aplicación para crear instancias de Cuidado.
 * Pertenece al dominio de la solución.
 */
public class CuidadoFabrica {

    public CuidadoFabrica() {
    }

    /**
     * Crea una instancia de Cuidado con los parámetros proporcionados.
     *
     * @param tipoTexto       Nombre del tipo de cuidado (debe coincidir con TipoCuidado)
     * @param descripcion     Descripción del cuidado
     * @param frecuenciaDias  Frecuencia sugerida en días (opcional, puede ser null)
     * @return                Instancia de Cuidado o null si el tipo no es válido
     */
    public Cuidado crearCuidado(String tipoTexto, String descripcion, Integer frecuenciaDias) {
        if (tipoTexto == null || tipoTexto.isBlank()) return null;

        TipoCuidado tipo;
        try {
            tipo = TipoCuidado.valueOf(tipoTexto.toUpperCase()); //  coincidir con el enum
        } catch (IllegalArgumentException e) {
            return null; 
        }

        return new Cuidado(tipo, descripcion, frecuenciaDias);
    }
}
