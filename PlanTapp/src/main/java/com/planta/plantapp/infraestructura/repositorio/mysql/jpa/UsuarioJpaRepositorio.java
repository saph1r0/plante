package com.planta.plantapp.infraestructura.repositorio.mysql.jpa;

import com.planta.plantapp.infraestructura.entidad.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioJpaRepositorio extends JpaRepository<UsuarioEntidad, String> {
    Optional<UsuarioEntidad> findByCorreo(String correo);
<<<<<<< HEAD:PlanTapp/src/main/java/com/planta/plantapp/infraestructura/repositorio/mysql/jpa/UsuarioJpaRepositorio.java
}
=======
}
>>>>>>> origin/hellen:PlanTapp/src/main/aplicacion/com/planta/plantapp/infraestructura/repositorio/mysql/jpa/UsuarioJpaRepositorio.java
