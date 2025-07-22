package com.planta.plantapp.infraestructura.mapper;

import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import com.planta.plantapp.infraestructura.entidad.UsuarioEntidad;

public class UsuarioMapper {
    private UsuarioMapper() {
    }

    public static UsuarioEntidad aEntidad(Usuario usuario) {
        UsuarioEntidad entidad = new UsuarioEntidad();
        entidad.setId(usuario.getId());
        entidad.setCorreo(usuario.getCorreo());
        // más campos...
        return entidad;
    }

    public static Usuario aDominio(UsuarioEntidad entidad) {
        return new Usuario(entidad.getId(), entidad.getCorreo() /*, ... */);
    }
}
