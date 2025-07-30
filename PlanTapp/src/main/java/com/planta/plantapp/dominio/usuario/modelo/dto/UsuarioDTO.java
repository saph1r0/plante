package com.planta.plantapp.dominio.usuario.modelo.dto;

public class UsuarioDTO {
    private Long id;
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
