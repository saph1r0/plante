package com.planta.plantapp.infraestructura.repositorio.mysql.jpa;

import com.planta.plantapp.infraestructura.entidad.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepositorio extends JpaRepository<UsuarioEntidad, Long> {
}
