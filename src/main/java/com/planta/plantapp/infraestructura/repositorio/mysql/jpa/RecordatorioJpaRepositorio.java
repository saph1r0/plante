package com.planta.plantapp.infraestructura.repositorio.mysql.jpa;

import com.planta.plantapp.infraestructura.entidad.RecordatorioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordatorioJpaRepositorio extends JpaRepository<RecordatorioEntidad, Long> {
}
