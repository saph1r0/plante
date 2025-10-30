package com.planta.plantapp.dominio.usuario.modelo.dto;

import lombok.NonNull;

public class UsuarioDTO {
    @NonNull
    private Long id;
    @NonNull
    private String correo;

    public UsuarioDTO(Long id, String correo) {
        this.id = id;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }
}
