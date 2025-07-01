package com.planta.demo.dominio.modelo.servicios;

import com.planta.demo.dominio.modelo.bitacora.Bitacora;
import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.IBitacoraRepositorio;

import java.util.Date;
import java.util.Objects;

/**
 * Servicio de dominio para registrar observaciones (bitácoras) asociadas a plantas.
 */
public class ServicioBitacoraDominio {

    private final IBitacoraRepositorio repositorioBitacora;

    /**
     * Constructor que inyecta el repositorio del dominio.
     */
    public ServicioBitacoraDominio(IBitacoraRepositorio repositorioBitacora) {
        this.repositorioBitacora = repositorioBitacora;
    }

    /**
     * Registra una observación para una planta en forma de bitácora.
     *
     * @param planta Planta observada
     * @param descripcion Texto descriptivo de la observación
     * @param fotoUrl (opcional) URL de foto relacionada
     */
    public void registrarObservacion(Planta planta, String descripcion, String fotoUrl) {
        Objects.requireNonNull(planta, "La planta no puede ser null");
        Objects.requireNonNull(descripcion, "La descripción no puede ser null");

        Bitacora bitacora = new Bitacora(descripcion, fotoUrl, planta);
        bitacora.setFecha(new Date());

        repositorioBitacora.guardar(bitacora);
    }
}
