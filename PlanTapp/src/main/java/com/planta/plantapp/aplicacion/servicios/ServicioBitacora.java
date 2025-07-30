package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.aplicacion.interfaces.IServicioBitacora;
import com.planta.plantapp.dominio.modelo.IBitacoraRepositorio;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de aplicación para la gestión de bitácoras.
 * Coordina las operaciones de dominio y orquesta los casos de uso.
 */
@Service
public class ServicioBitacora implements IServicioBitacora {

    private final IBitacoraRepositorio bitacoraRepositorio;

    @Autowired
    public ServicioBitacora(IBitacoraRepositorio bitacoraRepositorio) {
        this.bitacoraRepositorio = bitacoraRepositorio;
    }

    @Override
    public Bitacora registrarEntrada(String descripcion, String foto, String plantaId, String tipoCuidado, String observaciones) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }

        Bitacora bitacora = new Bitacora(descripcion, foto, plantaId, tipoCuidado, observaciones);
        bitacoraRepositorio.guardar(bitacora);
        return bitacora;
    }

    @Override
    public Bitacora obtenerBitacora(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID de la bitácora es obligatorio");
        }

        Bitacora bitacora = bitacoraRepositorio.obtenerPorId(id);
        if (bitacora == null) {
            throw new RuntimeException("Bitácora no encontrada con ID: " + id);
        }
        return bitacora;
    }

    @Override
    public List<Bitacora> listarTodasLasBitacoras() {
        return bitacoraRepositorio.listarTodas();
    }

    @Override
    public List<Bitacora> listarBitacorasPorPlanta(String plantaId) {
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        return bitacoraRepositorio.listarPorPlanta(plantaId);
    }

    @Override
    public List<Bitacora> listarBitacorasPorTipoCuidado(String tipoCuidado) {
        if (tipoCuidado == null || tipoCuidado.isBlank()) {
            throw new IllegalArgumentException("El tipo de cuidado es obligatorio");
        }
        return bitacoraRepositorio.listarPorTipoCuidado(tipoCuidado);
    }

    @Override
    public List<Bitacora> listarBitacorasPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        return bitacoraRepositorio.listarPorRangoFechas(fechaInicio, fechaFin);
    }

    @Override
    public List<Bitacora> listarBitacorasPorPlantaYFechas(String plantaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        return bitacoraRepositorio.listarPorPlantaYFechas(plantaId, fechaInicio, fechaFin);
    }

    @Override
    public Bitacora actualizarBitacora(String id, String descripcion, String foto, String tipoCuidado, String observaciones) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID de la bitácora es obligatorio");
        }

        Bitacora bitacora = obtenerBitacora(id);

        if (descripcion != null && !descripcion.isBlank()) {
            bitacora.setDescripcion(descripcion);
        }
        if (foto != null) {
            bitacora.setFoto(foto);
        }
        if (tipoCuidado != null && !tipoCuidado.isBlank()) {
            bitacora.setTipoCuidado(tipoCuidado);
        }
        if (observaciones != null) {
            bitacora.setObservaciones(observaciones);
        }

        bitacoraRepositorio.actualizar(bitacora);
        return bitacora;
    }

    @Override
    public void eliminarBitacora(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID de la bitácora es obligatorio");
        }

        // Verificar que la bitácora existe antes de eliminar
        obtenerBitacora(id);
        bitacoraRepositorio.eliminar(id);
    }

    @Override
    public List<Bitacora> obtenerHistorialReciente(String plantaId, int limite) {
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        if (limite <= 0) {
            throw new IllegalArgumentException("El límite debe ser mayor a 0");
        }
        return bitacoraRepositorio.obtenerUltimasPorPlanta(plantaId, limite);
    }

    @Override
    public long obtenerTotalCuidados(String plantaId) {
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        return bitacoraRepositorio.contarPorPlanta(plantaId);
    }

    @Override
    public List<Bitacora> exportarBitacorasPlanta(String plantaId) {
        if (plantaId == null || plantaId.isBlank()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        return bitacoraRepositorio.listarPorPlanta(plantaId);
    }
}
