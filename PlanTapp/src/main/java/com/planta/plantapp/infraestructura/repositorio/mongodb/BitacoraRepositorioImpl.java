package com.planta.plantapp.infraestructura.repositorio.mongodb;

import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import com.planta.plantapp.dominio.modelo.IBitacoraRepositorio;
import com.planta.plantapp.infraestructura.repositorio.mongodb.mongo.BitacoraMongoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del repositorio de bitácora usando MongoDB.
 */
@Repository
public class BitacoraRepositorioImpl implements IBitacoraRepositorio {

    private final BitacoraMongoRepositorio bitacoraMongoRepositorio;

    @Autowired
    public BitacoraRepositorioImpl(BitacoraMongoRepositorio bitacoraMongoRepositorio) {
        this.bitacoraMongoRepositorio = bitacoraMongoRepositorio;
    }

    @Override
    public void guardar(Bitacora bitacora) {
        bitacoraMongoRepositorio.save(bitacora);
    }

    @Override
    public Bitacora obtenerPorId(String id) {
        return bitacoraMongoRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<Bitacora> listarTodas() {
        return bitacoraMongoRepositorio.findAll(Sort.by(Sort.Direction.DESC, "fecha"));
    }

    @Override
    public void eliminar(String id) {
        bitacoraMongoRepositorio.deleteById(id);
    }

    @Override
    public void actualizar(Bitacora bitacora) {
        bitacoraMongoRepositorio.save(bitacora);
    }

    @Override
    public List<Bitacora> listarPorPlanta(String plantaId) {
        return bitacoraMongoRepositorio.findByPlantaIdOrderByFechaDesc(plantaId);
    }

    @Override
    public List<Bitacora> listarPorTipoCuidado(String tipoCuidado) {
        return bitacoraMongoRepositorio.findByTipoCuidadoOrderByFechaDesc(tipoCuidado);
    }

    @Override
    public List<Bitacora> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return bitacoraMongoRepositorio.findByFechaBetweenOrderByFechaDesc(fechaInicio, fechaFin);
    }

    @Override
    public List<Bitacora> listarPorPlantaYFechas(String plantaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return bitacoraMongoRepositorio.findByPlantaIdAndFechaBetweenOrderByFechaDesc(plantaId, fechaInicio, fechaFin);
    }

    @Override
    public long contarPorPlanta(String plantaId) {
        return bitacoraMongoRepositorio.countByPlantaId(plantaId);
    }

    @Override
    public List<Bitacora> obtenerUltimasPorPlanta(String plantaId, int limite) {
        PageRequest pageRequest = PageRequest.of(0, limite, Sort.by(Sort.Direction.DESC, "fecha"));
        return bitacoraMongoRepositorio.findByPlantaId(plantaId, pageRequest).getContent();
    }
}
