package com.planta.plantapp.infraestructura.repositorio.mysql.jpa;

import com.planta.plantapp.infraestructura.entidad.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioJpaRepositorio extends JpaRepository<UsuarioEntidad, Long> {
    Optional<UsuarioEntidad> findByCorreo(String correo);
}